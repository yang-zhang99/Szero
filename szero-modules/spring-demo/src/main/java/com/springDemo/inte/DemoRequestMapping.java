package com.springDemo.inte;


import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DemoRequestMapping {

    String value() default "";

}
