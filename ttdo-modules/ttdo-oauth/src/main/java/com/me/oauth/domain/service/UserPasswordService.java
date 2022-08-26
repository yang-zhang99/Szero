package com.me.oauth.domain.service;

public interface UserPasswordService {
    void updateUserPassword(Long userId, String newPassword);

    void resetUserPassword(Long userId, Long tenantId, boolean ldapUpdatable);

    void updateUserPassword(Long userId, String newPassword, boolean ldapUpdatable);

    String getTenantDefaultPassword(Long tenantId);
}
