package com.ttdo.oauth.domain.repository;


import com.ttdo.oauth.domain.entity.UserOpenAccount;

import java.util.List;

/**
 *
 */
public interface UserOpenAccountRepository  {


    List<String> selectUsernameByOpenId(String openAppCode, String openId);

    List<String> selectUsernameByUnionId(String openAppCode, String unionId);

    List<UserOpenAccount> selectOpenAccountByUsername(String openAppCode, String username);

    UserOpenAccount selectOpenAccount(String openAppCode, String username);
}
