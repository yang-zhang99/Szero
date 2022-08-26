package com.me.oauth.infra.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.me.oauth.domain.entity.User;
import com.me.oauth.domain.vo.UserRoleDetails;
import com.me.oauth.domain.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 查询用户信息的 Mapper
 *
 * @author yang99
 */
public interface UserPlusMapper extends BaseMapper<User> {

    /**
     * 查询登录必要信息
     *
     * @param params 参数
     */
    User selectLoginUser(User params);

    /**
     * 查询登录必要信息
     *
     * @param params 参数
     */
    UserVO selectSelf(UserVO params);

    /**
     * 查询用户角色信息
     *
     * @param userId 用户ID
     * @return 用户角色信息
     */
    List<UserRoleDetails> selectRoleDetails(@Param("userId") Long userId);

    List<UserRoleDetails> selectRootUserRoleDetails(@Param("userId") Long userId, @Param("tenantId") Long tenantId);

    /**
     * 查询用户分配的角色数量
     *
     * @param userId 用户ID
     */
    int countUserMemberRole(@Param("userId") Long userId);

    List<Long> findUserLegalOrganization(@Param("userId") Long userId);

    /**
     * 查询用户所有角色ID
     *
     * @param userId 用户ID
     * @return 角色ID
     */
    List<Long> selectUserRole(@Param("userId") Long userId);

}
