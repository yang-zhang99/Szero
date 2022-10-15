package com.me.demo.jdkproxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class MyProxyTest {


    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // =========================第一种==========================
//        // 1、生成$Proxy0的class文件
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
//        // 2、获取动态代理类
//        Class proxyClazz = Proxy.getProxyClass(Hello.class.getClassLoader(), Hello.class);
//        // 3、获得代理类的构造函数，并传入参数类型InvocationHandler.class
//        Constructor constructor = proxyClazz.getConstructor(InvocationHandler.class);
//        // 4、通过构造函数来创建动态代理对象，将自定义的InvocationHandler实例传入
//        Hello iHello1 = (Hello) constructor.newInstance(new MyInvocationHandler(new HelloImpl()));
//        // 5、通过代理对象调用目标方法
//        iHello1.printHello();

        // ==========================第二种=============================
        /**
         * Proxy类中还有个将2~4步骤封装好的简便方法来创建动态代理对象，
         *其方法签名为：newProxyInstance(ClassLoader loader,Class<?>[] instance, InvocationHandler h)
         */
        Hello iHello2 = (Hello) Proxy.newProxyInstance(Hello.class.getClassLoader(), // 加载接口的类加载器
                new Class[]{Hello.class}, // 一组接口
                new MyInvocationHandler(new HelloImpl())); // 自定义的InvocationHandler
        iHello2.printHello();
    }
}
