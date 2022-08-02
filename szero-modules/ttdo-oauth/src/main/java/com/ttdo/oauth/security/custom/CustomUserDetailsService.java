package com.ttdo.oauth.security.custom;

import com.ttdo.core.user.UserType;
import com.ttdo.oauth.domain.entity.User;
import com.ttdo.oauth.security.service.LoginRecordService;
import com.ttdo.oauth.security.service.UserAccountService;
import com.ttdo.oauth.security.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private static final ThreadLocal<UserDetails> LOCAL_USER_DETAILS = new ThreadLocal<>();

    private final UserAccountService userAccountService;

    private final LoginRecordService loginRecordService;

    public CustomUserDetailsService(LoginRecordService loginRecordService) {
        this.loginRecordService = loginRecordService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取当前线程池的用户信息
        UserDetails userDetails = LOCAL_USER_DETAILS.get();
        if (userDetails != null) {
            return userDetails;
        }
        // 获取线程池用户信息
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

        return null;
    }

    protected UserAccountService getUserAccountService() {
        return userAccountService;
    }

    protected LoginRecordService getLoginRecordService() {
        return loginRecordService;
    }
}
