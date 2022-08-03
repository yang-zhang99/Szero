package com.ttdo.oauth.api.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RefreshScope
@Controller
public class OauthController {

    private static final String LOGIN_DEFAULT = "login";


    /**
     * 默认登录页面
     */
    @GetMapping(value = "/login")
    public String login(HttpServletRequest request, Model model, HttpSession session,
                        @RequestParam(required = false) String device,
                        @RequestParam(required = false) String type) throws JsonProcessingException {
//        setPageDefaultData(request, session, model);
//        String template = (String) session.getAttribute(LoginUtil.FIELD_TEMPLATE);
//        // 登录页面
//        String returnPage = "mobile".equals(device) ? LOGIN_MOBILE : LOGIN_DEFAULT;
//        returnPage = template + SLASH + returnPage;
//
//        // 登录方式
//        type = LoginType.match(type) != null ? type : SecurityAttributes.DEFAULT_LOGIN_TYPE.code();
//        model.addAttribute(SecurityAttributes.FIELD_LOGIN_TYPE, type);
//
//        User user = userLoginService.queryRequestUser(request);
//        // 是否需要验证码
//        model.addAttribute(SecurityAttributes.FIELD_IS_NEED_CAPTCHA, userLoginService.isNeedCaptcha(user));
//
//        // 错误消息
//        String exceptionMessage = (String) session.getAttribute(SecurityAttributes.SECURITY_LAST_EXCEPTION);
//        if (StringUtils.isNotBlank(exceptionMessage)) {
//            model.addAttribute(SecurityAttributes.FIELD_LOGIN_ERROR_MSG, exceptionMessage);
//        }
//
//        String username = (String) session.getAttribute(SecurityAttributes.SECURITY_LOGIN_USERNAME);
//
//        SecurityAttributes.removeSecuritySessionAttribute(session);
//        if (StringUtils.isBlank(username)) {
//            return returnPage;
//        }
//
//        model.addAttribute(SecurityAttributes.SECURITY_LOGIN_USERNAME, username);
//        if (LoginType.SMS.code().equals(type)) {
//            model.addAttribute(SecurityAttributes.SECURITY_LOGIN_MOBILE, username);
//        }

        return "/main/login";
    }


}