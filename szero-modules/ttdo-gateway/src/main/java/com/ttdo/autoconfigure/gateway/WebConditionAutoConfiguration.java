package com.ttdo.autoconfigure.gateway;

import com.ttdo.core.redis.RedisHelper;
import com.ttdo.gateway.config.YGatewayProperties;
import com.ttdo.gateway.filter.IpCheckedFilter;
import com.ttdo.gateway.filter.RedisBlackSetRepository;
import com.ttdo.gateway.filter.RedisWhiteSetRepository;
import com.ttdo.gateway.filter.XForwardedForFilter;
import com.ttdo.gateway.filter.metric.*;
import com.ttdo.gateway.helper.api.reactive.ReactiveAuthenticationHelper;
import com.ttdo.gateway.ratelimit.RateLimitConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Configuration
@EnableConfigurationProperties({YGatewayProperties.class})
public class WebConditionAutoConfiguration {


    @Bean
    public XForwardedForFilter xForwardedForFilter() {
        return new XForwardedForFilter();
    }

    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @Configuration
    @Import(RateLimitConfiguration.class)
    public static class GatewayConfig {
        /**
         * 声明GateWayHelperFilter
         *
         * @return 配置的GateWayHelperFilter
         */
        @Bean
        public com.ttdo.gateway.filter.GateWayHelperFilter gateWayHelperFilter(ReactiveAuthenticationHelper gatewayHelper) {
            return new com.ttdo.gateway.filter.GateWayHelperFilter(gatewayHelper);
        }

    }


    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @Configuration
    @ConditionalOnProperty(name = "y.request.counter.enable", havingValue = "true")
    public static class MetricConfig {
        //======= metric start
        @Bean
        @ConditionalOnMissingBean
        public RequestCountRules requestCountRules(RedisHelper redisHelper) {
            RedisWhiteSetRepository redisWhiteSetRepository = new RedisWhiteSetRepository(redisHelper);
            RedisBlackSetRepository redisBlackSetRepository = new RedisBlackSetRepository(redisHelper);
            return new RequestCountRules(redisHelper, redisWhiteSetRepository, redisBlackSetRepository);
        }

        // todo
        @Bean
        public RequestCounter requestCounter(RequestCountRules requestCountRules) {
            RequestCounter counter = new RequestCounter(requestCountRules);
            counter.start();
            return counter;
        }

        @Bean
        public RequestCountTraceListener requestCountTraceListener(RequestCountRules requestCountRules) {
            return new RequestCountTraceListener(requestCountRules);
        }

        @Bean
        public CustomInMemoryHttpTraceRepository inMemoryHttpTraceRepository(List<TraceListener> listeners) {
            return new CustomInMemoryHttpTraceRepository(listeners);
        }

        @Bean
        public IpCheckedFilter ipCheckedFilter(RequestCountRules requestCountRules) {
            return new IpCheckedFilter(requestCountRules);
        }
        //======= metric end

        //======= metric endpoint start
        @Bean
        public MetricEndpoint metricEndpoint(RequestCounter requestCounter) {
            return new MetricEndpoint(requestCounter);
        }

        @Bean
        public MetricRuleEndpoint metricRuleEndpoint(RequestCountRules requestCountRules) {
            return new MetricRuleEndpoint(requestCountRules);
        }
        //======= metric endpoint end
    }


}
