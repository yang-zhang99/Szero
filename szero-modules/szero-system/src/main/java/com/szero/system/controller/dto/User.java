package com.szero.system.controller.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class User {


    @NotBlank
    private String loginName;

    @NotBlank
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
