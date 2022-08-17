package com.me.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * 将 远程地址 放到 header 的 X-Forwarded-For 中
 */
@Component
public class XForwardedForFilter implements GlobalFilter, Ordered {

    private static final String HTTP_X_FORWARDED_FOR = "X-Forwarded-For";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String remoteAddress = request.getRemoteAddress().getHostString();
        return chain.filter(
                exchange.mutate()
                        .request(
                                builder -> builder.header(HTTP_X_FORWARDED_FOR, remoteAddress)
                        )
                        .build());
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}



