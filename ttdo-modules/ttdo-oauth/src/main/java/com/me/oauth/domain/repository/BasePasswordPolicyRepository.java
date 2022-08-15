package com.me.oauth.domain.repository;


import com.me.oauth.domain.entity.BasePasswordPolicy;

public interface BasePasswordPolicyRepository  {
    BasePasswordPolicy selectPasswordPolicy(Long tenantId);

    void savePasswordPolicy(BasePasswordPolicy basePasswordPolicy);
}
