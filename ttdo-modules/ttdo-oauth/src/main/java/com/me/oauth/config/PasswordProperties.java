package com.me.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "hzero"
)
public class PasswordProperties {
    public static final String PREFIX = "hzero";
    private Captcha captcha = new Captcha();

    public PasswordProperties() {
    }

    public Captcha getCaptcha() {
        return this.captcha;
    }

    public void setCaptcha(Captcha captcha) {
        this.captcha = captcha;
    }

    public static class Captcha {
        private boolean alwaysEnable = false;

        public Captcha() {
        }

        public boolean isAlwaysEnable() {
            return this.alwaysEnable;
        }

        public void setAlwaysEnable(boolean alwaysEnable) {
            this.alwaysEnable = alwaysEnable;
        }
    }
}
