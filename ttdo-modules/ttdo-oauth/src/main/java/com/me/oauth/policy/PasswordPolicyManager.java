package com.me.oauth.policy;


import com.me.oauth.domain.entity.BasePasswordPolicy;
import com.me.oauth.domain.entity.BaseUser;
import com.me.oauth.domain.repository.BasePasswordPolicyRepository;

import com.me.oauth.domain.service.PasswordErrorTimesService;
import com.me.oauth.policy.PasswordPolicyMap;
import com.me.oauth.strategy.PasswordStrategy;
import com.me.oauth.strategy.PasswordStrategyStore;
import com.me.core.exception.CommonException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class PasswordPolicyManager {

    private static final String ERROR_EMPTY = "error.password.null";

    @Value("${hzero.oauth.enable-always-captcha:false}")
    private boolean alwaysEnableCaptcha;

    private final PasswordStrategyStore passwordStrategyStore;
    private final PasswordErrorTimesService passwordErrorTimesService;
    private final BasePasswordPolicyRepository basePasswordPolicyRepository;

    public PasswordPolicyManager(PasswordStrategyStore passwordStrategyStore,
                                 PasswordErrorTimesService passwordErrorTimesService,
                                 BasePasswordPolicyRepository basePasswordPolicyRepository) {
        this.passwordStrategyStore = passwordStrategyStore;
        this.passwordErrorTimesService = passwordErrorTimesService;
        this.basePasswordPolicyRepository = basePasswordPolicyRepository;
    }

    public void passwordValidate(String password, Long tenantId) {
        passwordValidate(password, tenantId, null);
    }

    public void passwordValidate(String password, Long tenantId, BaseUser baseUser) {
        if (password == null) {
            throw new CommonException(ERROR_EMPTY);
        }
        BasePasswordPolicy passwordPolicy = basePasswordPolicyRepository.selectPasswordPolicy(tenantId);
        baseUser = Optional.ofNullable(baseUser).orElse(new BaseUser(null, null, tenantId));
        PasswordPolicyMap passwordPolicyMap = PasswordPolicyMap.parse(passwordPolicy);
        if (passwordPolicyMap.isEnablePassword()) {
            for (PasswordStrategy p : getPasswordProviders(passwordPolicyMap, passwordStrategyStore)) {
                p.validate(passwordPolicyMap, baseUser, password);
            }
        }
    }

    /**
     * 判断是否需要显示图形验证码
     * @param user 用户信息
     * @return true - 是
     */
    public boolean isNeedCaptcha(BaseUser user) {
        if (alwaysEnableCaptcha) {
            return true;
        }
        if (user == null) {
            return false;
        }
        if (user.getLocked() != null && user.getLocked()) {
            return false;
        }
        BasePasswordPolicy passwordPolicy = basePasswordPolicyRepository.selectPasswordPolicy(user.getOrganizationId());
        // 登录安全策略禁用时禁止开启图形验证码及用户锁定
        if (passwordPolicy == null || !passwordPolicy.getEnableSecurity()) {
            return false;
        }

        boolean enableCaptcha = Optional.ofNullable(passwordPolicy.getEnableCaptcha()).orElse(false);
        long maxCaptchaTime = Optional.ofNullable(passwordPolicy.getMaxCheckCaptcha()).orElse(0);
        long loginErrorTime = passwordErrorTimesService.getErrorTimes(user.getId());

        return enableCaptcha && maxCaptchaTime >= 0 && loginErrorTime >= maxCaptchaTime;
    }

    public boolean isNeedCaptcha(Long tenantId) {
        if (alwaysEnableCaptcha) {
            return true;
        }
        BasePasswordPolicy passwordPolicy = basePasswordPolicyRepository.selectPasswordPolicy(tenantId);
        // 登录安全策略禁用时禁止开启图形验证码及用户锁定
        if (passwordPolicy == null || !passwordPolicy.getEnableSecurity()) {
            return false;
        }

        return Optional.ofNullable(passwordPolicy.getEnableCaptcha()).orElse(false);
    }

    private List<PasswordStrategy> getPasswordProviders(PasswordPolicyMap policy, PasswordStrategyStore store) {
        LinkedList<PasswordStrategy> list = new LinkedList<>();
        for (String id : policy.getPasswordPolicies()) {
            PasswordStrategy provider = store.getProvider(id);
            if (provider != null) {
                list.add(provider);
            }
        }
        return list;
    }
}
