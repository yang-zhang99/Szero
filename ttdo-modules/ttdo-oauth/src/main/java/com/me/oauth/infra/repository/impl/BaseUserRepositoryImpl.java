package com.me.oauth.infra.repository.impl;


import com.me.core.user.UserType;
import com.me.oauth.domain.entity.BaseUser;
import com.me.oauth.domain.repository.BaseUserRepository;
import org.springframework.stereotype.Component;

/**
 *
 * @author bojiangzhou 2019/08/06
 */
@Component
public class BaseUserRepositoryImpl  implements BaseUserRepository {

    @Override
    public boolean existsByLoginName(String loginName) {
        BaseUser params = new BaseUser();
        params.setLoginName(loginName);
//        return selectCount(params) > 0;
        return true;
    }

    @Override
    public boolean existsByPhone(String phone, UserType userType) {
        BaseUser params = new BaseUser();
        params.setPhone(phone);
        params.setUserType(userType.value());
//        return selectCount(params) > 0;
        return true;
    }

    @Override
    public boolean existsByEmail(String email, UserType userType) {
        BaseUser params = new BaseUser();
        params.setEmail(email);
        params.setUserType(userType.value());
//        return selectCount(params) > 0;
        return true;
    }

    @Override
    public boolean existsUser(String loginName, String phone, String email, UserType userType) {
        return existsByLoginName(loginName) || existsByPhone(phone, userType) || existsByEmail(email, userType);
    }
}
