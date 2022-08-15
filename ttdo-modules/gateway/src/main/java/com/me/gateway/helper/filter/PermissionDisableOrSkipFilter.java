package com.me.gateway.helper.filter;

import com.me.gateway.helper.api.HelperFilter;
import com.me.gateway.helper.config.GatewayHelperProperties;
import com.me.gateway.helper.entity.CheckState;
import com.me.gateway.helper.entity.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;


/**
 * 是否跳过权限校验
 */
@Component
public class PermissionDisableOrSkipFilter implements HelperFilter {
    private final AntPathMatcher matcher = new AntPathMatcher();
    private GatewayHelperProperties gatewayHelperProperties;

    public PermissionDisableOrSkipFilter(GatewayHelperProperties gatewayHelperProperties) {
        this.gatewayHelperProperties = gatewayHelperProperties;
    }

    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter(RequestContext context) {
        return true;
    }

    public boolean run(RequestContext context) {
        if (!this.gatewayHelperProperties.getPermission().getEnabled()) {
            context.response.setStatus(CheckState.SUCCESS_PERMISSION_DISABLED);
            context.response.setMessage("Permission check disabled");
            return false;
        } else {
            GatewayHelperProperties.Permission permission = this.gatewayHelperProperties.getPermission();
            if (permission.getInternalPaths().stream().anyMatch((t) -> this.matcher.match(t, context.request.uri))) {
                context.response.setStatus(CheckState.PERMISSION_WITH_IN);
                context.response.setMessage("No access to within interface");
                return false;
            } else if (permission.getSkipPaths().stream().anyMatch((t) -> this.matcher.match(t, context.request.uri))) {
                context.response.setStatus(CheckState.SUCCESS_SKIP_PATH);
                context.response.setMessage("This request match skipPath, skipPaths: " + this.gatewayHelperProperties.getPermission().getSkipPaths());
                return false;
            } else {
                return true;
            }
        }
    }
}
