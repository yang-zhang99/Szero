package com.ttdo.gateway.filter;

import com.ttdo.gateway.filter.metric.RequestCountRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Set;


@Order(value = Integer.MIN_VALUE)
public class IpCheckedFilter implements WebFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpCheckedFilter.class);


    public static final String X_REAL_IP = "X-Real-IP";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    private static final String ERROR_MESSAGE = "You are not allowed to access.";

    private final RequestCountRules requestCountRules;

    public IpCheckedFilter(RequestCountRules requestCountRules) {
        this.requestCountRules = requestCountRules;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String realIp = getRealIp(exchange);
        if (realIp == null) {
            LOGGER.warn("unknown ip request access gateway.");
            return chain.filter(exchange);
        }

        /**
         * url匹配白名单
         */
//        Set<String> urlWhitelist = requestCountRules.getWhitelist(uri);
//        if (!CollectionUtils.isEmpty(urlWhitelist)) {
//            if (!urlWhitelist.contains(realIp)) {
//                return response(exchange.getResponse(), ERROR_MESSAGE);
//            }
//        }
//
//        /**
//         * url匹配黑名单
//         */
//        Set<String> urlBlacklist = requestCountRules.getBlacklist(uri);
//        if (!CollectionUtils.isEmpty(urlBlacklist)) {
//            if (urlBlacklist.contains(realIp)) {
//                return response(exchange.getResponse(), ERROR_MESSAGE);
//            }
//        }

        return chain.filter(exchange);

    }

    private String getRealIp(ServerWebExchange exchange) {
        String realIp = null;

        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        if (remoteAddress != null) {
            realIp = remoteAddress.getHostName();
        }

        HttpHeaders headers = exchange.getRequest().getHeaders();

        List<String> forwardedIps = headers.get(X_FORWARDED_FOR);
        if (!CollectionUtils.isEmpty(forwardedIps)) {
            realIp = forwardedIps.get(0);
        }

        List<String> realIps = headers.get(X_REAL_IP);
        if (!CollectionUtils.isEmpty(realIps)) {
            realIp = realIps.get(0);
        }

        return realIp;
    }
}
