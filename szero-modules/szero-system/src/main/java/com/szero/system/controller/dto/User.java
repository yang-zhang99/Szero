package com.szero.system.controller.dto;

//import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class User {


    @NotBlank
    private String username;

    @NotBlank
    @Min(2)
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
