package com.yang.synchronizedTest;

import java.util.concurrent.TimeUnit;

public class Task {

    private String getData1;

    private String getData2;


    public synchronized void doLongTimeTask() {
        try {
            System.out.println("begin task");

            TimeUnit.SECONDS.sleep(3);

            getData1 = " 长时间处理任务后从远程返回的值 1 threadName=" + Thread.currentThread().getName();


            getData2 = " 长时间处理任务后从远程返回的值 2 threadName=" + Thread.currentThread().getName();


            System.out.println(getData1);
            System.out.println(getData2);
            System.out.println("end task");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
