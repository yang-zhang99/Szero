package com.me.lock.enums;


public enum ServerPattern {
    CLUSTER("cluster"),
    REPLICATED("replicated"),
    SENTINEL("sentinel"),
    MASTER_SLAVE("master_slave"),
    SINGLE("single");

    private String pattern;

    private ServerPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return this.pattern;
    }
}