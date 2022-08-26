package com.me.oauth.security.custom;

import com.me.oauth.domain.entity.User;
import com.me.oauth.security.service.LoginRecordService;
import com.me.oauth.security.service.UserAccountService;
import com.me.oauth.security.service.UserDetailsBuilder;
import com.yang.core.exception.CommonException;
import com.yang.core.user.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private static final ThreadLocal<UserDetails> LOCAL_USER_DETAILS = new ThreadLocal<>();

    private final UserAccountService userAccountService;

    private final LoginRecordService loginRecordService;
    @Autowired
    private UserDetailsBuilder userDetailsBuilder;

    public CustomUserDetailsService(UserAccountService userAccountService, LoginRecordService loginRecordService) {
        this.userAccountService = userAccountService;
        this.loginRecordService = loginRecordService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取当前线程池的用户信息
        UserDetails userDetails = LOCAL_USER_DETAILS.get();
        if (userDetails != null) {
            return userDetails;
        }
        User user = userAccountService.findLoginUser(username, UserType.ofDefault());
        LOGGER.debug("loaded user, user is {}", user);
        // 获取线程池用户信息
        if (user == null) {
            throw new CommonException("用户名和密码错误");
        }
        userDetails = userDetailsBuilder.buildUserDetails(user);
        LOCAL_USER_DETAILS.set(userDetails);
        return userDetails;

    }

    protected UserAccountService getUserAccountService() {
        return userAccountService;
    }

    protected LoginRecordService getLoginRecordService() {
        return loginRecordService;
    }
}
