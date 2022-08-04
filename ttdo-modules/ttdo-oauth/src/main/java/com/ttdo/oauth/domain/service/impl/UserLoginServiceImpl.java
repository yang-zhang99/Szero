package com.ttdo.oauth.domain.service.impl;

import com.ttdo.oauth.domain.entity.User;
import com.ttdo.oauth.domain.service.UserLoginService;
import com.ttdo.oauth.security.constant.SecurityAttributes;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户登录业务默认实现
 *
 * @author bojiangzhou 2019/02/25
 */
@Component
public class  UserLoginServiceImpl implements UserLoginService {

//    @Autowired
//    private CaptchaMessageHelper captchaMessageHelper;
//    @Autowired
//    private MessageClient messageClient;
//    @Autowired
//    private UserAccountService userAccountService;
//    @Autowired
//    private BaseClientRepository clientRepository;
//    @Autowired
//    private MobileLoginTokenService mobileLoginTokenService;
//    @Autowired
//    private OpenLoginTokenService openLoginTokenService;
//    @Autowired
//    private PasswordPolicyManager passwordPolicyManager;
//    @Autowired
//    private BaseOpenAppRepository baseOpenAppRepository;
//    @Autowired
//    private BaseUserRepository baseUserRepository;
//    @Autowired
//    private ConfigGetter configGetter;
//    @Autowired
//    private SocialUserBindService socialUserBindService;

    /**
     * 在登录页面根据用户名获取登录用户时，直接从session获取，登录失败时将用户信息放入session中.
     * @param request HttpServletRequest
     */

    @Override
    public User queryRequestUser(HttpServletRequest request) {
        User result = null;
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute(SecurityAttributes.SECURITY_LOGIN_USERNAME);
        if (username != null) {
            result = (User) session.getAttribute(SecurityAttributes.SECURITY_LOGIN_USER);
        }
        return result;
    }
//
//    @Override
//    public boolean isNeedCaptcha(User user) {
//        return userAccountService.isNeedCaptcha(user);
//    }
//
//    @Override
//    public List<BaseOpenApp> queryOpenLoginWays(String channel) {
//        List<BaseOpenApp> apps = baseOpenAppRepository.getOpenApps(channel);
//        if (CollectionUtils.isEmpty(apps)) {
//            return Collections.emptyList();
//        }
//        apps.forEach(app -> {
//            app.setAppId(null);
//            app.setAppKey(null);
//        });
//        return apps;
//    }
//
//    @Override
//    public List<BaseOpenApp> queryOpenLoginWays(HttpServletRequest request) {
//        String channel = StringUtils.defaultIfBlank(request.getParameter(SocialConstant.PARAM_CHANNEL), ChannelEnum.pc.name());
//        return queryOpenLoginWays(channel);
//    }
//
//    @Override
//    public CaptchaResult sendPhoneCaptcha(String internationalTelCode, String phone, UserType userType,
//                                          String businessScope, boolean checkRegistered) {
//        CaptchaResult captchaResult = null;
//        if (checkRegistered) {
//            if (!baseUserRepository.existsByPhone(phone, userType)) {
//                captchaResult = new CaptchaResult();
//                captchaResult.setSuccess(false);
//                captchaResult.setCode(LoginExceptions.PHONE_NOT_FOUND.value());
//                captchaResult.setMessage(MessageAccessor.getMessage(LoginExceptions.PHONE_NOT_FOUND.value()).desc());
//                return captchaResult;
//            }
//        }
//
//        captchaResult = captchaMessageHelper.generateMobileCaptcha(internationalTelCode, phone, userType,
//                businessScope, HZeroService.Oauth.CODE);
//
//        if (!captchaResult.isSuccess()) {
//            captchaResult.clearCaptcha();
//            return captchaResult;
//        }
//
//        Map<String, String> params = new HashMap<>(2);
//        params.put(CaptchaResult.FIELD_CAPTCHA, captchaResult.getCaptcha());
//        try {
//            messageClient.async().sendMessage(BaseConstants.DEFAULT_TENANT_ID, configGetter.getValue(ProfileCode.MSG_CODE_MOBILE_LOGIN), null,
//                    Collections.singletonList(new Receiver().setPhone(phone).setIdd(internationalTelCode)), params, Collections.singletonList("SMS"));
//        } catch (Exception e) {
//            captchaResult.setSuccess(false);
//            captchaResult.setMessage(MessageAccessor.getMessage("hoth.warn.captcha.sendPhoneCaptchaError").desc());
//        }
//
//        captchaResult.clearCaptcha();
//
//        return captchaResult;
//    }
//
//    @Override
//    public Map<String, Object> getLoginInitParams(HttpServletRequest request) {
//        Map<String, Object> result = new HashMap<>(2);
//
//        String clientId = request.getParameter(LoginUtil.FIELD_CLIENT_ID);
//        String username = request.getParameter(LoginUtil.FIELD_USERNAME);
//        String userType = request.getParameter(UserType.PARAM_NAME);
//
//        Long organizationId = null;
//        User user = null;
//        //登录验证码策略
//        if (StringUtils.isNotBlank(clientId)) {
//            BaseClient client = clientRepository.selectClient(clientId);
//            if (client != null) {
//                organizationId = client.getOrganizationId();
//            }
//        }
//
//        if (StringUtils.isNotBlank(username)) {
//            user = userAccountService.findLoginUser(username, UserType.ofDefault(userType));
//            if (user != null) {
//                organizationId = user.getOrganizationId();
//            }
//        }
//
//        // 是否需要验证码
//        result.put(SecurityAttributes.FIELD_IS_NEED_CAPTCHA, passwordPolicyManager.isNeedCaptcha(organizationId));
//        // 三方登录方式
//        result.put(SecurityAttributes.FIELD_OPEN_LOGIN_WAYS, queryOpenLoginWays(request));
//
//        return result;
//    }
//
//    @Override
//    public AuthenticationResult loginMobileForToken(HttpServletRequest request) {
//        return mobileLoginTokenService.loginForToken(request);
//    }
//
//    @Override
//    public AuthenticationResult loginOpenForToken(HttpServletRequest request) {
//        return openLoginTokenService.loginForToken(request);
//    }
//
//    @Override
//    public AuthenticationResult bindOpenAccount(HttpServletRequest request) {
//        return socialUserBindService.bindOpenAccount(request);
//    }

}
