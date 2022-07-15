package com.ttdo.gateway.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

/**
 * @author bojiangzhou 2020/01/06
 */
@ConfigurationProperties(prefix = YGatewayProperties.PREFIX)
public class YGatewayProperties {

    public static final String PREFIX = "y.gateway";

    private Cors cors = new Cors();


    public Cors getCors() {
        return cors;
    }

    public void setCors(Cors cors) {
        this.cors = cors;
    }

    public static class Cors {

        /**
         * 允许跨域访问的地址
         */
        private List<String> allowedOrigins = Collections.singletonList("*");
        /**
         * 允许的请求头
         */
        private List<String> allowedHeaders = Collections.singletonList("*");
        /**
         * 允许访问的方法
         */
        private List<String> allowedMethods = Collections.singletonList("*");

        public List<String> getAllowedOrigins() {
            return allowedOrigins;
        }

        public void setAllowedOrigins(List<String> allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
        }

        public List<String> getAllowedHeaders() {
            return allowedHeaders;
        }

        public void setAllowedHeaders(List<String> allowedHeaders) {
            this.allowedHeaders = allowedHeaders;
        }

        public List<String> getAllowedMethods() {
            return allowedMethods;
        }

        public void setAllowedMethods(List<String> allowedMethods) {
            this.allowedMethods = allowedMethods;
        }
    }

}
