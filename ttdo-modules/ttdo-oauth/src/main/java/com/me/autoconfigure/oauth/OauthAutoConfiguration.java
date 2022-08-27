package com.me.autoconfigure.oauth;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value = {
        "com.me.oauth.security",
        "com.me.oauth.api",
        "com.me.oauth.config",
        "com.me.oauth.domain",
        "com.me.oauth.infra",
        "com.me.oauth.policy",
        "com.me.oauth.strategy"
})
@MapperScan("com.me.oauth.infra.mapper")
@EnableConfigurationProperties
@Configuration
//@EnableOAuth2Client
public class OauthAutoConfiguration {


}
