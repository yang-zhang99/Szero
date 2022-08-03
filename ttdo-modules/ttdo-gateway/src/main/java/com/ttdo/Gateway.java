package com.ttdo;

import com.ttdo.autoconfigure.gateway.EnableGateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
@EnableDiscoveryClient


@EnableGateway
public class Gateway {

    public static void main(String[] args) {

        new SpringApplicationBuilder(Gateway.class).web(WebApplicationType.REACTIVE).run(args);


//        SpringApplication.run(Gateway.class, args);
    }

}
