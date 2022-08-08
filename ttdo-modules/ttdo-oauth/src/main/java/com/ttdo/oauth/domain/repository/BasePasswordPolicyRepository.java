package com.ttdo.oauth.domain.repository;


import com.ttdo.oauth.domain.entity.BasePasswordPolicy;

public interface BasePasswordPolicyRepository  {
    BasePasswordPolicy selectPasswordPolicy(Long tenantId);

    void savePasswordPolicy(BasePasswordPolicy basePasswordPolicy);
}
