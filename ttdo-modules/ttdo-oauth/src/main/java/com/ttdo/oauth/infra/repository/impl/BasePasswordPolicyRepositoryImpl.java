package com.ttdo.oauth.infra.repository.impl;


import java.util.Optional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ttdo.oauth.domain.entity.BasePasswordPolicy;
import com.ttdo.oauth.domain.repository.BasePasswordPolicyRepository;
import com.ttdo.oauth.infra.mapper.BasePasswordPolicyMapper;
import com.yang.common.HZeroService;
import com.yang.core.base.BaseConstants;
import com.yang.redis.RedisHelper;
import com.yang.redis.safe.SafeRedisHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class BasePasswordPolicyRepositoryImpl  implements BasePasswordPolicyRepository {
    private static final String PASSWORD_POLICY_KEY = "hoth:password_policy";
    private final RedisHelper redisHelper;

    @Autowired
    private BasePasswordPolicyMapper basePasswordPolicyMapper;

    public BasePasswordPolicyRepositoryImpl(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    public BasePasswordPolicy selectPasswordPolicy(Long tenantId) {
        Assert.notNull(tenantId, "tenantId not be null.");
        String str = (String) SafeRedisHelper.execute(HZeroService.Oauth.REDIS_DB, () -> {
            return this.redisHelper.hshGet("hoth:password_policy", tenantId.toString());
        });
        BasePasswordPolicy passwordPolicy;
        if (StringUtils.isNotBlank(str)) {
            passwordPolicy = (BasePasswordPolicy)this.redisHelper.fromJson(str, BasePasswordPolicy.class);
        } else {
            BasePasswordPolicy param = new BasePasswordPolicy();
            param.setOrganizationId(tenantId);
//            passwordPolicy = (BasePasswordPolicy)this.selectOne(param);
            QueryWrapper<BasePasswordPolicy> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("tenant_id",tenantId );
            passwordPolicy =   basePasswordPolicyMapper.selectOne(queryWrapper);
            if (passwordPolicy != null) {
                this.savePasswordPolicy(passwordPolicy);
            } else {
                str = (String)SafeRedisHelper.execute(HZeroService.Oauth.REDIS_DB, () -> {
                    return this.redisHelper.hshGet("hoth:password_policy", BaseConstants.DEFAULT_TENANT_ID.toString());
                });
                passwordPolicy = (BasePasswordPolicy)this.redisHelper.fromJson(str, BasePasswordPolicy.class);
            }
        }

        return (BasePasswordPolicy)Optional.ofNullable(passwordPolicy).orElseThrow(() -> {
            return new AuthenticationServiceException("hoth.warn.password.passwordPolicy.notFound");
        });
    }

    public void savePasswordPolicy(BasePasswordPolicy basePasswordPolicy) {
        Assert.notNull(basePasswordPolicy, "basePasswordPolicy not be null.");
        SafeRedisHelper.execute(HZeroService.Oauth.REDIS_DB, this.redisHelper, () -> {
            this.redisHelper.hshPut("hoth:password_policy", basePasswordPolicy.getOrganizationId().toString(), this.redisHelper.toJson(basePasswordPolicy));
        });
    }
}
