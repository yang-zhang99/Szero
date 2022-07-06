package com.ttdo.gateway.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Order(-1)
public class GateWayHelperFilter implements WebFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String url = exchange.getRequest().getURI().getPath();


        ResponseEntity responseContext = null;

//        responseContext =



        return null;
    }
}
