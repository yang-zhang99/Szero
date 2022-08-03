package com.ttdo.autoconfigure.gateway;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({WebConditionAutoConfiguration.class})
public @interface EnableGateway {

}
