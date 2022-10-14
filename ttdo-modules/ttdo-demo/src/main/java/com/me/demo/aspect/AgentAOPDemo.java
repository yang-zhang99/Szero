package com.me.demo.aspect;

import org.springframework.aop.framework.ProxyFactory;

public class AgentAOPDemo {

    public static void main(String[] args) {
        Agent target = new Agent();

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvice(new AgentDecorator());
        pf.setTarget(target);

        Agent proxy = (Agent) pf.getProxy();

        target.speck();
        System.out.println(" ");

        proxy.speck();
    }
}
