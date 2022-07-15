package com.ttdo.autoconfigure.gateway.helper.api;


import com.ttdo.gateway.helper.config.GatewayHelperProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.Signer;

@Configuration
@EnableConfigurationProperties(GatewayHelperProperties.class)
public class GatewayHelperConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayHelperConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(Signer.class)
    public Signer jwtSigner(GatewayHelperProperties gatewayHelperProperties) {
        return new MacSigner(gatewayHelperProperties.getJwtKey());
    }
}
