package com.ttdo.oauth.api.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttdo.oauth.config.MultiLanguageConfig;
import com.ttdo.oauth.domain.entity.Language;
import com.ttdo.oauth.domain.entity.User;
import com.ttdo.oauth.domain.service.LanguageService;
import com.ttdo.oauth.domain.service.UserLoginService;
import com.ttdo.oauth.domain.utils.ConfigGetter;
import com.ttdo.oauth.domain.utils.ProfileCode;
import com.ttdo.oauth.security.config.SecurityProperties;
import com.ttdo.oauth.security.constant.LoginType;
import com.ttdo.oauth.security.constant.SecurityAttributes;
import com.ttdo.oauth.security.util.LoginUtil;
import com.ttdo.oauth.security.util.RequestUtil;
import com.yang.core.base.BaseConstants;
import com.yang.core.user.UserType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RefreshScope
@Controller
public class OauthController {

    // 登录的默认地址
    private static final String LOGIN_DEFAULT = "login";
    // 移动端默认的登录地址
    private static final String LOGIN_MOBILE = "login-mobile";
    private static final String PASS_EXPIRED_PAGE = "pass-expired";
    private static final String PASS_FORCE_MODIFY_PAGE = "pass-force-modify";
    private static final String OPEN_BIND_PAGE = "open-app-bind";
    //   /
    private static final String SLASH = BaseConstants.Symbol.SLASH;
    private final LanguageService languageService;

    private final ConfigGetter configGetter;

    private final SecurityProperties securityProperties;
    private final UserLoginService userLoginService;
    @Autowired
    private MultiLanguageConfig multiLanguageConfig;

    public OauthController(LanguageService languageService,
                           ConfigGetter configGetter,
                           SecurityProperties securityProperties,
                           UserLoginService userLoginService) {
        this.languageService = languageService;
        this.configGetter = configGetter;
        this.securityProperties = securityProperties;
        this.userLoginService = userLoginService;
    }


    /**
     * 默认登录页面
     * <p>
     * 如果不做匹配那么直接返回 templates 的 html 模板即可。
     */
    @GetMapping(value = "/login")
    public String login(HttpServletRequest request,
                        Model model,
                        HttpSession session,
                        @RequestParam(required = false) String device,
                        @RequestParam(required = false) String type
    ) throws JsonProcessingException {
        setPageDefaultData(request, session, model);
        // 登录模板
        String template = (String) session.getAttribute(LoginUtil.FIELD_TEMPLATE);
        // 登录页面
        String returnPage = "mobile".equals(device) ? LOGIN_MOBILE : LOGIN_DEFAULT;
        returnPage = template + SLASH + returnPage;
        // 登录方式
        type = LoginType.match(type) != null ? type : SecurityAttributes.DEFAULT_LOGIN_TYPE.code();
        model.addAttribute(SecurityAttributes.FIELD_LOGIN_TYPE, type);
        // 在 HttpSession 中查询用户名，然后查看此用户是否需要验证码
        User user = userLoginService.queryRequestUser(request);
        model.addAttribute(SecurityAttributes.FIELD_IS_NEED_CAPTCHA, userLoginService.isNeedCaptcha(user));
        // 错误消息
        String exceptionMessage = (String) session.getAttribute(SecurityAttributes.SECURITY_LAST_EXCEPTION);
        if (StringUtils.isNotBlank(exceptionMessage)) {
            model.addAttribute(SecurityAttributes.FIELD_LOGIN_ERROR_MSG, exceptionMessage);
        }

        String username = (String) session.getAttribute(SecurityAttributes.SECURITY_LOGIN_USERNAME);

        SecurityAttributes.removeSecuritySessionAttribute(session);
        if (StringUtils.isBlank(username)) {
            return returnPage;
        }

        model.addAttribute(SecurityAttributes.SECURITY_LOGIN_USERNAME, username);
        if (LoginType.SMS.code().equals(type)) {
            model.addAttribute(SecurityAttributes.SECURITY_LOGIN_MOBILE, username);
        }

        return returnPage;
    }

    // 设置页面的默认数据   request、session、model
    private void setPageDefaultData(HttpServletRequest request, HttpSession session, Model model) throws JsonProcessingException {
        // 模板
        String template = RequestUtil.getParameterValueFromRequestOrSavedRequest(request, LoginUtil.FIELD_TEMPLATE, configGetter.getValue(ProfileCode.OAUTH_DEFAULT_TEMPLATE));
        // 控制用户类型
        String userType = RequestUtil.getParameterValueFromRequestOrSavedRequest(request, UserType.PARAM_NAME, UserType.DEFAULT_USER_TYPE);
        // 控制登录字段
        String loginField = RequestUtil.getParameterValueFromRequestOrSavedRequest(request, LoginUtil.FIELD_LOGIN_FIELD, null);

        model.addAttribute(LoginUtil.FIELD_TEMPLATE, template);

        session.setAttribute(LoginUtil.FIELD_TEMPLATE, template);
        session.setAttribute(UserType.PARAM_NAME, userType);
        session.setAttribute(LoginUtil.FIELD_LOGIN_FIELD, loginField);
//
        // 是否加密
        if (securityProperties.getPassword().isEnableEncrypt()) {
//            String publicKey = encryptClient.getPublicKey();
//            model.addAttribute(LoginUtil.FIELD_PUBLIC_KEY, publicKey);
//            session.setAttribute(LoginUtil.FIELD_PUBLIC_KEY, publicKey);
        }
//        setCommonPageConfigData(model);
        // 三方登录方式
//        List<BaseOpenApp> apps = userLoginService.queryOpenLoginWays(request);
//        model.addAttribute(SecurityAttributes.FIELD_OPEN_LOGIN_WAYS, apps);
//        model.addAttribute(SecurityAttributes.FIELD_OPEN_LOGIN_WAYS_JSON, BaseConstants.MAPPER.writeValueAsString(apps));
        // 语言
        if (configGetter.isTrue(ProfileCode.OAUTH_SHOW_LANGUAGE)) {
            List<Language> languages = languageService.listLanguage();
            model.addAttribute(SecurityAttributes.FIELD_LANGUAGES, languages);
            model.addAttribute(SecurityAttributes.FIELD_LANGUAGES_JSON, BaseConstants.MAPPER.writeValueAsString(languages));
        }
        setLoginPageLabel(model, session);
    }

    /**
     * 设置登录页面多语言标签
     */
    private void setLoginPageLabel(Model model, HttpSession session) {
        // 默认语言
        String language = (String) session.getAttribute(SecurityAttributes.FIELD_LANG);
        if (StringUtils.isBlank(language)) {
            language = configGetter.getValue(ProfileCode.OAUTH_DEFAULT_LANGUAGE);
            model.addAttribute(SecurityAttributes.FIELD_LANG, language);
        }
        Map<String, String> map = multiLanguageConfig.getLanguageValue(language);
        model.addAllAttributes(map);
    }
}