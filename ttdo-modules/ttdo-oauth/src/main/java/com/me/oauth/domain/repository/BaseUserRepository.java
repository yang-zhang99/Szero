package com.me.oauth.domain.repository;

import com.me.core.user.UserType;


/**
 *
 * @author bojiangzhou 2019/08/06
 */
public interface BaseUserRepository  {

    /**
     * 查询账号是否存在
     */
    boolean existsByLoginName(String loginName);

    /**
     * 查询手机是否存在
     */
    boolean existsByPhone(String phone, UserType userType);

    /**
     * 查询邮箱是否存在
     */
    boolean existsByEmail(String email, UserType userType);

    /**
     * 判断账号，或手机，或邮箱是否存
     */
    boolean existsUser(String loginName, String phone, String email, UserType userType);

}
