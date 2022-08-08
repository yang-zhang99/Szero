//package com.ttdo.oauth.config;
//
//import com.ttdo.oauth.security.config.SecurityProperties;
//import com.ttdo.oauth.security.custom.CustomAuthenticationFailureHandler;
//import com.ttdo.oauth.security.custom.CustomAuthenticationSuccessHandler;
//import org.apache.commons.lang3.ArrayUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.MediaType;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
//import org.springframework.security.web.PortMapper;
//import org.springframework.security.web.PortResolver;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//import org.springframework.security.web.savedrequest.RequestCache;
//import org.springframework.security.web.util.matcher.*;
//import org.springframework.web.accept.ContentNegotiationStrategy;
//import org.springframework.web.accept.HeaderContentNegotiationStrategy;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// * Web 安全 配置类
// */
//
//@Configuration
//@Order(org.springframework.boot.autoconfigure.security.SecurityProperties.BASIC_AUTH_ORDER)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private SecurityProperties securityProperties;
////    @Autowired
////    private SsoProperties ssoProperties;
////    @Autowired
////    private CustomAuthenticationDetailsSource detailsSource;
////    @Autowired
////    private SimpleUrlAuthenticationFailureHandler authenticationFailureHandler;
////    @Autowired
////    private SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler;
////    @Autowired
////    private CustomLogoutSuccessHandler customLogoutSuccessHandler;
////    @Autowired
////    private ClearResourceService clearResourceService;
//
////    @Autowired(required = false)
////    private SmsAuthenticationDetailsSource smsAuthenticationDetailsSource;
////    @Autowired(required = false)
////    private SmsAuthenticationFailureHandler smsAuthenticationFailureHandler;
////    @Autowired(required = false)
////    private SmsAuthenticationProvider smsAuthenticationProvider;
//
//    @Autowired
//    private PortMapper portMapper;
//    @Autowired
//    private PortResolver portResolver;
//
////    @Autowired(required = false)
////    private SsoAuthenticationEntryPoint ssoAuthenticationEntryPoint;
//
//    private static final String[] PERMIT_PATHS = new String[]{
//            "/login", "/login/**", "/open-bind", "/token/**", "/pass-page/**", "/admin/**",
//            "/v2/choerodon/**", "/choerodon/**", "/public/**", "/password/**",
//            "/admin/**", "/static/**", "/saml/metadata", "/actuator/**"
//    };
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        String[] permitPaths = ArrayUtils.addAll(PERMIT_PATHS, securityProperties.getPermitPaths());
//
//        http
//                .authorizeRequests()
//                .antMatchers (permitPaths)
//                .permitAll()
//                .and()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login1221")
//////                .authenticationDetailsSource(detailsSource)
//////                .failureHandler(authenticationFailureHandler)
//////                .successHandler(authenticationSuccessHandler)
//                .and()
////                .logout().deleteCookies("access_token").invalidateHttpSession(true)
//////                .logoutSuccessHandler(customLogoutSuccessHandler)
////                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
////                .and()
////                .portMapper()
////                .portMapper(portMapper)
////                .and()
////                .requestCache()
////                .requestCache(getRequestCache(http))
////                .and()
//                .csrf()
//                .disable()
//        ;
//    }
//
//
//    private RequestCache getRequestCache(HttpSecurity http) {
//        RequestCache result = http.getSharedObject(RequestCache.class);
//        if (result != null) {
//            return result;
//        }
//        HttpSessionRequestCache defaultCache = new HttpSessionRequestCache();
//        defaultCache.setRequestMatcher(createDefaultSavedRequestMatcher(http));
//        // 标准 HttpSessionRequestCache 无法设置 PortResolver
//        defaultCache.setPortResolver(portResolver);
//        return defaultCache;
//    }
//
//    @SuppressWarnings("unchecked")
//    private RequestMatcher createDefaultSavedRequestMatcher(HttpSecurity http) {
//        ContentNegotiationStrategy contentNegotiationStrategy = http
//                .getSharedObject(ContentNegotiationStrategy.class);
//        if (contentNegotiationStrategy == null) {
//            contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
//        }
//
//        RequestMatcher notFavIcon = new NegatedRequestMatcher(new AntPathRequestMatcher(
//                "/**/favicon.ico"));
//
//        MediaTypeRequestMatcher jsonRequest = new MediaTypeRequestMatcher(
//                contentNegotiationStrategy, MediaType.APPLICATION_JSON);
//        jsonRequest.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
//        RequestMatcher notJson = new NegatedRequestMatcher(jsonRequest);
//
//        RequestMatcher notXRequestedWith = new NegatedRequestMatcher(
//                new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest"));
//
//        boolean isCsrfEnabled = http.getConfigurer(CsrfConfigurer.class) != null;
//
//        List<RequestMatcher> matchers = new ArrayList<>();
//        if (isCsrfEnabled) {
//            RequestMatcher getRequests = new AntPathRequestMatcher("/**", "GET");
//            matchers.add(0, getRequests);
//        }
//        matchers.add(notFavIcon);
//        matchers.add(notJson);
//        matchers.add(notXRequestedWith);
//
//        return new AndRequestMatcher(matchers);
//    }
//}
