package com.me.gateway.filter.metric;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;

import java.util.List;

/**
 * @author XCXCXCXCX
 * @version 1.2.0
 */
@Endpoint(id = "refresh-metric-rule")
public class MetricRuleEndpoint {

    private RequestCountRules requestCountRules;

    public MetricRuleEndpoint(RequestCountRules requestCountRules) {
        this.requestCountRules = requestCountRules;
    }

    @WriteOperation
    public List<Long> refresh() {
        return requestCountRules.refreshRules();
    }

}
