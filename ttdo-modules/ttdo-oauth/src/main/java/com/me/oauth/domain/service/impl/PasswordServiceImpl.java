package com.me.oauth.domain.service.impl;

import com.me.oauth.domain.entity.User;
import com.me.oauth.domain.repository.UserRepository;
import com.me.oauth.domain.service.PasswordService;
import com.me.oauth.domain.service.UserPasswordService;
import com.me.oauth.infra.constant.Constants;
import com.me.core.exception.CommonException;
import com.me.core.exception.MessageException;
import com.me.core.user.UserType;
import com.me.redis.captcha.CaptchaMessageHelper;
import com.me.redis.captcha.CaptchaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bojiangzhou 2019/03/04
 */
@Component
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private CaptchaMessageHelper captchaMessageHelper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPasswordService userPasswordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePasswordByAccount(String account, UserType userType, String businessScope,
                                        String password, String captchaKey, String captcha) {

        CaptchaResult captchaResult = captchaMessageHelper.checkCaptcha(captchaKey, captcha, account, userType,
                businessScope, Constants.APP_CODE, false);
        if (!captchaResult.isSuccess()) {
            throw new MessageException(captchaResult.getMessage(), captchaResult.getCode());
        }

        User user = userRepository.selectUserByPhoneOrEmail(account, userType);
        if (user == null) {
            throw new CommonException("hoth.warn.phoneOrEmailNotFound");
        }

        userPasswordService.updateUserPassword(user.getId(), password);
    }

    @Override
    public void updatePasswordByUser(Long userId, UserType userType, String password) {
        userPasswordService.updateUserPassword(userId, password);
    }

}
