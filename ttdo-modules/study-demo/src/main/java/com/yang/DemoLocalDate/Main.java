package com.yang.DemoLocalDate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {


    public static void main(String[] args) {

        LocalDateTime a = LocalDateTime.of(2022, 7, 5, 1, 3, 1);

        LocalDateTime b = LocalDateTime.of(2022, 7, 6, 2, 4, 2);

        System.out.println("a:" + a);
        System.out.println("b:" + b);

        Duration d = Duration.between(a, b);

        System.out.println(d.toDays());

        System.out.println(d.toHours());

        System.out.println(d.toMinutes());

        System.out.println(d.toMillis());
        System.out.println(d.toMinutes() * 60 * 1000);

        StringBuilder sb = new StringBuilder();

        sb.append(d.toDays()).append(" 天  ")
                .append(d.toHours() - d.toDays() * 24).append(" 时  ")
                .append(d.toMinutes() - d.toHours() * 60).append(" 分  ")
                .append((d.toMillis() - d.toMinutes() * 60000) / 1000).append(" 秒 ");

        System.out.println(sb.toString());

    }
}
