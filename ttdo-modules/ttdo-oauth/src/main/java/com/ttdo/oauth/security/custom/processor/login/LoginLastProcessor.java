package com.ttdo.oauth.security.custom.processor.login;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录后置处理
 *
 * @author XCXCXCXCX
 * @since 1.0
 */
@Component
public class LoginLastProcessor implements LoginSuccessProcessor {

    @Override
    public Object process(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
