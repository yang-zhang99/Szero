package com.me.gateway.helper.domain.vo;

import com.me.gateway.helper.domain.entity.CheckState;
import com.me.core.oauth.CustomUserDetails;

import java.io.Serializable;

public class CustomUserDetailsWithResult implements Serializable {
    private static final long serialVersionUID = -2022325583037285394L;
    private CustomUserDetails customUserDetails;
    private CheckState state;
    private String message;

    public CustomUserDetails getCustomUserDetails() {
        return this.customUserDetails;
    }

    public CustomUserDetailsWithResult() {
    }

    public CustomUserDetailsWithResult(CustomUserDetails customUserDetails, CheckState state) {
        this.customUserDetails = customUserDetails;
        this.state = state;
    }

    public CustomUserDetailsWithResult(CheckState state, String message) {
        this.state = state;
        this.message = message;
    }

    public void setCustomUserDetails(CustomUserDetails customUserDetails) {
        this.customUserDetails = customUserDetails;
    }

    public CheckState getState() {
        return this.state;
    }

    public void setState(CheckState state) {
        this.state = state;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "CustomUserDetailsWithResult{customUserDetails=" + this.customUserDetails + ", state=" + this.state + ", message='" + this.message + '\'' + '}';
    }
}