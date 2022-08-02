package com.ttdo.oauth;

import com.ttdo.autoconfigure.oauth.EnableOauth;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@EnableOauth
@MapperScan("com.ttdo.oauth.infra.mapper")
public class TtdoOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtdoOauthApplication.class, args);
    }

}
