package com.ttdo.oauth.infra.repository.impl;

import com.ttdo.oauth.domain.entity.User;
import com.ttdo.oauth.domain.repository.UserRepository;
import com.ttdo.oauth.infra.mapper.UserPlusMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryImpl implements UserRepository {


    @Autowired
    private UserPlusMapper userPlusMapper;


    @Override
    public User selectLoginUserByEmail(String email, String userType) {
        User param = new User();
        param.setEmail(email);
        return userPlusMapper.selectLoginUser(param);
    }

    @Override
    public User selectLoginUserByPhone(String internationalTelCode, String phone, String userType) {
        User param = new User();
        param.setInternationalTelCode(internationalTelCode);
        param.setPhone(phone);
        return userPlusMapper.selectLoginUser(param);
    }

    @Override
    public User selectLoginUserByPhone(String phone, String userType) {
        User param = new User();
        param.setPhone(phone);
        return userPlusMapper.selectLoginUser(param);
    }

    @Override
    public User selectLoginUserByLoginName(String loginName, String userType) {
        User param = new User();
        param.setLoginName(loginName);
        return userPlusMapper.selectLoginUser(param);
    }
}
