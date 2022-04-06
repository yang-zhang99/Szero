package com.szero.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.szero.oauth")
public class SzeroOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SzeroOauthApplication.class, args);
    }

}
