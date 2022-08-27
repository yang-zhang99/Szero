package com.me.oauth.domain.repository;


import com.me.oauth.domain.entity.BaseLdap;

/**
 *
 * @author bojiangzhou 2019/08/06
 */
public interface BaseLdapRepository  {

    /**
     * 查询租户的Ldap
     *
     * @param tenantId 租户ID
     */
    BaseLdap selectLdap(Long tenantId);

    /**
     * 缓存 Ldap
     * 
     * @param ldap Ldap
     */
    void saveLdap(BaseLdap ldap);

    /**
     * 删除Ldap缓存
     *
     * @param ldap Ldap
     */
    void deleteLdap(BaseLdap ldap);

}
