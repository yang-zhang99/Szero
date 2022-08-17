package com.me.gateway.helper.domain.entity;

public class CheckRequest {

    public final String accessToken;

    public final String uri;

    public final String method;


    public CheckRequest(String accessToken, String uri, String method) {
        this.accessToken = accessToken;
        this.uri = uri;
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
