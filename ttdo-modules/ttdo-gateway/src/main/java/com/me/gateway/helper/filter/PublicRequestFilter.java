package com.me.gateway.helper.filter;

import com.me.gateway.helper.api.HelperFilter;
import com.me.gateway.helper.domain.entity.CheckState;
import com.me.gateway.helper.domain.entity.RequestContext;
import org.springframework.stereotype.Component;


@Component
public class PublicRequestFilter implements HelperFilter {


    public int filterOrder() {
        return 30;
    }

    public boolean shouldFilter(RequestContext context) {
        return context.getPermission().getPublicAccess();
    }

    public boolean run(RequestContext context) {
        context.response.setStatus(CheckState.SUCCESS_PUBLIC_ACCESS);
        context.response.setMessage("Have access to this 'publicAccess' interface, permission: " + context.getPermission());
        return false;
    }
}
