package com.me.oauth.domain.service.impl;

import com.me.oauth.domain.service.PasswordErrorTimesService;
import com.yang.common.HZeroService;
import com.yang.redis.RedisHelper;
import com.yang.redis.safe.SafeRedisHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PasswordErrorTimesServiceImpl implements PasswordErrorTimesService {
    private static final String LOGIN_ERROR_TIMES_KEY = "hoth:login_error_times:";
    @Autowired
    private RedisHelper redisHelper;


    public long increaseErrorTimes(Long userId) {
        return (Long) SafeRedisHelper.execute(HZeroService.Oauth.REDIS_DB, () -> {
            long times = this.redisHelper.strIncrement("hoth:login_error_times:" + userId, 1L);
            this.redisHelper.setExpire("hoth:login_error_times:" + userId, 24L, TimeUnit.HOURS);
            return times;
        });
    }

    public void clearErrorTimes(Long userId) {
        SafeRedisHelper.execute(HZeroService.Oauth.REDIS_DB, () -> {
            this.redisHelper.delKey("hoth:login_error_times:" + userId);
        });
    }

    public long getErrorTimes(Long userId) {
        return (Long) SafeRedisHelper.execute(HZeroService.Oauth.REDIS_DB, () -> {
            String str = this.redisHelper.strGet("hoth:login_error_times:" + userId);
            return StringUtils.isNotBlank(str) ? Long.parseLong(str) : 0L;
        });
    }
}
