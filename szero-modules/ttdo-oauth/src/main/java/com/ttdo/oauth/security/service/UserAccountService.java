package com.ttdo.oauth.security.service;

import com.ttdo.core.user.UserType;
import com.ttdo.oauth.domain.entity.User;
import com.ttdo.oauth.security.constant.LoginType;

/**
 * 用户登录相关的业务场景
 */
public interface UserAccountService {

    /**
     * 查询登录用户
     *
     * @param loginType 登录方式
     * @param account   loginName/phone/email
     */
    User findLoginUser(LoginType loginType, String account, UserType userType);

}
