package com.ttdo.oauth.strategy;


import com.ttdo.oauth.domain.entity.BaseUser;
import com.ttdo.oauth.policy.PasswordPolicyMap;

/**
 * @author wuguokai
 */
public interface PasswordStrategy {

    <T> T validate(PasswordPolicyMap policyMap, BaseUser user, String password);

    String getType();

    Object parseConfig(Object value);
}
