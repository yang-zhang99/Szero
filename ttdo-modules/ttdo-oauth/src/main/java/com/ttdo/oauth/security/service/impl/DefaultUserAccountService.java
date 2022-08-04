package com.ttdo.oauth.security.service.impl;

import com.ttdo.oauth.domain.entity.User;
import com.ttdo.oauth.domain.repository.UserRepository;
import com.ttdo.oauth.security.constant.LoginField;
import com.ttdo.oauth.security.constant.LoginType;
import com.ttdo.oauth.security.service.UserAccountService;
import com.yang.core.util.Regexs;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.ttdo.oauth.security.constant.LoginField.*;
import static com.yang.core.base.BaseConstants.Symbol.MIDDLE_LINE;


@Component

public class DefaultUserAccountService implements UserAccountService {


    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserAccountService.class);

    private UserRepository userRepository;

    @Override
    public User findLoginUser(String username, String userType) {
        return findLoginUser(LoginType.ACCOUNT, username, userType);
    }


    /**
     * 根据登录方式查找用户
     *
     * @param loginType {@link LoginType}
     * @param username  用户名
     * @return UserDO
     */
    @Override
    public User findLoginUser(LoginType loginType, String username, String userType) {
        User user;
        switch (loginType) {
            case ACCOUNT:
                user = queryByLoginField(username, userType);
                break;
            case SMS:
                user = queryByPhone(username, userType);
                break;
            default:
                user = null;
                break;
        }

        return user;
    }

    @Override
    public boolean isNeedCaptcha(User user) {
//        BaseUser baseUser = null;
//        if (user != null) {
//            baseUser = new BaseUser(user.getId(), user.getLoginName(), user.getOrganizationId(), user.getLocked());
//        }
//        return passwordPolicyManager.isNeedCaptcha(baseUser);
        // 查询是否需要验证码
        return false;
    }

    protected User queryByLoginField(String account, String userType) {
        if (StringUtils.isBlank(account)) {
            return null;
        }

        User user = null;
        LoginField loginField = null;
        if (Regexs.isEmail(account)) {
            user = userRepository.selectLoginUserByEmail(account, userType);
            loginField = EMAIL;
        } else if (StringUtils.contains(account, MIDDLE_LINE)) {
            String[] arr = StringUtils.split(account, MIDDLE_LINE, 2);
            String crownCode = arr[0];
            String mobile = arr[1];
            if (Regexs.isNumber(crownCode) && Regexs.isNumber(mobile) && Regexs.isMobile(crownCode, mobile)) {
                user = userRepository.selectLoginUserByPhone(crownCode, mobile, userType);
                loginField = PHONE;
            }
        } else if (Regexs.isNumber(account) && Regexs.isMobile(account)) {
            user = userRepository.selectLoginUserByPhone(account, userType);
            loginField = PHONE;
        }

        if (user == null) {
            user = userRepository.selectLoginUserByLoginName(account, userType);
            loginField = USERNAME;
        }

        if (user != null) {
            user.setLoginField(loginField);
        }

        return user;
    }

    protected User queryByPhone(String account, String userType) {
        if (StringUtils.isBlank(account)) {
            return null;
        }

        User user = null;
        // 国际冠码手机号
        if (StringUtils.contains(account, MIDDLE_LINE)) {
            String[] arr = StringUtils.split(account, MIDDLE_LINE, 2);
            String crownCode = arr[0];
            String mobile = arr[1];
            if (Regexs.isNumber(crownCode) && Regexs.isNumber(mobile) && Regexs.isMobile(crownCode, mobile)) {
                user = userRepository.selectLoginUserByPhone(crownCode, mobile, userType);
            }
        } else if (Regexs.isNumber(account) && Regexs.isMobile(account)) {
            user = userRepository.selectLoginUserByPhone(account, userType);
        }

        if (user != null) {
            user.setLoginField(PHONE);
        }

        return user;
    }
}
