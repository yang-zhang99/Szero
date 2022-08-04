package com.ttdo.oauth.domain.utils;

import com.yang.core.base.BaseConstants;
import com.yang.redis.RedisHelper;
import com.yang.redis.safe.SafeRedisHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component
public class ConfigGetter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigGetter.class);
    @Autowired
    private Environment environment;
    @Autowired
    private RedisHelper redisHelper;

    public String getValue(ProfileCode profile) {
        return getValue(0L, profile);
    }


    /**
     * 获取租户的配置信息
     * 如果存在 key，则从 Redis 中取值。
     * 如果不存在 key，则从 Environment 中取值。
     *
     * @param tenantId tenantId
     * @param profile  profile
     * @return String
     */
    public String getValue(long tenantId, ProfileCode profile) {
        String profileKey = profile.profileKey();
        String value = null;
        if (StringUtils.isNotBlank(profileKey)) {
            try {
                value = SafeRedisHelper.execute(1, () -> {
                    String key = "code" + ":config:" + profileKey + "." + tenantId;
                    return redisHelper.strGet(key);
                });
            } catch (Exception e) {
                LOGGER.error("get profile error, profile code is {}", profile.profileKey(), e);
            }
        }
        if (StringUtils.isBlank(value)) {
            value = environment.getProperty(profile.configKey(), profile.defaultValue());
        }
        return value;
    }

    public boolean isTrue(ProfileCode profile) {
        return isTrue(0L, profile);
    }

    public boolean isTrue(long tenantId, ProfileCode profile) {
        String value = getValue(tenantId, profile);
        if (StringUtils.isNumeric(value)) {
            return Integer.parseInt(value) > 0;
        }
        return Boolean.parseBoolean(value);
    }
}
