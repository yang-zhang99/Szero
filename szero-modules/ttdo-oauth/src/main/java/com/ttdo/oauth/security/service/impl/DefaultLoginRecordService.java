package com.ttdo.oauth.security.service.impl;

import com.ttdo.oauth.domain.entity.User;
import com.ttdo.oauth.security.service.LoginRecordService;

import java.util.concurrent.TimeUnit;

public class DefaultLoginRecordService implements LoginRecordService {

    private static final ThreadLocal<User> LOCAL_LOGIN_USER = new ThreadLocal<>();


    @Override
    public long loginError(User user) {
        return 0;
    }

    @Override
    public long getErrorTimes(User user) {
        return 0;
    }

    @Override
    public void loginSuccess(User user) {

    }

    @Override
    public void saveLocalLoginUser(User user) {

    }

    @Override
    public User getLocalLoginUser() {
        return LOCAL_LOGIN_USER.get();
    }

    @Override
    public void clearLocalLoginUser() {

    }

    @Override
    public void recordLogoutUrl(String tokenValue, String logoutRedirectUrl) {

    }

    @Override
    public String getLogoutUrl(String tokenValue) {
        return null;
    }

    @Override
    public boolean savePassIfAbsent(String clientName, String pass, long expire, TimeUnit timeUnit) {
        return false;
    }
}
