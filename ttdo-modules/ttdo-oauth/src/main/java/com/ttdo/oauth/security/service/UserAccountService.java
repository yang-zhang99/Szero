package com.ttdo.oauth.security.service;

import com.ttdo.oauth.domain.entity.User;
import com.ttdo.oauth.security.constant.LoginType;
import org.springframework.stereotype.Component;


/**
 * 用户登录相关的业务场景
 */
@Component
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


    /**
     * 是否需要验证码
     *
     * @param user 用户
     */
    boolean isNeedCaptcha(User user);

}
