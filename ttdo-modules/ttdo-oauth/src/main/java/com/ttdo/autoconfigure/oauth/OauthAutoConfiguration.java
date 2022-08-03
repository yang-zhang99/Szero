package com.ttdo.autoconfigure.oauth;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {
        "com.ttdo.oauth.security",
        "com.ttdo.oauth.api",
        "com.ttdo.oauth.config",
        "com.ttdo.oauth.domain",
        "com.ttdo.oauth.infra",
})
@EnableConfigurationProperties
public class OauthAutoConfiguration {


}
