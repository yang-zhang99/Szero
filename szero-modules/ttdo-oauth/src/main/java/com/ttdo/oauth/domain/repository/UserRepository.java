package com.ttdo.oauth.domain.repository;

import com.ttdo.oauth.domain.entity.User;

public interface UserRepository {


    /**
     * 根据邮箱查询登录用户信息
     *
     * @param email 邮箱
     */
    User selectLoginUserByEmail(String email, String userType);

    /**
     * 根据手机号查询登录用户信息
     *
     * @param internationalTelCode 国际冠码
     * @param phone                手机号
     */
    User selectLoginUserByPhone(String internationalTelCode, String phone, String userType);

    /**
     * 根据手机号查询登录用户信息
     *
     * @param phone                手机号
     */
    User selectLoginUserByPhone(String phone, String userType);

    /**
     * 根据登录名查询登录用户信息
     *
     * @param loginName 登录账号
     */
    User selectLoginUserByLoginName(String loginName, String userType);

}
