package com.me.oauth.security.token;


import com.me.oauth.domain.vo.AuthenticationResult;
import com.me.oauth.security.custom.processor.authorize.AuthorizeSuccessProcessor;
import com.me.oauth.security.exception.CustomAuthenticationException;
import com.me.oauth.security.util.LoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenRequest;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录获取Token
 *
 */
public abstract class LoginTokenService implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginTokenService.class);

    private static final String GRANT_TYPE_IMPLICIT = "implicit";
    private static final String GRANT_TYPE_PASSWORD = "password";

    private TokenGranter tokenGranter;

    private ClientDetailsService clientDetailsService;

    private OAuth2RequestFactory oAuth2RequestFactory;

    private OAuth2RequestFactory defaultOAuth2RequestFactory;

    private AuthenticationProvider authenticationProvider;

    private List<AuthorizeSuccessProcessor> authorizeSuccessProcessors;

    public LoginTokenService() {
    }

    public LoginTokenService(TokenGranter tokenGranter,
                             ClientDetailsService clientDetailsService,
                             OAuth2RequestFactory oAuth2RequestFactory,
                             AuthenticationProvider authenticationProvider,
                             List<AuthorizeSuccessProcessor> authorizeSuccessProcessors) {
        this.tokenGranter = tokenGranter;
        this.clientDetailsService = clientDetailsService;
        this.oAuth2RequestFactory = oAuth2RequestFactory;
        this.authenticationProvider = authenticationProvider;
        this.authorizeSuccessProcessors = authorizeSuccessProcessors;
        this.authorizeSuccessProcessors.sort(Comparator.comparingInt(AuthorizeSuccessProcessor::getOrder));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.state(tokenGranter != null, "TokenGranter must be provided");
        Assert.state(clientDetailsService != null, "ClientDetailsService must be provided");
        Assert.state(authenticationProvider != null, "AuthenticationProvider must be provided");
        defaultOAuth2RequestFactory = new DefaultOAuth2RequestFactory(getClientDetailsService());
        if (oAuth2RequestFactory == null) {
            oAuth2RequestFactory = defaultOAuth2RequestFactory;
        }
    }

    /**
     * 用户名密码登录获取Token
     *
     * @param request HttpServletRequest
     * @return AuthenticationResult
     */
    public AuthenticationResult loginForToken(HttpServletRequest request) {
        // 封装请求参数
        Authentication authRequest = attemptAuthentication(request);
        // 认证
        Authentication authentication = authenticationProvider.authenticate(authRequest);
        // 设置登录成功
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 创建token
        OAuth2AccessToken token = createAccessToken(request);
        // 返回结果
        AuthenticationResult authenticationResult = new AuthenticationResult();
        if (token != null) {
            authenticationResult.authenticateSuccess();
            authenticationResult.setOAuth2AccessToken(token);
        } else {
            throw new CustomAuthenticationException("hoth.warn.createClientError");
        }

        return authenticationResult;
    }

    protected abstract Authentication attemptAuthentication(HttpServletRequest request);

    protected Map<String, String> extractParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>(8);
        request.getParameterMap().forEach((key, vals) -> {
            parameters.put(key, vals != null && vals.length > 0 ? vals[0] : "");
        });
        return parameters;
    }

    protected OAuth2AccessToken createAccessToken(HttpServletRequest request) {
        String clientId = request.getParameter(LoginUtil.FIELD_CLIENT_ID);
        String grantType = request.getParameter(LoginUtil.FIELD_GRANT_TYPE);
        if (StringUtils.isEmpty(clientId)) {
            throw new NoSuchClientException("client is empty");
        }

        Map<String, String> parameters = extractParameters(request);
        TokenRequest tokenRequest = null;
        OAuth2AccessToken token = null;

        if (StringUtils.isEmpty(grantType)) {
            throw new InvalidRequestException("Missing grant type");
        }

        // 简化模式
        AuthorizationRequest authorizationRequest = getOAuth2RequestFactory().createAuthorizationRequest(parameters);
        if (GRANT_TYPE_IMPLICIT.equalsIgnoreCase(grantType)) {
            authorizationRequest.setApproved(true);
            tokenRequest = getOAuth2RequestFactory().createTokenRequest(authorizationRequest, grantType);
            OAuth2Request storedOAuth2Request = getOAuth2RequestFactory().createOAuth2Request(authorizationRequest);
            token = getTokenGranter().grant(GRANT_TYPE_IMPLICIT, new ImplicitTokenRequest(tokenRequest, storedOAuth2Request));
        }
        // 密码模式
        else if (GRANT_TYPE_PASSWORD.equalsIgnoreCase(grantType)) {
            ClientDetails authenticatedClient = getClientDetailsService().loadClientByClientId(clientId);
            tokenRequest = getOAuth2RequestFactory().createTokenRequest(parameters, authenticatedClient);
            token = getTokenGranter().grant(GRANT_TYPE_PASSWORD, tokenRequest);
        } else {
            throw new InvalidRequestException("Grant type not support for " + grantType);
        }

        if (token == null) {
            throw new UnsupportedGrantTypeException("Unsupported grant type: " + tokenRequest.getGrantType());
        }

        for (AuthorizeSuccessProcessor processor : authorizeSuccessProcessors) {
            try {
                processor.process(request, null, authorizationRequest, token);
            } catch (Exception e) {
                LOGGER.error("authorize processor error, processor is {}, ex={}",
                        processor.getClass().getSimpleName(), e.getMessage(), e);
            }
        }

        return token;
    }

    public void setTokenGranter(TokenGranter tokenGranter) {
        this.tokenGranter = tokenGranter;
    }

    protected TokenGranter getTokenGranter() {
        return tokenGranter;
    }

    protected OAuth2RequestFactory getOAuth2RequestFactory() {
        return oAuth2RequestFactory;
    }

    protected OAuth2RequestFactory getDefaultOAuth2RequestFactory() {
        return defaultOAuth2RequestFactory;
    }

    public void setOAuth2RequestFactory(OAuth2RequestFactory oAuth2RequestFactory) {
        this.oAuth2RequestFactory = oAuth2RequestFactory;
    }

    protected ClientDetailsService getClientDetailsService() {
        return clientDetailsService;
    }

    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    public AuthenticationProvider getAuthenticationProvider() {
        return authenticationProvider;
    }

    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }
}
