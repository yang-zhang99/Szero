package com.me.oauth.strategy.password;

import com.me.oauth.domain.entity.BaseUser;
import com.me.oauth.policy.PasswordPolicyMap;
import com.me.oauth.policy.PasswordPolicyType;
import com.me.oauth.strategy.PasswordStrategy;
import com.me.core.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author wuguokai
 */
@Component
public class NotUsernameStrategy implements PasswordStrategy {
    private static final String ERROR_MESSAGE = "hoth.warn.password.policy.notUsername";
    public static final String TYPE = PasswordPolicyType.NOT_USERNAME.getValue();

    @Override
    public Object validate(PasswordPolicyMap policyMap, BaseUser user, String password) {
        Boolean notUsername = (Boolean) policyMap.getPasswordConfig().get(TYPE);
        if ((!notUsername) && StringUtils.isNotBlank(user.getLoginName())) {
            String userName = user.getLoginName();
            if (password.equals(userName)) {
                throw new CommonException(ERROR_MESSAGE);
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
