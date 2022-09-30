package com.me.utils;

import java.util.Random;


public class KeyGenerator {

    private static final Random RANDOM = new Random();

    private static final int BOUND = 100;

    public static String generate(){
        return System.currentTimeMillis() + "-" + RANDOM.nextInt(BOUND);
    }

}
