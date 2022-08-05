package com.yang.core.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.yang.core.variable.RequestVariableHolder.*;

/**
 * @author qingsheng.chen@hand-china.com
 */
public class TokenUtils {
    private static final String TOKEN_PREFIX = HEADER_BEARER + " ";

    /**
     * @return 获取当前登陆客户端 token
     */
    public static String getToken() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String accessToken = requestAttributes.getRequest().getHeader(HEADER_AUTH);
        if (!StringUtils.hasText(accessToken)) {
            accessToken = requestAttributes.getRequest().getParameter(ACCESS_TOKEN);
        }
        if (StringUtils.startsWithIgnoreCase(accessToken, TOKEN_PREFIX)) {
            accessToken = accessToken.substring(TOKEN_PREFIX.length());
        }
        return accessToken;
    }

    public static String getToken(HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTH);
        if (!StringUtils.hasText(accessToken)) {
            accessToken = request.getParameter(ACCESS_TOKEN);

        }
        if (StringUtils.startsWithIgnoreCase(accessToken, TOKEN_PREFIX)) {
            accessToken = accessToken.substring(TOKEN_PREFIX.length());
        }
        return accessToken;
    }
}
