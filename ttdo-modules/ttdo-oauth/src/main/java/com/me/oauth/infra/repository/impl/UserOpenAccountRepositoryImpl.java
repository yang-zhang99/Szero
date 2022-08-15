package com.me.oauth.infra.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.me.oauth.domain.entity.UserOpenAccount;
import com.me.oauth.domain.repository.UserOpenAccountRepository;
import com.me.oauth.infra.mapper.UserOpenAccountMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 */
@Component
public class UserOpenAccountRepositoryImpl implements UserOpenAccountRepository {

    @Autowired
    private UserOpenAccountMapper userOpenAccountMapper;


    @Override
    public List<String> selectUsernameByOpenId(String openAppCode, String openId) {
        if (StringUtils.isAnyBlank(openAppCode, openId)) {
            return Collections.emptyList();
        }
        UserOpenAccount openAccount = new UserOpenAccount();
        openAccount.setOpenAppCode(openAppCode);
        openAccount.setOpenId(openId);
        List<UserOpenAccount> accounts = userOpenAccountMapper.selectListByUserOpenAccount(openAccount);
        return accounts.stream().map(UserOpenAccount::getUsername).collect(Collectors.toList());
    }

    @Override
    public List<String> selectUsernameByUnionId(String openAppCode, String unionId) {
        if (StringUtils.isAnyBlank(openAppCode, unionId)) {
            return Collections.emptyList();
        }
        UserOpenAccount openAccount = new UserOpenAccount();
        openAccount.setOpenAppCode(openAppCode);
        openAccount.setUnionId(unionId);
        List<UserOpenAccount> accounts = userOpenAccountMapper.selectListByUserOpenAccount(openAccount);
        return accounts.stream().map(UserOpenAccount::getUsername).collect(Collectors.toList());
    }

    @Override
    public List<UserOpenAccount> selectOpenAccountByUsername(String openAppCode, String username) {
        UserOpenAccount openAccount = new UserOpenAccount();
        openAccount.setOpenAppCode(openAppCode);
        openAccount.setUsername(username);
        return userOpenAccountMapper.selectListByUserOpenAccount(openAccount);
    }

    @Override
    public UserOpenAccount selectOpenAccount(String openAppCode, String username) {
        QueryWrapper<UserOpenAccount> openAccount = new QueryWrapper<>();
        openAccount.eq("open_app_code", openAppCode);
        openAccount.eq("user_name", username);
//        openAccount.setOpenAppCode(openAppCode);
//        openAccount.setUsername(username);
        return userOpenAccountMapper.selectOne(openAccount);
    }
}
