package com.ttdo.oauth.domain.utils;

import com.yang.redis.RedisHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ConfigGetter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigGetter.class);

    @Autowired
    private RedisHelper redisHelper;

    public String getValue(ProfileCode profile) {
        return getValue(0L, profile);
    }


    /**
     * 获取租户的配置信息
     *
     * @param tenantId tenantId
     * @param profile profile
     * @return String
     */
    public String getValue(long tenantId, ProfileCode profile) {
        String profileKey = profile.profileKey();
        String value = null;
//        if (StringUtils.isNotBlank(profileKey)) {
//            try {
//                value = SafeRedisHelper.execute(HZeroService.Platform.REDIS_DB, () -> {
//                    String key = HZeroService.Platform.CODE + ":config:" + profileKey + "." + tenantId;
//                    return redisHelper.strGet(key);
//                });
//            } catch (Exception e) {
//                LOGGER.error("get profile error, profile code is {}", profile.profileKey(), e);
//            }
//        }
//        if (StringUtils.isBlank(value)) {
//            value = environment.getProperty(profile.configKey(), profile.defaultValue());
//        }
        return value;
    }
}
