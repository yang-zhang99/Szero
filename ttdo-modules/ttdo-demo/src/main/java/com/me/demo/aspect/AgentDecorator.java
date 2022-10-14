package com.me.demo.aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AgentDecorator implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        System.out.println("James ");


        Object retVal = methodInvocation.proceed();


        System.out.println("!");

        return retVal;
    }
}
