package com.me.autoconfigure.gateway.helper;

import com.me.gateway.helper.api.AuthenticationHelper;
import com.me.gateway.helper.api.HelperFilter;
import com.me.gateway.helper.api.reactive.ReactiveAuthenticationHelper;
import com.me.gateway.helper.config.GatewayHelperProperties;
import com.me.gateway.helper.filter.SignatureAccessFilter;
import com.me.gateway.helper.impl.DefaultAuthenticationHelper;
import com.me.gateway.helper.impl.HelperChain;
import com.me.gateway.helper.impl.reactive.DefaultReactiveAuthenticationHelper;
import com.me.gateway.helper.service.CustomPermissionCheckService;
import com.me.gateway.helper.service.SignatureService;
import com.me.gateway.helper.service.impl.DefaultCustomPermissionCheckService;
import com.me.gateway.helper.service.impl.DefaultSignatureService;
import com.me.redis.RedisHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@ComponentScan({"com.me.gateway.helper"})
@EnableCaching
@Configuration
@EnableAsync
@Order(2147483642)
public class GatewayHelperAutoConfiguration {
    public GatewayHelperAutoConfiguration() {
    }

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

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "y.gateway.helper",
            value = {"signature.enabled"},
            havingValue = "true"
    )
    public SignatureService signatureService(GatewayHelperProperties properties, RedisHelper redisHelper) {
        return new DefaultSignatureService(properties, redisHelper);
    }

    @Bean(
            name = {"signatureAccessFilter"}
    )
    @ConditionalOnProperty(
            prefix = "y.gateway.helper",
            value = {"signature.enabled"},
            havingValue = "true"
    )
    public SignatureAccessFilter signatureAccessFilter(GatewayHelperProperties properties, SignatureService signatureService) {
        return new SignatureAccessFilter(properties, signatureService);
    }

    @Bean
    @Qualifier("permissionCheckSaveExecutor")
    public AsyncTaskExecutor commonAsyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("ps-check-save-");
        executor.setMaxPoolSize(200);
        executor.setCorePoolSize(50);
        return executor;
    }

    @Bean
    @ConditionalOnMissingBean({CustomPermissionCheckService.class})
    public CustomPermissionCheckService customPermissionCheckService() {
        return new DefaultCustomPermissionCheckService();
    }


}
