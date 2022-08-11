package com.ttdo.gateway.helper.filter;

import com.ttdo.gateway.helper.api.HelperFilter;
import com.ttdo.gateway.helper.config.GatewayPropertiesWrapper;
import com.ttdo.gateway.helper.entity.CheckState;
import com.ttdo.gateway.helper.entity.CommonRoute;
import com.ttdo.gateway.helper.entity.RequestContext;
import com.ttdo.gateway.helper.util.ServerRequestUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

@Component
public class GetRequestRouterFilter implements HelperFilter {
    private static final String ZUUL_SERVLET_PATH = "zuul/";
    public static final String REQUEST_KEY_SEPARATOR = ":::";
    private static final String URI_SQL_DATA = "/lovs/sql/data";
    private static final String URI_SQL_MEANING = "/lovs/sql/meaning";
    private final AntPathMatcher matcher = new AntPathMatcher();
    private GatewayPropertiesWrapper properties;

    public GetRequestRouterFilter(GatewayPropertiesWrapper properties) {
        this.properties = properties;
    }

    public int filterOrder() {
        return 10;
    }

    public boolean shouldFilter(RequestContext context) {
        return true;
    }

    public boolean run(RequestContext context) {
        String requestUri = context.request.uri;
        if (requestUri.startsWith("/zuul/")) {
            requestUri = requestUri.substring(5);
        }

        CommonRoute route = this.getRoute(requestUri, this.properties.getRoutes());
        if (route == null) {
            context.response.setStatus(CheckState.PERMISSION_SERVICE_ROUTE);
            context.response.setMessage("This request mismatch any routes, uri: " + requestUri);
            return false;
        } else {
            String trueUri = this.getRequestTruePath(requestUri, route.getPath());
            if (trueUri.endsWith("/lovs/sql/data") || trueUri.endsWith("/lovs/sql/meaning")) {
                Object servletRequest = context.getServletRequest();
                String lovCode = null;
                if (servletRequest instanceof HttpServletRequest) {
                    lovCode = ((HttpServletRequest) servletRequest).getParameter("lovCode");
                } else if (servletRequest instanceof ServerHttpRequest) {
                    lovCode = ServerRequestUtils.resolveParam((ServerHttpRequest) servletRequest, "lovCode");
                }

                context.setLovCode(lovCode);
            }

            context.setTrueUri(trueUri);
            context.setRoute(route);
            context.setRequestKey(this.generateKey(trueUri, context.request.method, route.getServiceId()));
            return true;
        }
    }

    private String generateKey(String uri, String method, String service) {
        return uri + ":::" + method + ":::" + service;
    }

    private String getRequestTruePath(String uri, String routePath) {
        return "/" + this.matcher.extractPathWithinPattern(routePath, uri);
    }

    private CommonRoute getRoute(final String requestUri, final Map<String, CommonRoute> routeMap) {
        Iterator var3 = routeMap.values().iterator();

        CommonRoute zuulRoute;
        do {
            if (!var3.hasNext()) {
                return null;
            }

            zuulRoute = (CommonRoute) var3.next();
        } while (!this.matcher.match(zuulRoute.getPath(), requestUri));

        return zuulRoute;
    }
}
