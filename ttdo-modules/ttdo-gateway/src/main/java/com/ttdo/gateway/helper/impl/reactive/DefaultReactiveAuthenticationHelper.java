package com.ttdo.gateway.helper.impl.reactive;

import com.ttdo.gateway.helper.api.reactive.ReactiveAuthenticationHelper;
import com.ttdo.gateway.helper.entity.CheckRequest;
import com.ttdo.gateway.helper.entity.CheckResponse;
import com.ttdo.gateway.helper.entity.RequestContext;
import com.ttdo.gateway.helper.entity.ResponseContext;
import com.ttdo.gateway.helper.impl.HelperChain;
import com.ttdo.gateway.helper.util.ServerRequestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

public class DefaultReactiveAuthenticationHelper implements ReactiveAuthenticationHelper {

    private static final String BEARER_PREFIX = "Bearer ";
    private final HelperChain chain;

    public DefaultReactiveAuthenticationHelper(HelperChain chain) {
        this.chain = chain;
    }

    @Override
    public ResponseContext authentication(ServerWebExchange exchange) {
        RequestContext requestContext = RequestContext.initRequestContext(
                new CheckRequest("Bearer ".toLowerCase() + this.parse(exchange.getRequest()),
                        exchange.getRequest().getURI().getPath(), exchange.getRequest().getMethod().name().toLowerCase()),
                new CheckResponse());

        requestContext.setServletRequest(exchange.getRequest());
        return this.chain.doFilter(requestContext);
    }


    private String parse(final ServerHttpRequest req) {
        String accessToken = ServerRequestUtils.resolveHeader(req, "Authorization");
        if (StringUtils.isEmpty(accessToken)) {
            accessToken = ServerRequestUtils.resolveParam(req, "access_token");
        }

        if (org.springframework.util.StringUtils.startsWithIgnoreCase(accessToken, "Bearer ")) {
            accessToken = accessToken.substring("Bearer ".length());
        }

        return accessToken;
    }

}
