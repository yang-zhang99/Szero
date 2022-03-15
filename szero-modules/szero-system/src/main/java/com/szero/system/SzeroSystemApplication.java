package com.szero.system;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.beanvalidation.BeanValidationPostProcessor;

@SpringBootApplication
@ComponentScan("com.szero.system.controller")
public class SzeroSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SzeroSystemApplication.class, args);
    }


}
