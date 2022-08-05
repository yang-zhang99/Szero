package com.ttdo.gateway.filter;

import com.ttdo.gateway.helper.api.reactive.ReactiveAuthenticationHelper;
import com.ttdo.gateway.helper.entity.CheckState;
import com.ttdo.gateway.helper.entity.ResponseContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

//import static com.ttdo.core.variable.RequestVariableHolder.HEADER_JWT;


@Order(-1)
public class GateWayHelperFilter implements WebFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GateWayHelperFilter.class);

    private final ReactiveAuthenticationHelper authenticationHelper;

    private static final String CONFIG_ENDPOINT = "/choerodon/config";

    /**
     * 构造器
     *
     * @param authenticationHelper spring内部requestCustomizers
     */
    public GateWayHelperFilter(ReactiveAuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String uri = exchange.getRequest().getURI().getPath();

        if (CONFIG_ENDPOINT.equals(uri)) {
            return chain.filter(exchange);
        }

        ResponseContext responseContext = null;
        try {
            responseContext = authenticationHelper.authentication(exchange);
            if (StringUtils.equals(responseContext.getRequestCode(), CheckState.SUCCESS_SKIP_PATH.getCode())) {
                return chain.filter(exchange);
            }
            if (responseContext.getHttpStatus().is2xxSuccessful()) {
                String jwtToken = responseContext.getJwtToken();
                return chain.filter(exchange.mutate()
//                        .request(builder -> builder.header(HEADER_JWT, jwtToken))
                        .build());
            } else {
                return handleError(exchange.getResponse(), responseContext);
            }
        } catch (IOException e) {
            LOGGER.error("org.hzero.gateway call helper authentication error, ex={}", e.getMessage());

            ServerHttpResponse res = exchange.getResponse();
            StringBuilder responseMessage = new StringBuilder()
                    .append("<error>")
                    .append("<status>500</status>")
                    .append("<code>GATEWAY_ERROR</code>")
                    .append("<message>").append(e.getMessage()).append("</message>")
                    .append("</error>");
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            setHeaders(res);

            DataBufferFactory factory = res.bufferFactory();
            DataBuffer dataBuffer = factory.wrap(responseMessage.toString().getBytes(StandardCharsets.UTF_8));
            return res.writeAndFlushWith(Flux.just(dataBuffer).map(Flux::just));
        }
    }

    private void setHeaders(ServerHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        headers.put(HttpHeaders.ACCEPT_CHARSET, Collections.singletonList("utf-8"));
        headers.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("text/html;charset=UTF-8"));
    }

    /**
     * 鉴权失败逻辑处理
     *
     * @param response        ServerHttpResponse
     * @param responseContext ResponseContext
     */
    private Mono<Void> handleError(ServerHttpResponse response, ResponseContext responseContext) throws IOException {
        setHeaders(response);

        int statusCode = responseContext.getHttpStatus().value();
        String requestStatus = responseContext.getRequestStatus();
        String requestCode = responseContext.getRequestCode();
        String requestMessage = responseContext.getRequestMessage();

        StringBuilder responseMessage = new StringBuilder();
        // 403
        if (statusCode == HttpStatus.FORBIDDEN.value()) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            responseMessage
                    .append("<oauth>")
                    .append("<status>").append(requestStatus).append("</status>")
                    .append("<code>").append(requestCode).append("</code>")
                    .append("<message>").append(requestMessage).append("</message>")
                    .append("</oauth>");
            // 401
        } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            responseMessage
                    .append("<oauth>")
                    .append("<status>").append(requestStatus).append("</status>")
                    .append("<code>").append(requestCode).append("</code>")
                    .append("<message>").append(requestMessage).append("</message>")
                    .append("</oauth>");
        } else {
            // 506
            if (CheckState.PERMISSION_SERVICE_ROUTE.getCode().equals(requestCode)) {
                response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
            }
            responseMessage
                    .append("<oauth>")
                    .append("<status>").append(requestStatus).append("</status>")
                    .append("<code>").append(requestCode).append("</code>")
                    .append("<message>").append(requestMessage).append("</message>")
                    .append("</oauth>");
        }
        LOGGER.warn("org.hzero.gateway-helper response message, {}", responseMessage.toString());
        DataBufferFactory factory = response.bufferFactory();
        DataBuffer dataBuffer = factory.wrap(responseMessage.toString().getBytes(StandardCharsets.UTF_8));
        return response.writeAndFlushWith(Flux.just(dataBuffer).map(Flux::just));
    }
}
