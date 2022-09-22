package com.me.demo.json;


import com.alibaba.fastjson.annotation.JSONType;

//@JSONType(seeAlso={Person.class})
public class Animal {

    private String type;

    public Animal(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "type='" + type + '\'' +
                '}';
    }
}
