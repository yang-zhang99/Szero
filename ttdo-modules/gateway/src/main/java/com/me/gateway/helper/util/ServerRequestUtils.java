package com.me.gateway.helper.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class ServerRequestUtils {

    public static String resolveHeader(ServerHttpRequest request, String key) {
        HttpHeaders headers = request.getHeaders();
        List<String> values = headers.get(key);
        if (values == null) {
            return null;
        }
        return values.stream()
                .filter(value -> value != null && !value.isEmpty())
                .findFirst()
                .orElseGet(() -> null);
    }

    public static String resolveParam(ServerHttpRequest request, String key) {
        MultiValueMap<String, String> params = request.getQueryParams();
        List<String> values = params.get(key);
        if (values == null) {
            return null;
        }
        return values.stream()
                .filter(value -> value != null && !value.isEmpty())
                .findFirst()
                .orElseGet(() -> null);
    }
}
