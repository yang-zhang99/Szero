package com.ttdo.oauth.infra.mapper;


import com.ttdo.oauth.domain.entity.User;

/**
 * 查询用户信息的 Mapper
 *
 * @author yang99
 */
public interface UserPlusMapper {

    /**
     * 查询登录的必要信息（不做限制）
     *
     * @param params params
     * @return User
     */
    User selectLoginUser(User params);


    /**
     * 查询登录必要信息
     *
     * @param params 参数
     */
//    UserVO selectSelf(UserVO params);

}
