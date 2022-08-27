package com.me.oauth.security.custom;

import com.me.oauth.domain.entity.User;
import com.me.oauth.security.exception.CustomAuthenticationException;
import com.me.oauth.security.exception.LoginExceptions;
import com.me.oauth.security.service.LoginRecordService;
import com.me.oauth.security.service.UserAccountService;
import com.me.oauth.security.service.UserDetailsBuilder;
import com.me.core.exception.CommonException;
import com.me.core.user.UserType;
import com.me.oauth.security.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);


    private final UserAccountService userAccountService;
    private final UserDetailsBuilder userDetailsBuilder;
    private final LoginRecordService loginRecordService;

    private static final ThreadLocal<UserDetails> LOCAL_USER_DETAILS = new ThreadLocal<>();

    public CustomUserDetailsService(UserAccountService userAccountService,
                                    UserDetailsBuilder userDetailsBuilder,
                                    LoginRecordService loginRecordService) {
        this.userAccountService = userAccountService;
        this.userDetailsBuilder = userDetailsBuilder;
        this.loginRecordService = loginRecordService;
    }

    /**
     * 通过用户名加载用户对象
     *
     * @param username 用户名
     * @return 用户对象，带权限列表
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDetails userDetails = LOCAL_USER_DETAILS.get();
        if (userDetails != null) {
            return userDetails;
        }

        User user = getLoginRecordService().getLocalLoginUser();
        if (user == null) {
            String userType = RequestUtil.getParameterValueFromRequestOrSavedRequest(UserType.PARAM_NAME, UserType.DEFAULT_USER_TYPE);
            user = getUserAccountService().findLoginUser(username, UserType.ofDefault(userType));
            LOGGER.debug("loaded user, userType is {}, user is {}", userType, user);
            if (user == null) {
                throw new CustomAuthenticationException(LoginExceptions.USERNAME_OR_PASSWORD_ERROR.value());
            }
            getLoginRecordService().saveLocalLoginUser(user);
        }
        LOGGER.debug("loading login user, username={}, userType={}", username, user.getUserType());

        userDetails = getUserDetailsBuilder().buildUserDetails(user);

        LOCAL_USER_DETAILS.set(userDetails);

        return userDetails;
    }

    protected UserAccountService getUserAccountService() {
        return userAccountService;
    }

    protected UserDetailsBuilder getUserDetailsBuilder() {
        return userDetailsBuilder;
    }

    protected LoginRecordService getLoginRecordService() {
        return loginRecordService;
    }

    public static void clearLocalResource() {
        LOCAL_USER_DETAILS.remove();
    }
}
