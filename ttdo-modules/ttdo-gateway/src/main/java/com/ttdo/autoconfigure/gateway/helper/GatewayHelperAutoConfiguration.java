package com.ttdo.autoconfigure.gateway.helper;

import com.ttdo.gateway.helper.api.AuthenticationHelper;
import com.ttdo.gateway.helper.api.HelperFilter;
import com.ttdo.gateway.helper.api.reactive.ReactiveAuthenticationHelper;
import com.ttdo.gateway.helper.impl.DefaultAuthenticationHelper;
import com.ttdo.gateway.helper.impl.reactive.DefaultReactiveAuthenticationHelper;
import com.ttdo.gateway.helper.impl.HelperChain;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@ComponentScan({"com.ttdo.gateway.helper"})
//@EnableCaching
@Configuration
//@EnableAsync
@Order(2147483642)
public class GatewayHelperAutoConfiguration {

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
}
