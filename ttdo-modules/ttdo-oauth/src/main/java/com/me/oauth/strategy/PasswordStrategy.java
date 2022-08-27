package com.me.oauth.strategy;


import com.me.oauth.domain.entity.BaseUser;
import com.me.oauth.policy.PasswordPolicyMap;

/**
 * @author wuguokai
 */
public interface PasswordStrategy {

    <T> T validate(PasswordPolicyMap policyMap, BaseUser user, String password);

    String getType();

    Object parseConfig(Object value);
}
