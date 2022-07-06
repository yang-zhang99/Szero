package com.ttdo.gateway.helper.entity;

public class CheckRequest {

    public final String uri;

    public final String accessToken;

    public final String method;

    public CheckRequest(String uri, String accessToken, String method) {
        this.uri = uri;
        this.accessToken = accessToken;
        this.method = method;
    }

    @Override
    public String toString() {
        return "CheckRequest{" +
                "accessToken='" + accessToken + '\'' +
                ", uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
