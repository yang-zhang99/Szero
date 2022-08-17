package com.me.autoconfigure.gateway;

import com.me.gateway.config.MaintainProperties;
import com.me.gateway.config.YGatewayProperties;
import com.me.gateway.endpoint.MaintainEndpoint;
import com.me.gateway.filter.*;
import com.me.gateway.filter.metric.*;
import com.me.gateway.helper.config.GatewayHelperProperties;
import com.me.gateway.ratelimit.RateLimitConfiguration;
import com.yang.redis.RedisHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties({YGatewayProperties.class, // 服务的Cors
        MaintainProperties.class  // 服务的监控
})
@MapperScan("com.me.gateway.helper.infra.mapper")
public class WebConditionAutoConfiguration {

    @Bean
    public MaintainEndpoint maintainEndpoint(MaintainProperties maintainProperties) {
        return new MaintainEndpoint(maintainProperties);
    }

    @Bean
    public HeaderWrapperFilter headerWrapperFilter(GatewayHelperProperties gatewayHelperProperties) {
        return new HeaderWrapperFilter(gatewayHelperProperties);
    }


    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @Configuration
    @ConditionalOnProperty(name = "y.request.counter.enable", havingValue = "true")
    public static class MetricConfig {

        //======= metric start
        @Bean
        @ConditionalOnMissingBean
        public RequestCountRules requestCountRules(RedisHelper redisHelper) {
            // 白名单与黑名单
            RedisWhiteSetRepository redisWhiteSetRepository = new RedisWhiteSetRepository(redisHelper);
            RedisBlackSetRepository redisBlackSetRepository = new RedisBlackSetRepository(redisHelper);
            return new RequestCountRules(redisHelper, redisWhiteSetRepository, redisBlackSetRepository);
        }

        // 请求统计
        @Bean
        public RequestCounter requestCounter(RequestCountRules requestCountRules) {
            RequestCounter counter = new RequestCounter(requestCountRules);
            counter.start();
            return counter;
        }

        // ？？？
        @Bean
        public RequestCountTraceListener requestCountTraceListener(RequestCountRules requestCountRules) {
            return new RequestCountTraceListener(requestCountRules);
        }

        @Bean
        public CustomInMemoryHttpTraceRepository inMemoryHttpTraceRepository(List<TraceListener> listeners) {
            return new CustomInMemoryHttpTraceRepository(listeners);
        }

        // 黑白名单
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

    @Bean
    public XForwardedForFilter xForwardedForFilter() {
        return new XForwardedForFilter();
    }

    /**
     * 服务限流控制
     */
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @Configuration
    @Import(RateLimitConfiguration.class)
    public static class GatewayConfig {
        /**
         * 声明 GateWayHelperFilter
         *
         * @return 配置的 GateWayHelperFilter
         */
//        @Bean
//        public com.me.gateway.filter.GateWayHelperFilter gateWayHelperFilter(ReactiveAuthenticationHelper gatewayHelper) {
//            return new com.me.gateway.filter.GateWayHelperFilter(gatewayHelper);
//        }
        @Bean
        @Order(Integer.MIN_VALUE)
        public CorsWebFilter corsWebFilter(YGatewayProperties properties) {
            org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            for (String allowedOrigin : properties.getCors().getAllowedOrigins()) {
                config.addAllowedOriginPattern(allowedOrigin);
            }
            for (String allowedHeader : properties.getCors().getAllowedHeaders()) {
                config.addAllowedHeader(allowedHeader);
            }
            for (String allowedMethod : properties.getCors().getAllowedMethods()) {
                config.addAllowedMethod(allowedMethod);
            }
            config.setMaxAge(18000L);
            //添加response暴露的header
            String[] responseHeader =
                    {"date", "content-encoding", "server", "etag", "vary", "Cache-Control", "Last-Modified",
                            "content-type", "transfer-encoding", "connection", "x-application-context"};
            config.setExposedHeaders(Arrays.asList(responseHeader));
            source.registerCorsConfiguration("/**", config);
            return new CorsWebFilter(source);
        }

    }

}
