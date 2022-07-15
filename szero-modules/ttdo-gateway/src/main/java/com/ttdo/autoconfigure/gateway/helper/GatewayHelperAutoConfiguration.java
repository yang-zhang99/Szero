package com.ttdo.autoconfigure.gateway.helper;

import com.ttdo.gateway.helper.api.HelperFilter;
import com.ttdo.gateway.helper.api.reactive.ReactiveAuthenticationHelper;
import com.ttdo.gateway.helper.impl.reactive.DefaultReactiveAuthenticationHelper;
import com.ttdo.gateway.helper.impl.reactive.HelperChain;
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

@ComponentScan({"com.ttdo.gateway.helper"})
//@EnableCaching
@Configuration
//@EnableAsync
@Order(2147483642)
public class GatewayHelperAutoConfiguration {

    @Bean
    public HelperChain helperChain(Optional<List<HelperFilter>> optionalHelperFilters) {
        return new HelperChain(optionalHelperFilters);
    }

    @Bean
    @ConditionalOnWebApplication(
            type = Type.REACTIVE
    )
    @ConditionalOnMissingBean({ReactiveAuthenticationHelper.class})
    public DefaultReactiveAuthenticationHelper reactiveAuthenticationHelper(HelperChain helperChain) {
        return new DefaultReactiveAuthenticationHelper(helperChain);
    }
}
