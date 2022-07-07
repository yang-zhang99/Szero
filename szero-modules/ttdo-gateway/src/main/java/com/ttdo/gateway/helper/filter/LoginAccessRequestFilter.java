package com.ttdo.gateway.helper.filter;

import com.ttdo.gateway.helper.api.HelperFilter;
import com.ttdo.gateway.helper.entity.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class LoginAccessRequestFilter implements HelperFilter {
    @Override
    public int filterOrder() {
        return 60;
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
