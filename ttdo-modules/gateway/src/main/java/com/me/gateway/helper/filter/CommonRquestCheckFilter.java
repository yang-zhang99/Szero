package com.me.gateway.helper.filter;

import com.me.gateway.helper.api.HelperFilter;
import com.me.gateway.helper.domain.entity.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class CommonRquestCheckFilter implements HelperFilter {
    @Override
    public int filterOrder() {
        return 90;
    }

    @Override
    public boolean shouldFilter(RequestContext requestContext) {
        return false;
    }

    @Override
    public boolean run(RequestContext context) {
        return false;
    }
}
