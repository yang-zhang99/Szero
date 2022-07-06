package com.ttdo.gateway.helper.entity;

public final class CheckState {


    /**
     * gatewayHelper发生异常
     */
    public static final CheckState EXCEPTION_GATEWAY_HELPER = new CheckState(501, "error.gatewayHelper.exception", "EXCEPTION_GATEWAY_HELPER");





    private final int value;

    private final String code;

    private final String name;

    private CheckState(int value, String code, String name) {
        this.value = value;
        this.code = code;
        this.name = name;
    }

    public static CheckState newState(int value, String code, String name) {
        return new CheckState(value, code, name);
    }

    public int getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
