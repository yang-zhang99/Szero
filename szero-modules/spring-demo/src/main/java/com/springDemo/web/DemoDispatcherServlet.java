//package com.springDemo.web;
//
//import com.springDemo.inte.*;
//
//import javax.servlet.ServletConfig;
//import javax.servlet.http.HttpServlet;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.net.URL;
//import java.util.*;
//
//public class DemoDispatcherServlet extends HttpServlet {
//
//    // 保存扫描 application.properties 配置文件中的内容
//    private Properties contextConfig = new Properties();
//
//    // 保存扫描的所有的类名
//    private List<String> classNames = new ArrayList<String>();
//
//    // 传说中的 IoC 容器，我们来它的神秘面纱
//    private Map<String, Object> ioc = new HashMap<String, Object>();
//
//    // 保存 url 和 Method 的对应关系
//    private Map<String, Method> handlerMapping = new HashMap<String, Method>();
//
//
//    public void init(ServletConfig config) {
//        // 1. 加载配置文件
//        doLoadConfig(config.getInitParameter("contextConfigLocation"));
//        // 2. 扫描相应的类
//        doScanner(contextConfig.getProperty("scanPackage"));
//        // 3. 初始化 扫描到的类，并将它们放入 IoC 容器中
//        doInstance();
//        // 4. 完成依赖注入
//
//    }
//
//
//    private void doLoadConfig(String contextConfigLocation) {
//
//        InputStream fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
//
//        try {
//            contextConfig.load(fis);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (null != fis) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
//
//    private void doScanner(String scanPackage) {
//        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
//
//        File classPath = new File(url.getFile());
//
//        for (File file : classPath.listFiles()) {
//            if (file.isDirectory()) {
//                doScanner(scanPackage + "." + file.getName());
//            } else {
//                if (!file.getName().endsWith(".class")) {
//                    continue;
//                }
//                String className = (scanPackage + "." + file.getName().replace(".class", ""));
//                classNames.add(className);
//            }
//        }
//    }
//
//    private void doInstance() {
//        if (classNames.isEmpty()) return;
//
//        for (String className : classNames) {
//            try {
//                Class<?> clazz = Class.forName(className);
//
//                if (clazz.isAnnotationPresent(DemoController.class)) {
//
//                    Object instance = clazz.newInstance();
//
//                    String beanName = clazz.getSimpleName();
//                    ioc.put(beanName, instance);
//                } else if (clazz.isAnnotationPresent(DemoService.class)) {
//                    DemoService service = clazz.getAnnotation(DemoService.class);
//                    String beanName = service.value();
//                    if ("".equals(beanName.trim())) {
//                        beanName = clazz.getSimpleName();
//                    }
//                    Object instance = clazz.newInstance();
//                    ioc.put(beanName, instance);
//                    for (Class<?> i : clazz.getInterfaces()) {
//                        if (ioc.containsKey(i.getName())) {
//                            throw new Exception("xxx");
//                        }
//                        ioc.put(i.getName(), instance);
//                    }
//                } else {
//                    continue;
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    private void doAutowirad(){
//        if (ioc.isEmpty()) return;
//
//        for (Map.Entry<String,Object> entry: ioc.entrySet()) {
//            Field[] fields = entry.getValue().getClass().getDeclaredFields();
//
//            for (Field field: fields) {
//                if (!field.isAnnotationPresent(DemoAutowired.class)) continue;
//
//                DemoAutowired autowired = field.getAnnotation(DemoAutowired.class);
//
//                String beanName = autowired.value().trim();
//
//                if ("".equals(beanName)) {
//                    beanName = field.getType().getName();
//                }
//
//                field.setAccessible(true);
//
//
//                try {
//                    field.set(entry.getValue(), ioc.get(beanName));
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
//
//    private void initHandlerMappint(){
//        if (ioc.isEmpty()) return;
//
//        for (Map.Entry<String,Object> entry: ioc.entrySet()) {
//            Class<?> clazz = entry.getValue().getClass();
//
//            if (!clazz.isAnnotationPresent(DemoController.class)) continue;
//
//            String beanUrl = "";
//
//
//            if (clazz.isAnnotationPresent(DemoRequestMapping.class)) {
//                DemoRequestMapping requestMapping = clazz.getAnnotation(DemoRequestMapping.class);
//                beanUrl = requestMapping.value();
//            }
//
//            for (Method method: clazz.getMethods()) {
//                if (!method.isAnnotationPresent(DemoRequestMapping.class)) continue;
//
//                DemoRequestMapping requestMapping = method.getAnnotation(DemoRequestMapping.class);
//
//                String url = ("/" + beanUrl + "/" + requestMapping.value().replaceAll("/+","/"));
//                handlerMapping.put(url,method);
//                System.out.println("Mapped:" + url + "," + method);
//            }
//
//
//        }
//    }
//
//}
