package com.me;

import com.me.autoconfigure.gateway.EnableGateway;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

@EnableGateway
public class Gateway {

    public static void main(String[] args) {

        new SpringApplicationBuilder(Gateway.class).web(WebApplicationType.REACTIVE).run(args);

    }

}
