package com.me;

import com.me.autoconfigure.gateway.EnableGateway;
//import com.me.core.resource.annoation.EnableResourceServer;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * 网关服务
 */
@SpringBootApplication
@EnableDiscoveryClient

//@EnableResourceServer

@EnableGateway
public class TtdoGateway {

    public static void main(String[] args) {

        new SpringApplicationBuilder(TtdoGateway.class).web(WebApplicationType.REACTIVE).run(args);

    }

}
