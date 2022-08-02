package com.ttdo.oauth.security.service;

import com.ttdo.oauth.domain.entity.User;
import com.ttdo.oauth.security.constant.LoginType;

public interface UserAccountService {




    /**
     * 查询登录用户
     *
     * @param account loginName/phone/email
     */
    User findLoginUser(String account, String userType);


    /**
     * 查询登录用户
     *
     * @param loginType 登录方式
     * @param account   loginName/phone/email
     */
    User findLoginUser(LoginType loginType, String account, String userType);
}
