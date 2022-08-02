package com.ttdo.oauth.security.service;

import com.ttdo.oauth.domain.entity.User;

import java.util.concurrent.TimeUnit;

/**
 * 登录记录
 */
public interface LoginRecordService {


    String LOGOUT_REDIRECT_URL_PREFIX = "hout" + ":logout_redirect_url:";

    /**
     * 登录失败记录
     *
     * @param user 用户
     * @return 返回还可以登录几次
     */
    long loginError(User user);

    /**
     * 获取登录失败次数
     */
    long getErrorTimes(User user);

    /**
     * 登录成功记录
     *
     * @param user 用户
     */
    void loginSuccess(User user);

    /**
     * 将登录用户存储到线程变量中，避免多次查询
     *
     * @param user 登录用户
     */
    void saveLocalLoginUser(User user);

    /**
     * 获取线程变量中的登录用户
     *
     * @return user 登录用户
     */
    User getLocalLoginUser();

    /**
     * 清除线程变量中的登录用户
     */
    void clearLocalLoginUser();

    /**
     * 记录登录成功地址
     *
     * @param tokenValue        access_token
     * @param logoutRedirectUrl HttpServletRequest
     */
    void recordLogoutUrl(String tokenValue, String logoutRedirectUrl);

    /**
     * 获取记录的登录跳转地址
     */
    String getLogoutUrl(String tokenValue);

    /**
     * 保存密码
     *
     * @param clientName 客户端
     * @param pass       密码
     * @param expire     过期时间
     * @param timeUnit   时间单位
     * @return true - pass 存在； false - pass 不存在
     */
    boolean savePassIfAbsent(String clientName, String pass, long expire, TimeUnit timeUnit);

}
