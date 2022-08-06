package com.ttdo.oauth.strategy.password;

import com.ttdo.oauth.domain.entity.BaseUser;
import com.ttdo.oauth.policy.PasswordPolicyMap;
import com.ttdo.oauth.policy.PasswordPolicyType;
import com.ttdo.oauth.strategy.PasswordStrategy;
import com.yang.core.exception.CommonException;
import org.springframework.stereotype.Component;

/**
 * @author wuguokai
 */
@Component
public class UppercaseCountStrategy implements PasswordStrategy {
    private static final String ERROR_MESSAGE = "hoth.warn.password.policy.upperCase";
    public static final String TYPE = PasswordPolicyType.UPPERCASE_COUNT.getValue();

    @Override
    public Object validate(PasswordPolicyMap policyMap, BaseUser user, String password) {
        Integer min = (Integer) policyMap.getPasswordConfig().get(TYPE);
        if (min != null && min != 0) {
            int count = 0;
            for (char c : password.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    count++;
                }
            }
            if (count < min) {
                throw new CommonException(ERROR_MESSAGE, min);
            }
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
