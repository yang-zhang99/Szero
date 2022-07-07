package com.ttdo.gateway.filter;

import com.ttdo.gateway.helper.api.reactive.ReactiveAuthenticationHelper;
import com.ttdo.gateway.helper.entity.CheckState;
import com.ttdo.gateway.helper.entity.ResponseContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static com.ttdo.core.variable.RequestVariableHolder.HEADER_JWT;


@Order(-1)
public class GateWayHelperFilter implements WebFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GateWayHelperFilter.class);

    private ReactiveAuthenticationHelper authenticationHelper;

    public GateWayHelperFilter(ReactiveAuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();


        ResponseContext responseContext = null;

        try {
            responseContext = authenticationHelper.authentication(exchange);

            if (StringUtils.equals(responseContext.getRequestCode(), CheckState.SUCCESS_SKIP_PATH.getCode())) {
                return chain.filter(exchange);
            }
            if (responseContext.getHttpStatus().is2xxSuccessful()) {
                String jwtToken = responseContext.getJwtToken();
                return chain.filter(exchange.mutate().request(builder -> builder.header(HEADER_JWT, jwtToken)).build());
            } else {
                return handleError(exchange.getResponse(), responseContext);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Mono<Void> handleError(ServerHttpResponse response, ResponseContext responseContext) throws IOException {


        return null;
    }
}
