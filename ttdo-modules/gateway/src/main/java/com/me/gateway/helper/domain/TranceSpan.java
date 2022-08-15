package com.me.gateway.helper.domain;

import java.time.LocalDate;

public class TranceSpan {

    private static final String SERVICE_INVOKE_KEY_TEMPLATE = "gateway:span:%s";
    private static final String SERVICE_INVOKE_VALUE_TEMPLATE = "%s";
    private static final String API_INVOKE_KEY_TEMPLATE = "gateway:span:%s:%s";
    private static final String API_INVOKE_VALUE_TEMPLATE = "%s:%s";
    private String url;
    private String service;
    private String method;
    private LocalDate today;

    public TranceSpan() {
    }

    public TranceSpan(String url, String service, String method, LocalDate today) {
        this.url = url;
        this.service = service;
        this.method = method;
        this.today = today;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDate getToday() {
        return this.today;
    }

    public void setToday(LocalDate today) {
        this.today = today;
    }

    public String getServiceInvokeKey() {
        return String.format("gateway:span:%s", String.valueOf(this.today));
    }

    public String getServiceInvokeValue() {
        return String.format("%s", this.service);
    }

    public String getApiInvokeKey() {
        return String.format("gateway:span:%s:%s", String.valueOf(this.today), this.service);
    }

    public String getApiInvokeValue() {
        return String.format("%s:%s", this.url, this.method);
    }

    public String toString() {
        return "TranceSpan{url='" + this.url + '\'' + ", service='" + this.service + '\'' + ", method='" + this.method + '\'' + ", today=" + this.today + '}';
    }
}
