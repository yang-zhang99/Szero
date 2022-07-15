package com.ttdo.gateway.filter.metric;


import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.Map;

@Endpoint(id = "request-count")
public class MetricEndpoint {


    private final RequestCounter requestCounter;

    public MetricEndpoint(RequestCounter requestCounter) {
        this.requestCounter = requestCounter;
    }

    @ReadOperation
    public Map<String, RequestCount> read() {
        return requestCounter.getCountMap();
    }

}
