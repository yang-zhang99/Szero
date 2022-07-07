package com.ttdo.gateway.helper.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(
        prefix = "y.filter"
)
public class ListFilterProperties {
    private WhiteList whiteList = new WhiteList();
    private BlackList blackList = new BlackList();

    public ListFilterProperties() {
    }

    public WhiteList getWhiteList() {
        return this.whiteList;
    }

    public ListFilterProperties setWhiteList(WhiteList whiteList) {
        this.whiteList = whiteList;
        return this;
    }

    public BlackList getBlackList() {
        return this.blackList;
    }

    public ListFilterProperties setBlackList(BlackList blackList) {
        this.blackList = blackList;
        return this;
    }

    public static class BlackList {
        private boolean enable = false;
        private List<String> ip;

        public BlackList() {
        }

        public boolean isEnable() {
            return this.enable;
        }

        public BlackList setEnable(boolean enable) {
            this.enable = enable;
            return this;
        }

        public List<String> getIp() {
            return this.ip;
        }

        public BlackList setIp(List<String> ip) {
            this.ip = ip;
            return this;
        }
    }

    public static class WhiteList {
        private boolean enable = false;
        private List<String> ip;

        public WhiteList() {
        }

        public boolean isEnable() {
            return this.enable;
        }

        public WhiteList setEnable(boolean enable) {
            this.enable = enable;
            return this;
        }

        public List<String> getIp() {
            return this.ip;
        }

        public WhiteList setIp(List<String> ip) {
            this.ip = ip;
            return this;
        }
    }


}
