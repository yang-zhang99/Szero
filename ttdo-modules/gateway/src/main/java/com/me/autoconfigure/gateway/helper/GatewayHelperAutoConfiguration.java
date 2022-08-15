package com.me.autoconfigure.gateway.helper;

import com.me.gateway.helper.api.AuthenticationHelper;
import com.me.gateway.helper.api.HelperFilter;
import com.me.gateway.helper.api.reactive.ReactiveAuthenticationHelper;
import com.me.gateway.helper.config.GatewayHelperProperties;
import com.me.gateway.helper.impl.DefaultAuthenticationHelper;
import com.me.gateway.helper.impl.HelperChain;
import com.me.gateway.helper.impl.reactive.DefaultReactiveAuthenticationHelper;
import com.me.gateway.helper.service.CustomPermissionCheckService;
import com.me.gateway.helper.service.impl.DefaultCustomPermissionCheckService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@ComponentScan({"com.me.gateway.helper"})
//@EnableCaching
@Configuration
//@EnableAsync
@Order(2147483642)
public class GatewayHelperAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(Signer.class)
    public Signer jwtSigner(GatewayHelperProperties gatewayHelperProperties) {
        return new MacSigner(gatewayHelperProperties.getJwtKey());
    }
    /**
     * 过滤器链
     * @param optionalHelperFilters
     * @return
     */
    @Bean
    public HelperChain helperChain(Optional<List<HelperFilter>> optionalHelperFilters) {
        return new HelperChain(optionalHelperFilters);
    }

    @Bean
    @ConditionalOnWebApplication(
            type = Type.SERVLET
    )
    @ConditionalOnMissingBean({AuthenticationHelper.class})
    public DefaultAuthenticationHelper authenticationHelper(HelperChain helperChain) {
        return new DefaultAuthenticationHelper(helperChain);
    }

    @Bean
    @ConditionalOnWebApplication(
            type = Type.REACTIVE
    )
    @ConditionalOnMissingBean({ReactiveAuthenticationHelper.class})
    public DefaultReactiveAuthenticationHelper reactiveAuthenticationHelper(HelperChain helperChain) {
        return new DefaultReactiveAuthenticationHelper(helperChain);
    }

    @Bean(
            name = {"helperRestTemplate"}
    )
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    @ConditionalOnMissingBean
//    @ConditionalOnProperty(
//            prefix = "hzero.gateway.helper",
//            value = {"signature.enabled"},
//            havingValue = "true"
//    )
//    public SignatureService signatureService(GatewayHelperProperties properties, RedisHelper redisHelper) {
//        return new DefaultSignatureService(properties, redisHelper);
//    }

    @Bean
    @ConditionalOnMissingBean(CustomPermissionCheckService.class)
    public CustomPermissionCheckService customPermissionCheckService() {
        return new DefaultCustomPermissionCheckService();
    }


}
