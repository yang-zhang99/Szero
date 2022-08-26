package com.me.oauth.security.service;

import com.me.oauth.domain.entity.User;
import com.yang.core.oauth.CustomUserDetails;

/**
 * 构建 CustomUserDetails 将 持久化实体与符合 UserDetails 契约的类做耦合
 */
public interface UserDetailsBuilder {

    /**
     * 构建 CustomUserDetails
     *
     * @param user User
     * @return CustomUserDetails
     */
    CustomUserDetails buildUserDetails(User user);

}
