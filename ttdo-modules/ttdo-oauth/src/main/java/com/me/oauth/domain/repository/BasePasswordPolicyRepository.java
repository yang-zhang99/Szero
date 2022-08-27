package com.me.oauth.domain.repository;


import com.me.oauth.domain.entity.BasePasswordPolicy;

import java.util.List;

public interface BasePasswordPolicyRepository  {

    /**
     * 查询租户的密码策略
     *
     * @param tenantId 租户ID
     */
    BasePasswordPolicy selectPasswordPolicy(Long tenantId);

    /**
     * 缓存密码策略
     * @param basePasswordPolicy 密码策略
     */
    void savePasswordPolicy(BasePasswordPolicy basePasswordPolicy);

    /**
     * 批量缓存密码策略
     */
    void batchSavePasswordPolicy(List<BasePasswordPolicy> basePasswordPolicyList);
}
