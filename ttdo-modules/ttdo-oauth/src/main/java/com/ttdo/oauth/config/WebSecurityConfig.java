package com.ttdo.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web 安全 配置类
 */

@Configuration
@Order(org.springframework.boot.autoconfigure.security.SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private static final String[] PERMIT_PATHS = new String[]{
            "/login", "/login/**", "/open-bind", "/token/**", "/pass-page/**", "/admin/**",
            "/v2/choerodon/**", "/choerodon/**", "/public/**", "/password/**",
            "/admin/**", "/static/**", "/saml/metadata", "/actuator/**"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {


//        http
//                .authorizeRequests()
//                .antMatchers(PERMIT_PATHS)
//                .permitAll()
//                .and()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
////                .authenticationDetailsSource(detailsSource)
////                .failureHandler(authenticationFailureHandler)
////                .successHandler(authenticationSuccessHandler)
//                .and()
//                .logout().deleteCookies("access_token").invalidateHttpSession(true)
////                .logoutSuccessHandler(customLogoutSuccessHandler)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .and()
//                .portMapper()
////                .portMapper(portMapper)
//                .and()
//                .requestCache()
////                .requestCache(getRequestCache(http))
//                .and()
//                .csrf()
//                .disable()
//        ;
    }
}
