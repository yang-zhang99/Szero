package com.ttdo.autoconfigure.oauth;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(OauthAutoConfiguration.class)
public @interface EnableOauth {
}
