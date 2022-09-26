//package com.me.demo.json;
//
//import com.alibaba.fastjson.annotation.JSONType;
//import com.azul.crs.com.fasterxml.jackson.annotation.JsonCreator;
//import com.azul.crs.com.fasterxml.jackson.annotation.JsonProperty;
//import com.azul.crs.com.fasterxml.jackson.annotation.JsonTypeInfo;
//
//import java.util.List;
//
//
//
////@JSONType(deserializer=Person.class)
////@JSONType(typeName = "person")
//
//public class Person extends Animal {
//
//    private String name;
//
//    private String sex;
//
////    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
//    private List<Phone> phones;
//
//    @JsonCreator
//    public Person(
//            @JsonProperty("type") String type) {
//        super(type);
//    }
//
////    public Person() {
////        super(null);
////    }
//
//    public List<Phone> getPhones() {
//        return phones;
//    }
//
//    public void setPhones(List<Phone> phones) {
//        this.phones = phones;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSex() {
//        return sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
//
//
//    @Override
//    public String toString() {
//        return "Person{" +
//                "name='" + name + '\'' +
//                ", sex='" + sex + '\'' +
//                ", phones=" + phones +
//                '}';
//    }
//}
