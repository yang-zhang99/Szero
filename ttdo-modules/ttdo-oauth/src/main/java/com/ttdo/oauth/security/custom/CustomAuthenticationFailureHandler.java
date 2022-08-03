package com.ttdo.oauth.security.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler, Serializable {

    private static final long serialVersionUID = 8528419529380369200L;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    private String usernameParameter;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 获取用户名
        String userName = request.getParameter(usernameParameter);
        // Session
        HttpSession session = request.getSession();
        //


    }
}
