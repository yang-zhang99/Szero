package com.springDemo.web;

import com.springDemo.inte.DemoController;
import com.springDemo.inte.DemoRequestMapping;
import com.springDemo.inte.DemoRequestParam;

@DemoController
@DemoRequestMapping("/demo")
public class DemoAction {

    public void query(@DemoRequestParam("name") String name) {
        System.out.println("query");
    }
}
