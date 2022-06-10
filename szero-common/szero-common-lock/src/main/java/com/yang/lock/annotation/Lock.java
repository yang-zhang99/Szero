package com.yang.lock.annotation;


import com.yang.lock.enums.LockType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 *
 * @author yang99
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {


    String name() default "";

    // 锁类型
    LockType lockType() default LockType.FAIR;

    // 等待时间
    long waitTime() default 60L;

    //
    long leaseTime() default 60L;

    TimeUnit timeunit() default TimeUnit.SECONDS;

    String[] keys() default {};

}
