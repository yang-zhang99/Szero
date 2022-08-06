package com.ttdo.oauth.security.service.impl;

import com.ttdo.oauth.domain.entity.User;
import com.ttdo.oauth.security.service.LoginRecordService;
import com.yang.core.base.BaseConstants;
import com.yang.redis.RedisHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component

public class DefaultLoginRecordService implements LoginRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLoginRecordService.class);

    private static final String ANTI_REPLAY_PASSWORD_PREFIX = "hoth" + ":replay-pass:";

    private static final ThreadLocal<User> LOCAL_LOGIN_USER = new ThreadLocal<>();

//    private final BaseUserService baseUserService;
//    private final PasswordErrorTimesService passwordErrorTimesService;
//    private final BasePasswordPolicyRepository basePasswordPolicyRepository;
    private final RedisHelper redisHelper;
    public DefaultLoginRecordService(
//            BaseUserService baseUserService,
//                                     PasswordErrorTimesService passwordErrorTimesService,
//                                     BasePasswordPolicyRepository basePasswordPolicyRepository,
                                     RedisHelper redisHelper
                                     ) {
//        this.baseUserService = baseUserService;
//        this.passwordErrorTimesService = passwordErrorTimesService;
//        this.basePasswordPolicyRepository = basePasswordPolicyRepository;
        this.redisHelper = redisHelper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long loginError(User user) {
        if (user == null) {
            return 0;
        }
//        long errorTimes = passwordErrorTimesService.increaseErrorTimes(user.getId());
//
//        BasePasswordPolicy passwordPolicy = basePasswordPolicyRepository.selectPasswordPolicy(user.getOrganizationId());
//        boolean enableSecurity = Optional.ofNullable(passwordPolicy.getEnableSecurity()).orElse(false);
//        boolean enableLock = Optional.ofNullable(passwordPolicy.getEnableLock()).orElse(false);
//        long maxErrorTime = Optional.ofNullable(passwordPolicy.getMaxErrorTime()).orElse(0);
//        // 判断启用登录安全策略并且开启允许锁定用户才支持锁定，否则不会锁定
//        if (enableSecurity && enableLock) {
//            if (errorTimes >= maxErrorTime) {
//                // 锁定用户
//                LOGGER.info("begin lock user, userId is: {} ", user.getId());
//                baseUserService.lockUser(user.getId(), user.getOrganizationId());
//                user.setLocked(true);
//            } else {
//                return maxErrorTime - errorTimes;
//            }
//        }
        // 如果不锁定用户，相当于是可以无限尝试
        return -1;
    }

    @Override
    public long getErrorTimes(User user) {
        if (user == null) {
            return 0;
        }
//        return passwordErrorTimesService.getErrorTimes(user.getId());
        return -1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void loginSuccess(User user) {
//        passwordErrorTimesService.clearErrorTimes(user.getId());
    }

    @Override
    public void saveLocalLoginUser(User user) {
        if (user != null) {
            LOCAL_LOGIN_USER.set(user);
        }
    }

    @Override
    public User getLocalLoginUser() {
        return LOCAL_LOGIN_USER.get();
    }

    @Override
    public void clearLocalLoginUser() {
        LOCAL_LOGIN_USER.remove();
    }

    @Override
    public void recordLogoutUrl(String tokenValue, String logoutRedirectUrl) {
        String key = LOGOUT_REDIRECT_URL_PREFIX + tokenValue;
        redisHelper.strSet(key, logoutRedirectUrl);
    }

    @Override
    public String getLogoutUrl(String tokenValue) {
        String key = LOGOUT_REDIRECT_URL_PREFIX + tokenValue;
        String logoutUrl = redisHelper.strGet(key);
        redisHelper.delKey(key);

        return logoutUrl;
    }

    @Override
    public boolean savePassIfAbsent(String clientName, String pass, long expire, TimeUnit timeUnit) {
        String key = ANTI_REPLAY_PASSWORD_PREFIX + clientName + BaseConstants.Symbol.COLON + pass;

        Boolean absent = redisHelper.strSetIfAbsent(key, "1"); // true: 原 key 不存在
        if (absent == null) {
            return StringUtils.isNotBlank(redisHelper.strGet(key));
        } else {
            if (absent) {
                redisHelper.setExpire(key, expire, timeUnit);
            }
            return !absent;
        }
    }
}
