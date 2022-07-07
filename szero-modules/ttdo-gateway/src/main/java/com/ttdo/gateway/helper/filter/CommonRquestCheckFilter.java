package com.ttdo.gateway.helper.filter;

import com.ttdo.gateway.helper.api.HelperFilter;
import com.ttdo.gateway.helper.entity.RequestContext;
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
