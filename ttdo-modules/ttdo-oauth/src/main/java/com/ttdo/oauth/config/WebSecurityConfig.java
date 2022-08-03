package com.ttdo.oauth.config;

import com.ttdo.oauth.security.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * Web 安全 配置类
 */
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    private static final String[] PERMIT_PATHS = new String[]{
            "/login", "/login/**", "/open-bind", "/token/**", "/pass-page/**", "/admin/**",
            "/v2/choerodon/**", "/choerodon/**", "/public/**", "/password/**",
            "/admin/**", "/static/**", "/saml/metadata", "/actuator/**"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .authorizeRequests()
                .antMatchers (PERMIT_PATHS)
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage(securityProperties.getLogin().getPage())
//                .authenticationDetailsSource(detailsSource)
//                .failureHandler(authenticationFailureHandler)
//                .successHandler(authenticationSuccessHandler)
                .and()
                .logout().deleteCookies("access_token").invalidateHttpSession(true)
//                .logoutSuccessHandler(customLogoutSuccessHandler)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
                .portMapper()
//                .portMapper(portMapper)
                .and()
                .requestCache()
//                .requestCache(getRequestCache(http))
                .and()
                .csrf()
                .disable()
        ;    }
}
