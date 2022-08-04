package com.ttdo.oauth.domain.utils;

public enum ProfileCode {
    /**
     * 默认模板
     */
    OAUTH_DEFAULT_TEMPLATE(null, "main", "hzero.oauth.login.default-template"),

    ;

    private String profileKey;

    private String defaultValue;

    private String configKey;


    ProfileCode(String profileKey, String defaultValue, String configKey) {
        this.profileKey = profileKey;
        this.defaultValue = defaultValue;
        this.configKey = configKey;
    }

    public String profileKey() {
        return profileKey;
    }

    public String defaultValue() {
        return defaultValue;
    }

    public String configKey() {
        return configKey;
    }
}
