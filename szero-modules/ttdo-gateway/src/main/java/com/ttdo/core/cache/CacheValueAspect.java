package com.ttdo.core.cache;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttdo.core.base.BaseConstants;
import com.ttdo.core.redis.RedisHelper;
import com.ttdo.core.util.Reflections;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Order(20)
public class CacheValueAspect {
    private final RedisHelper redisHelper;
    private final Environment environment;
    private final ObjectMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(CacheValueAspect.class);
    private static final String FIELD_LANG = "lang";
    private static final String FIELD_USER_ID = "userId";
    private static final String FIELD_TENANT_ID = "tenantId";
    private static final String PLACEHOLDER_LANG = "{lang}";
    private static final String PLACEHOLDER_USER_ID = "{userId}";
    private static final String PLACEHOLDER_TENANT_ID = "{tenantId}";

    public CacheValueAspect(RedisHelper redisHelper, Environment environment) {
        this.mapper = BaseConstants.MAPPER;
        Assert.notNull(redisHelper, "redisHelper not be null.");
        Assert.notNull(environment, "environment not be null.");
        this.redisHelper = redisHelper;
        this.environment = environment;
    }

    /**
     * 使用注解根据配置从缓存中获取值。
     *
     * @param joinPoint         joinPoint
     * @param processCacheValue processCacheValue
     * @param result            result
     * @return Object
     * @throws Exception
     */
    @AfterReturning(value = "@annotation(processCacheValue)", returning = "result")
    public Object afterReturning(JoinPoint joinPoint, ProcessCacheValue processCacheValue, Object result) throws Exception {
        if (result == null) {
            return null;
        } else {
            if (result instanceof ResponseEntity) {
                Object body = ((ResponseEntity<?>) result).getBody();
                if (body == null) {
                    return result;
                }
                if (body instanceof Collection) {
                    for (Object obj : (Collection) body) {
                        this.processObject(obj);
                    }
                } else {
                    this.processObject(body);
                }
            } else if (result instanceof Collection) {
                for (Object obj : (Collection) result) {
                    this.processObject(obj);
                }
            } else {
                this.processObject(result);
            }

            return result;
        }
    }

    /**
     * 查找 CacheValue.class 类
     *
     * @param obj obj
     * @throws IllegalAccessException IllegalAccessException
     * @throws IOException            IOException
     */
    private void processObject(Object obj) throws IllegalAccessException, IOException {
        if (obj instanceof Cacheable) {
            Field[] fields = Reflections.getAllField(obj.getClass());

            for (Field field : fields) {
                if (field.isAnnotationPresent(CacheValue.class)) {
                    this.processObjectCacheValue(obj, fields, field, field.getAnnotation(CacheValue.class));
                }
                Reflections.makeAccessible(field);
                Object fieldValue = field.get(obj);
                if (fieldValue != null) {
                    if (fieldValue instanceof Collection) {
                        for (Object fv : (Collection) fieldValue) {
                            this.processObject(fv);
                        }
                    } else {
                        this.processObject(fieldValue);
                    }
                }
            }
        }
    }

    /**
     * @param target          target
     * @param targetFields    targetFields
     * @param cacheValueField cacheValueField
     * @param cacheValue      cacheValue
     * @throws IllegalAccessException IllegalAccessException
     * @throws IOException            IOException
     */
    private void processObjectCacheValue(Object target, Field[] targetFields, Field cacheValueField, CacheValue cacheValue) throws IllegalAccessException, IOException {
        int db = cacheValue.db();
        String key;
        if (StringUtils.isNotBlank(cacheValue.dbAlias())) {
            try {
                key = this.environment.resolveRequiredPlaceholders(cacheValue.dbAlias());
                db = Integer.parseInt(key);
            } catch (Exception e) {
                logger.warn("parse redis db alias error, use default db, dbAlias is {}", cacheValue.dbAlias(), e);
            }
        }
        key = this.replacePlaceholder(cacheValue.key(), target, targetFields);

        Field primaryField = null;
        for (Field targetField : targetFields) {
            if (StringUtils.equals(cacheValue.primaryKey(), targetField.getName())) {
                primaryField = targetField;
                break;
            }
        }

        String primaryValue = null;
        if (primaryField != null) {
            Reflections.makeAccessible(primaryField);
            primaryValue = String.valueOf(primaryField.get(target));
        }

        primaryValue = StringUtils.defaultIfBlank(primaryValue, "null");
        String searchKey = cacheValue.searchKey();

        logger.debug("process cache value, key is [{}], primaryValue is [{}], db is [{}]", new Object[]{key, primaryValue, db});
        String searchValue = null;
        Map map = null;
        String json;

        if (db > -1) {
            this.redisHelper.setCurrentDatabase(db);
        }

        switch (cacheValue.structure()) {
            case VALUE:
                searchValue = this.redisHelper.strGet(key);
                break;
            case OBJECT:
                json = this.redisHelper.strGet(key);
                if (StringUtils.isNotBlank(json)) {
                    map = this.mapper.readValue(json, HashMap.class);
                    searchValue = (String) map.get(searchKey);
                }
                break;
            case MAP_VALUE:
                searchValue = this.redisHelper.hshGet(key, primaryValue);
                break;
            case MAP_OBJECT:
                json = this.redisHelper.hshGet(key, primaryValue);
                if (StringUtils.isNotBlank(json)) {
                    map = this.mapper.readValue(json, HashMap.class);
                    searchValue = (String) map.get(searchKey);
                }
                break;
            case LIST_OBJECT:
                List<String> list = this.redisHelper.lstAll(key);
                for (String obj : list) {
                    map = this.mapper.readValue(obj, HashMap.class);
                    String mapKey = StringUtils.defaultIfBlank(cacheValue.primaryKeyAlias(), cacheValue.primaryKey());
                    if (StringUtils.equals((CharSequence) map.get(mapKey), primaryValue)) {
                        searchValue = (String) map.get(searchKey);
                        break;
                    }
                }
        }

        if (db > -1) {
            this.redisHelper.clearCurrentDatabase();
        }

        Reflections.makeAccessible(cacheValueField);
        cacheValueField.set(target, searchValue);
    }

    /**
     * @param key          key
     * @param target       target
     * @param targetFields targetFields
     * @return String String
     * @throws IllegalAccessException
     */
    private String replacePlaceholder(String key, Object target, Field[] targetFields) throws IllegalAccessException {
        String[] placeholders = StringUtils.substringsBetween(key, "{", "}");
        if (ArrayUtils.isEmpty(placeholders)) {
            return key;
        } else {
            HashMap<String, String> values = new HashMap<>(placeholders.length);

            for (Field targetField : targetFields) {
                for (String placeholder : placeholders) {
                    if (StringUtils.equals(placeholder, targetField.getName())) {
                        Reflections.makeAccessible(targetField);
                        values.put(placeholder, String.valueOf(targetField.get(target)));
                        break;
                    }
                }
            }

            for (String placeholder : placeholders) {
                if (values.containsKey(placeholder)) {
                    key = StringUtils.replace(key, "{" + placeholder + "}", (String) StringUtils.defaultIfBlank((CharSequence) values.get(placeholder), "null"));
                }
            }
//            String tenantId;
//            CustomUserDetails details;
//            if (key.contains("{lang}")) {
//                details = DetailsHelper.getUserDetails();
//                if (details != null) {
//                    tenantId = details.getLanguage();
//                } else {
//                    tenantId = LanguageHelper.language();
//                }
//
//                key = StringUtils.replace(key, "{lang}", String.valueOf(tenantId));
//            }
//
//            if (key.contains("{userId}")) {
//                details = DetailsHelper.getUserDetails();
//                Object userId;
//                if (details != null) {
//                    userId = details.getUserId();
//                } else {
//                    userId = "null";
//                }
//
//                key = StringUtils.replace(key, "{userId}", String.valueOf(userId));
//            }
//
//            if (key.contains("{tenantId}")) {
//                details = DetailsHelper.getUserDetails();
//                if (details != null) {
//                    tenantId = details.getOrganizationId().toString();
//                } else {
//                    tenantId = BaseConstants.DEFAULT_TENANT_ID.toString();
//                }
//
//                key = StringUtils.replace(key, "{tenantId}", String.valueOf(tenantId));
//            }
            return key;
        }
    }
}