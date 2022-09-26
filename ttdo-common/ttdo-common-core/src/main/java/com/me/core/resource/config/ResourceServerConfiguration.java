package com.me.core.resource.config;

import com.me.core.resource.filter.JwtTokenExtractor;
import com.me.core.resource.filter.JwtTokenFilter;
import com.me.core.resource.permission.PublicPermissionOperationPlugin;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import static javax.servlet.DispatcherType.REQUEST;

//@EnableWebSecurity

public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServerConfiguration.class);


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/actuator/**").antMatchers("/prometheus");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/monitor/**")
                .permitAll();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Bean
    public FilterRegistrationBean someFilterRegistration(JwtTokenFilter jwtTokenFilter) {
        String[] pattern = StringUtils.replace(null, " ", "").split(",");
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(jwtTokenFilter);
        registration.addUrlPatterns(pattern);
        registration.setName("jwtTokenFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE - 100);
        registration.setDispatcherTypes(REQUEST);
        return registration;
    }

    @Bean
    public JwtTokenExtractor jwtTokenExtractor() {
        return new JwtTokenExtractor();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter(PublicPermissionOperationPlugin publicPermissionOperationPlugin,
                                         JwtTokenExtractor jwtTokenExtractor) {
        return new JwtTokenFilter(tokenServices(),
                jwtTokenExtractor,
                publicPermissionOperationPlugin.getPublicPaths(),
               null);
    }

    /**
     * DefaultTokenService Bean
     *
     * @return DefaultTokenService对象
     */
    private DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    private TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * 返回converter
     *
     * @return converter
     */
    private JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setAccessTokenConverter(new CustomTokenConverter());
//        converter.setSigningKey(properties.getOauthJwtKey());
        try {
            converter.afterPropertiesSet();
        } catch (Exception e) {
            LOGGER.warn("error.ResourceServerConfiguration.accessTokenConverter {}", e.getMessage());
        }
        return converter;
    }

}
