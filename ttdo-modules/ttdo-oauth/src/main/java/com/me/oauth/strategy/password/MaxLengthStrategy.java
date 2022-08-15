package com.me.oauth.strategy.password;

import com.me.oauth.domain.entity.BaseUser;
import com.me.oauth.policy.PasswordPolicyMap;
import com.me.oauth.policy.PasswordPolicyType;
import com.me.oauth.strategy.PasswordStrategy;
import com.yang.core.exception.CommonException;
import org.springframework.stereotype.Component;

/**
 * @author wuguokai
 */
@Component
public class MaxLengthStrategy implements PasswordStrategy {
    private static final String ERROR_MESSAGE = "hoth.warn.password.policy.maxLength";
    private static final String TYPE = PasswordPolicyType.MAX_LENGTH.getValue();

    @Override
    public Object validate(PasswordPolicyMap policyMap, BaseUser user, String password) {
        Integer max = (Integer) policyMap.getPasswordConfig().get(TYPE);
        if (max != null && max != 0 && password.length() > max) {
            throw new CommonException(ERROR_MESSAGE, max);
        }
        return null;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Object parseConfig(Object value) {
        return null;
    }
}
