package com.ttdo.autoconfigure.gateway.helper;

import com.ttdo.gateway.helper.api.HelperFilter;
import com.ttdo.gateway.helper.api.reactive.ReactiveAuthenticationHelper;
import com.ttdo.gateway.helper.impl.reactive.DefaultReactiveAuthenticationHelper;
import com.ttdo.gateway.helper.impl.reactive.HelperChain;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

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
            type = Type.REACTIVE
    )
    @ConditionalOnMissingBean({ReactiveAuthenticationHelper.class})
    public DefaultReactiveAuthenticationHelper reactiveAuthenticationHelper(HelperChain helperChain) {
        return new DefaultReactiveAuthenticationHelper(helperChain);
    }
}
