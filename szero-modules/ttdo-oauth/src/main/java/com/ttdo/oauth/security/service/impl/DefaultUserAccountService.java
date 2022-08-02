package com.ttdo.oauth.security.service.impl;

import com.ttdo.core.user.UserType;
import com.ttdo.oauth.domain.entity.User;
import com.ttdo.oauth.security.constant.LoginType;
import com.ttdo.oauth.security.service.UserAccountService;

public class DefaultUserAccountService implements UserAccountService {


    @Override
    public User findLoginUser(LoginType loginType, String account, UserType userType) {
        User user;
        switch (loginType) {
            case ACCOUNT:
        }

        return user;
    }
}
