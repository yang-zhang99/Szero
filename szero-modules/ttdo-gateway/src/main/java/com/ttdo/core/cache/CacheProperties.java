package com.ttdo.core.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(
        prefix = CacheProperties.PREFIX
)
public class CacheProperties {
    public static final String PREFIX = "y.cache-value";
    private boolean enable = true;

    public CacheProperties() {
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
