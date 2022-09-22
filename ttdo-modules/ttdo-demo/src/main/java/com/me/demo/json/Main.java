package com.me.demo.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Main {


    public static void main(String[] args) {
        String s1 =
                "{\n" +
                        "  \"type\": \"123\",\n" +
                        "  \"name\": \"zhangyang\",\n" +
                        "  \"sex\": \"1\",\n" +
                        "  \"phones\": [\n" +
                        "    {\n" +
                        "      \"number\": \"14423121123\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"number\": \"12211112123\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";

        Animal animal =  JSON.parseObject(s1, Animal.class);
        System.out.println(animal.toString());


        Person person = JSON.parseObject(s1, Person.class);

        System.out.println(person.toString());
    }
}
