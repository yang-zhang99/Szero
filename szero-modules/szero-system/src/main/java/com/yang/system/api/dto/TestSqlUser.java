package com.yang.system.api.dto;


import javax.validation.constraints.NotBlank;

public class TestSqlUser {


    @NotBlank
    private String loginName;

    @NotBlank
    private String password;

    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
