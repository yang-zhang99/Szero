package com.ttdo.gateway.helper.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@ConfigurationProperties(prefix = GatewayHelperProperties.PREFIX)
public class GatewayHelperProperties {

    public static final String PREFIX = "y.gateway.helper";

    private Permission permission = new Permission();


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

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
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


    public static class Permission {
        private Boolean enabled = true;

        private List<String> skipPaths = Arrays.asList("/**/skip/**", "/oauth/**");

        private List<String> internalPaths = Arrays.asList("/oauth/admin/**", "/oauth/api/**", "/v2/choerodon/api-docs");

        private Long cacheSeconds = 600L;

        private Long cacheSize = 5000L;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public List<String> getSkipPaths() {
            return skipPaths;
        }

        public void setSkipPaths(List<String> skipPaths) {
            this.skipPaths = skipPaths;
        }

        public List<String> getInternalPaths() {
            return internalPaths;
        }

        public void setInternalPaths(List<String> internalPaths) {
            this.internalPaths = internalPaths;
        }

        public Long getCacheSeconds() {
            return cacheSeconds;
        }

        public void setCacheSeconds(Long cacheSeconds) {
            this.cacheSeconds = cacheSeconds;
        }

        public Long getCacheSize() {
            return cacheSize;
        }

        public void setCacheSize(Long cacheSize) {
            this.cacheSize = cacheSize;
        }

    }
}
