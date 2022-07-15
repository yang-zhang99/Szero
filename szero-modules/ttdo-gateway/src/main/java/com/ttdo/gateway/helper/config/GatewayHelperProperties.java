package com.ttdo.gateway.helper.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collection;

@ConfigurationProperties(prefix = GatewayHelperProperties.PREFIX)
public class GatewayHelperProperties {

    public static final String PREFIX = "y.gateway.helper";


    private Filter filter = new Filter();


    private boolean enabledJwtLog = false;


    private String jwtKey = "hzero";

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }


    public boolean isEnabledJwtLog() {
        return enabledJwtLog;
    }

    public void setEnabledJwtLog(boolean enabledJwtLog) {
        this.enabledJwtLog = enabledJwtLog;
    }

    public String getJwtKey() {
        return jwtKey;
    }

    public void setJwtKey(String jwtKey) {
        this.jwtKey = jwtKey;
    }

    public static class Filter {

        private CollectSpan collectSpan = new CollectSpan();

        public CollectSpan getCollectSpan() {
            return collectSpan;
        }

        public void setCollectSpan(CollectSpan collectSpan) {
            this.collectSpan = collectSpan;
        }

        public static class CollectSpan {

            private boolean enabled = true;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }


    }
}
