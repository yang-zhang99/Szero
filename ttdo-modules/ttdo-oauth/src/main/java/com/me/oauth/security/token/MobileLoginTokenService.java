//package com.me.oauth.security.token;
//
//import org.apache.commons.lang3.StringUtils;
//
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
//import org.springframework.security.oauth2.provider.TokenGranter;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
///**
// *
// * @author bojiangzhou 2019/06/02
// */
//public class MobileLoginTokenService extends LoginTokenService {
//
//    private SmsAuthenticationDetailsSource authenticationDetailsSource;
//    private SecurityProperties securityProperties;
//
//    public MobileLoginTokenService(TokenGranter tokenGranter,
//                                   ClientDetailsService clientDetailsService,
//                                   OAuth2RequestFactory oAuth2RequestFactory,
//                                   AuthenticationProvider authenticationProvider,
//                                   SmsAuthenticationDetailsSource authenticationDetailsSource,
//                                   SecurityProperties securityProperties,
//                                   List<AuthorizeSuccessProcessor> authorizeSuccessProcessors) {
//        super(tokenGranter, clientDetailsService, oAuth2RequestFactory, authenticationProvider, authorizeSuccessProcessors);
//        this.authenticationDetailsSource = authenticationDetailsSource;
//        this.securityProperties = securityPropertieses;
//    }
//
//    @Override
//    protected Authentication attemptAuthentication(HttpServletRequest request) {
//        String mobile = request.getParameter(securityProperties.getLogin().getMobileParameter());
//
//        mobile = StringUtils.defaultIfBlank(mobile, "").trim();
//
//        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(mobile);
//
//        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
//
//        return authRequest;
//    }
//
//    public SmsAuthenticationDetailsSource getAuthenticationDetailsSource() {
//        return authenticationDetailsSource;
//    }
//
//    public SecurityProperties getSecurityProperties() {
//        return securityProperties;
//    }
//
//}
