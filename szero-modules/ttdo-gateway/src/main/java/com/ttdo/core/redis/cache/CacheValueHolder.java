//package com.ttdo.core.redis.cache;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.choerodon.core.convertor.ApplicationContextHelper;
//import io.choerodon.core.oauth.CustomUserDetails;
//import io.choerodon.core.oauth.DetailsHelper;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import org.apache.commons.lang3.StringUtils;
//import org.hzero.core.base.BaseConstants;
//import org.hzero.core.helper.LanguageHelper;
//import org.hzero.core.redis.RedisHelper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class CacheValueHolder {
//    public static final String PLACEHOLDER_LANG = "{lang}";
//    public static final String PLACEHOLDER_TENANT_ID = "{tenantId}";
//    private static final Logger logger = LoggerFactory.getLogger(CacheValueHolder.class);
//
//    private CacheValueHolder() throws IllegalAccessException {
//        throw new IllegalAccessException();
//    }
//
//    public static String getValue(String key) {
//        key = replacePlaceholder(key);
//        return getRedisHelper().strGet(key);
//    }
//
//    public static String getValueFromObject(String key, String searchKey) {
//        key = replacePlaceholder(key);
//        String json = getRedisHelper().strGet(key);
//        if (StringUtils.isNotBlank(json)) {
//            try {
//                HashMap<String, String> map = (HashMap)getObjectMapper().readValue(json, HashMap.class);
//                return (String)map.get(searchKey);
//            } catch (IOException var4) {
//                logger.error(var4.getMessage(), var4);
//            }
//        }
//
//        return null;
//    }
//
//    public static String getValueFromMapValue(String key, Object primaryValue) {
//        key = replacePlaceholder(key);
//        return getRedisHelper().hshGet(key, String.valueOf(primaryValue));
//    }
//
//    public static String getValueFromMapObject(String key, Object primaryValue, String searchKey) {
//        key = replacePlaceholder(key);
//        String json = getRedisHelper().hshGet(key, String.valueOf(primaryValue));
//
//        try {
//            HashMap<String, String> map = (HashMap)getObjectMapper().readValue(json, HashMap.class);
//            return (String)map.get(searchKey);
//        } catch (IOException var5) {
//            logger.error(var5.getMessage(), var5);
//            return null;
//        }
//    }
//
//    public static String getValueFromListObject(String key, String primaryKey, Object primaryValue, String searchKey) {
//        key = replacePlaceholder(key);
//        List<String> list = getRedisHelper().lstAll(key);
//        Iterator var5 = list.iterator();
//
//        while(var5.hasNext()) {
//            String obj = (String)var5.next();
//
//            try {
//                HashMap<String, String> map = (HashMap)getObjectMapper().readValue(obj, HashMap.class);
//                if (((String)map.get(primaryKey)).equals(primaryValue)) {
//                    return (String)map.get(searchKey);
//                }
//            } catch (IOException var8) {
//                logger.error(var8.getMessage(), var8);
//            }
//        }
//
//        return null;
//    }
//
//    private static RedisHelper getRedisHelper() {
//        return (RedisHelper)ApplicationContextHelper.getContext().getBean(RedisHelper.class);
//    }
//
//    private static ObjectMapper getObjectMapper() {
//        return (ObjectMapper)ApplicationContextHelper.getContext().getBean(ObjectMapper.class);
//    }
//
//    private static String replacePlaceholder(String key) {
//        String tenantId;
//        CustomUserDetails details;
//        if (key.contains("{lang}")) {
//            details = DetailsHelper.getUserDetails();
//            if (details != null) {
//                tenantId = details.getLanguage();
//            } else {
//                tenantId = LanguageHelper.language();
//            }
//
//            key = StringUtils.replace(key, "{lang}", tenantId);
//        }
//
//        if (key.contains("{tenantId}")) {
//            tenantId = "";
//            details = DetailsHelper.getUserDetails();
//            if (details != null) {
//                tenantId = details.getOrganizationId().toString();
//            } else {
//                tenantId = BaseConstants.DEFAULT_TENANT_ID.toString();
//            }
//
//            key = StringUtils.replace(key, "{tenantId}", tenantId);
//        }
//
//        return key;
//    }
//}
