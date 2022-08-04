package com.ttdo.oauth;

import com.ttdo.autoconfigure.oauth.EnableOauth;
import org.mybatis.spring.annotation.MapperScan;
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
