package com.springDemo.inte;


import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DemoRequestParam {

    String value() default "";

}
