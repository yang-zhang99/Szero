package com.szero;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        MyThread a = new MyThread("A");
        MyThread b = new MyThread("B");
        MyThread c = new MyThread("C");

        a.start();
        b.start();
        c.start();


//        new Thread(new UploadThread()).start();
//        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 6, 3,
//                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3)
//        );

//        for (int i = 1; i <= 10; i++) {
//            try {
//            String task = "task=" + i;
//            System.out.println("创建任务并提交到线程池中：" + task);
//            threadPool.submit(new Thread(new UploadThread()));

//            Thread.sleep(100);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
    }


}
