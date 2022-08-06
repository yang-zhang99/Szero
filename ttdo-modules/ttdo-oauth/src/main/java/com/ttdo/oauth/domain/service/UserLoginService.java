package com.ttdo.oauth.domain.service;



import com.ttdo.oauth.domain.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户相关业务
 *
 * @author bojiangzhou 2019/02/25
 */
public interface UserLoginService {

    /**
     * 根据 request 中的 username 字段查询用户
     *
     * @param request HttpServletRequest
     * @return User
     */
    User queryRequestUser(HttpServletRequest request);

    /**
     * 是否需要验证码
     *
     * @param user 用户
     */
    boolean isNeedCaptcha(User user);

//    Map<String, Object> getLoginInitParams(HttpServletRequest request);
//
//    /**
//     * 手机+验证码登录获取Token
//     *
//     * @param request HttpServletRequest
//     * @return Token
//     */
//    AuthenticationResult loginMobileForToken(HttpServletRequest request);
//
//    /**
//     * 三方登录获取Token
//     *
//     * @param request HttpServletRequest
//     * @return Token
//     */
//    AuthenticationResult loginOpenForToken(HttpServletRequest request);
//
//    /**
//     * 绑定三方账号
//     *
//     * @param request HttpServletRequest
//     * @return Token
//     */
//    AuthenticationResult bindOpenAccount(HttpServletRequest request);

}
