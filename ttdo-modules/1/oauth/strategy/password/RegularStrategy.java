package com.me.oauth.strategy.password;

import com.me.oauth.domain.entity.BaseUser;
import com.me.oauth.policy.PasswordPolicyMap;
import com.me.oauth.policy.PasswordPolicyType;
import com.me.oauth.strategy.PasswordStrategy;
import com.yang.core.exception.CommonException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wuguokai
 */
@Component
public class RegularStrategy implements PasswordStrategy {
    private static final String ERROR_MESSAGE = "hoth.warn.password.policy.regular";
    public static final String TYPE = PasswordPolicyType.REGULAR.getValue();

    @Override
    public Object validate(PasswordPolicyMap policyMap, BaseUser user, String password) {
        Object reg = policyMap.getPasswordConfig().get(TYPE);
        if (reg instanceof String) {
            Pattern pattern = Pattern.compile((String) reg);
            Matcher matcher = pattern.matcher(password);
            if (!matcher.matches()) {
                throw new CommonException(ERROR_MESSAGE, reg);
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
