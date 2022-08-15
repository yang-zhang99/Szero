package com.me.oauth.domain.utils;

public enum ProfileCode {
    /**
     * 默认模板
     */
    OAUTH_DEFAULT_TEMPLATE(null, "main", "hzero.oauth.login.default-template"),
    /**
     * 是否显示语言
     */
    OAUTH_SHOW_LANGUAGE("HOTH.SHOW_LANGUAGE", "1", "hzero.oauth.show-language"),
    /**
     * 默认语言
     */
    OAUTH_DEFAULT_LANGUAGE("HOTH.DEFAULT_LANGUAGE", "zh_CN", "hzero.oauth.default-language"),
    ;


    // key
    private String profileKey;
    // 默认值
    private String defaultValue;
    // 配置值
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
