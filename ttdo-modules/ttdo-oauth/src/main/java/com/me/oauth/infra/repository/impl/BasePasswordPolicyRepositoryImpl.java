package com.me.oauth.infra.repository.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.me.oauth.domain.entity.BasePasswordPolicy;
import com.me.oauth.domain.repository.BasePasswordPolicyRepository;
import com.me.oauth.infra.mapper.BasePasswordPolicyMapper;
import com.me.common.HZeroService;
import com.me.core.base.BaseConstants;
import com.me.redis.RedisHelper;
import com.me.redis.safe.SafeRedisHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BasePasswordPolicyRepositoryImpl implements BasePasswordPolicyRepository {

    private static final String PASSWORD_POLICY_KEY = "hoth:password_policy";
    private final RedisHelper redisHelper;

    @Autowired
    private BasePasswordPolicyMapper basePasswordPolicyMapper;

    public BasePasswordPolicyRepositoryImpl(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    public BasePasswordPolicy selectPasswordPolicy(Long tenantId) {
        Assert.notNull(tenantId, "tenantId not be null.");
        String str = SafeRedisHelper.execute(HZeroService.Oauth.REDIS_DB,
                () -> this.redisHelper.hshGet("hoth:password_policy", tenantId.toString()));
        BasePasswordPolicy passwordPolicy;
        if (StringUtils.isNotBlank(str)) {
            passwordPolicy = this.redisHelper.fromJson(str, BasePasswordPolicy.class);
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
                str = SafeRedisHelper.execute(HZeroService.Oauth.REDIS_DB, () ->
                        this.redisHelper.hshGet("hoth:password_policy", BaseConstants.DEFAULT_TENANT_ID.toString()));
                passwordPolicy = this.redisHelper.fromJson(str, BasePasswordPolicy.class);
            }
        }

        return Optional.ofNullable(passwordPolicy).orElseThrow(
                () -> new AuthenticationServiceException("hoth.warn.password.passwordPolicy.notFound"));
    }

    @Override
    public void savePasswordPolicy(BasePasswordPolicy basePasswordPolicy) {
        Assert.notNull(basePasswordPolicy, "basePasswordPolicy not be null.");
        SafeRedisHelper.execute(HZeroService.Oauth.REDIS_DB, redisHelper,
                () -> redisHelper.hshPut(PASSWORD_POLICY_KEY, basePasswordPolicy.getOrganizationId().toString(), redisHelper.toJson(basePasswordPolicy)));
    }

    @Override
    public void batchSavePasswordPolicy(List<BasePasswordPolicy> basePasswordPolicyList) {
        if (CollectionUtils.isEmpty(basePasswordPolicyList)) {
            return;
        }
        Map<String, String> map = basePasswordPolicyList.stream()
                .collect(Collectors.toMap(pp -> pp.getOrganizationId().toString(), redisHelper::toJson));

        SafeRedisHelper.execute(HZeroService.Oauth.REDIS_DB, redisHelper,
                () -> redisHelper.hshPutAll(PASSWORD_POLICY_KEY, map));
    }
}
