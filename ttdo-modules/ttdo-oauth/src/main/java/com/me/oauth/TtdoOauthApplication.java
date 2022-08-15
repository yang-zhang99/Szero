package com.me.oauth;

import com.me.autoconfigure.oauth.EnableOauth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableOauth

@EnableDiscoveryClient

@SpringBootApplication

public class TtdoOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtdoOauthApplication.class, args);
    }

}
