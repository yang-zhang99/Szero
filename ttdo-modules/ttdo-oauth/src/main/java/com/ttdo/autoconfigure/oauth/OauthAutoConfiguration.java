package com.ttdo.autoconfigure.oauth;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value = {
        "com.ttdo.oauth.security",
        "com.ttdo.oauth.api",
        "com.ttdo.oauth.config",
        "com.ttdo.oauth.domain",
        "com.ttdo.oauth.infra",
})
@MapperScan("com.ttdo.oauth.infra.mapper")

@EnableConfigurationProperties
@Configuration
public class OauthAutoConfiguration {


}
