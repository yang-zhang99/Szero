//package com.ttdo.core.redis.cache;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ttdo.core.redis.RedisHelper;
//
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.env.Environment;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.Assert;
//
//@Aspect
//@Order(20)
//public class CacheValueAspect {
//    private final RedisHelper redisHelper;
//    private final Environment environment;
//    private ObjectMapper mapper;
//    private static final Logger logger = LoggerFactory.getLogger(CacheValueAspect.class);
//    private static final String FIELD_LANG = "lang";
//    private static final String FIELD_USER_ID = "userId";
//    private static final String FIELD_TENANT_ID = "tenantId";
//    private static final String PLACEHOLDER_LANG = "{lang}";
//    private static final String PLACEHOLDER_USER_ID = "{userId}";
//    private static final String PLACEHOLDER_TENANT_ID = "{tenantId}";
//
//    public CacheValueAspect(RedisHelper redisHelper, Environment environment) {
//        this.mapper = BaseConstants.MAPPER;
//        Assert.notNull(redisHelper, "redisHelper not be null.");
//        Assert.notNull(environment, "environment not be null.");
//        this.redisHelper = redisHelper;
//        this.environment = environment;
//    }
//
//    @AfterReturning(
//            value = "@annotation(processCacheValue)",
//            returning = "result"
//    )
//    public Object afterReturning(JoinPoint joinPoint, ProcessCacheValue processCacheValue, Object result) throws Exception {
//        if (result == null) {
//            return null;
//        } else {
//            if (result instanceof ResponseEntity) {
//                Object body = ((ResponseEntity)result).getBody();
//                if (body == null) {
//                    return result;
//                }
//
//                if (body instanceof Collection) {
//                    Iterator var5 = ((Collection)body).iterator();
//
//                    while(var5.hasNext()) {
//                        Object obj = var5.next();
//                        this.processObject(obj);
//                    }
//                } else {
//                    this.processObject(body);
//                }
//            } else if (result instanceof Collection) {
//                Iterator var7 = ((Collection)result).iterator();
//
//                while(var7.hasNext()) {
//                    Object obj = var7.next();
//                    this.processObject(obj);
//                }
//            } else {
//                this.processObject(result);
//            }
//
//            return result;
//        }
//    }
//
//    private void processObject(Object obj) throws IllegalAccessException, IOException {
//        if (obj instanceof Cacheable) {
//            Field[] fields = Reflections.getAllField(obj.getClass());
//            Field[] var3 = fields;
//            int var4 = fields.length;
//
//            for(int var5 = 0; var5 < var4; ++var5) {
//                Field field = var3[var5];
//                if (field.isAnnotationPresent(CacheValue.class)) {
//                    this.processObjectCacheValue(obj, fields, field, (CacheValue)field.getAnnotation(CacheValue.class));
//                }
//
//                Reflections.makeAccessible(field);
//                Object fieldValue = field.get(obj);
//                if (fieldValue != null) {
//                    if (fieldValue instanceof Collection) {
//                        Iterator var8 = ((Collection)fieldValue).iterator();
//
//                        while(var8.hasNext()) {
//                            Object fv = var8.next();
//                            this.processObject(fv);
//                        }
//                    } else {
//                        this.processObject(fieldValue);
//                    }
//                }
//            }
//
//        }
//    }
//
//    private void processObjectCacheValue(Object target, Field[] targetFields, Field cacheValueField, CacheValue cacheValue) throws IllegalAccessException, IOException {
//        int db = cacheValue.db();
//        String key;
//        if (StringUtils.isNotBlank(cacheValue.dbAlias())) {
//            try {
//                key = this.environment.resolveRequiredPlaceholders(cacheValue.dbAlias());
//                db = Integer.parseInt(key);
//            } catch (Exception var17) {
//                logger.warn("parse redis db alias error, use default db, dbAlias is {}", cacheValue.dbAlias(), var17);
//            }
//        }
//
//        key = this.replacePlaceholder(cacheValue.key(), target, targetFields);
//        Field primaryField = null;
//        Field[] var8 = targetFields;
//        int var9 = targetFields.length;
//
//        Field json;
//        for(int var10 = 0; var10 < var9; ++var10) {
//            json = var8[var10];
//            if (StringUtils.equals(cacheValue.primaryKey(), json.getName())) {
//                primaryField = json;
//                break;
//            }
//        }
//
//        String primaryValue = null;
//        if (primaryField != null) {
//            Reflections.makeAccessible(primaryField);
//            primaryValue = String.valueOf(primaryField.get(target));
//        }
//
//        primaryValue = (String)StringUtils.defaultIfBlank(primaryValue, "null");
//        String searchKey = cacheValue.searchKey();
//        logger.debug("process cache value, key is [{}], primaryValue is [{}], db is [{}]", new Object[]{key, primaryValue, db});
//        String searchValue = null;
//        json = null;
//        Map<String, String> map = null;
//        if (db > -1) {
//            this.redisHelper.setCurrentDatabase(db);
//        }
//
//        String json;
//        switch (cacheValue.structure()) {
//            case VALUE:
//                searchValue = this.redisHelper.strGet(key);
//                break;
//            case OBJECT:
//                json = this.redisHelper.strGet(key);
//                if (StringUtils.isNotBlank(json)) {
//                    map = (Map)this.mapper.readValue(json, HashMap.class);
//                    searchValue = (String)map.get(searchKey);
//                }
//                break;
//            case MAP_VALUE:
//                searchValue = this.redisHelper.hshGet(key, primaryValue);
//                break;
//            case MAP_OBJECT:
//                json = this.redisHelper.hshGet(key, primaryValue);
//                if (StringUtils.isNotBlank(json)) {
//                    map = (Map)this.mapper.readValue(json, HashMap.class);
//                    searchValue = (String)map.get(searchKey);
//                }
//                break;
//            case LIST_OBJECT:
//                List<String> list = this.redisHelper.lstAll(key);
//                Iterator var14 = list.iterator();
//
//                while(var14.hasNext()) {
//                    String obj = (String)var14.next();
//                    map = (Map)this.mapper.readValue(obj, HashMap.class);
//                    String mapKey = (String)StringUtils.defaultIfBlank(cacheValue.primaryKeyAlias(), cacheValue.primaryKey());
//                    if (StringUtils.equals((CharSequence)map.get(mapKey), primaryValue)) {
//                        searchValue = (String)map.get(searchKey);
//                        break;
//                    }
//                }
//        }
//
//        if (db > -1) {
//            this.redisHelper.clearCurrentDatabase();
//        }
//
//        Reflections.makeAccessible(cacheValueField);
//        cacheValueField.set(target, searchValue);
//    }
//
//    private String replacePlaceholder(String key, Object target, Field[] targetFields) throws IllegalAccessException {
//        String[] placeholders = StringUtils.substringsBetween(key, "{", "}");
//        if (ArrayUtils.isEmpty(placeholders)) {
//            return key;
//        } else {
//            Map<String, String> values = new HashMap(placeholders.length);
//            Field[] var6 = targetFields;
//            int var7 = targetFields.length;
//
//            int var8;
//            for(var8 = 0; var8 < var7; ++var8) {
//                Field targetField = var6[var8];
//                String[] var10 = placeholders;
//                int var11 = placeholders.length;
//
//                for(int var12 = 0; var12 < var11; ++var12) {
//                    String placeholder = var10[var12];
//                    if (StringUtils.equals(placeholder, targetField.getName())) {
//                        Reflections.makeAccessible(targetField);
//                        values.put(placeholder, String.valueOf(targetField.get(target)));
//                        break;
//                    }
//                }
//            }
//
//            String[] var14 = placeholders;
//            var7 = placeholders.length;
//
//            for(var8 = 0; var8 < var7; ++var8) {
//                String placeholder = var14[var8];
//                if (values.containsKey(placeholder)) {
//                    key = StringUtils.replace(key, "{" + placeholder + "}", (String)StringUtils.defaultIfBlank((CharSequence)values.get(placeholder), "null"));
//                }
//            }
//
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
//
//            return key;
//        }
//    }
//}
