package com.me.gateway.helper.impl;

import com.me.gateway.helper.api.AuthenticationHelper;
import com.me.gateway.helper.entity.CheckRequest;
import com.me.gateway.helper.entity.CheckResponse;
import com.me.gateway.helper.entity.RequestContext;
import com.me.gateway.helper.entity.ResponseContext;
import com.yang.core.util.TokenUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DefaultAuthenticationHelper implements AuthenticationHelper {
    private static final String TOKEN_PREFIX = StringUtils.lowerCase("Bearer ");
    private HelperChain chain;

    public DefaultAuthenticationHelper(HelperChain chain) {
        this.chain = chain;
    }

    public ResponseContext authentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestContext requestContext = RequestContext.initRequestContext(new CheckRequest(TOKEN_PREFIX + TokenUtils.getToken(request), request.getRequestURI(), request.getMethod().toLowerCase()), new CheckResponse());
        requestContext.setServletRequest(request);
        return this.chain.doFilter(requestContext);
    }
}
