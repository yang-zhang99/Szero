package com.yang;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {

    public static void main(String[] args) throws InterruptedException {

        long nowTime = System.currentTimeMillis();
        System.out.println("当前时间" + nowTime);

        long scheduleTime = (nowTime + 10000);
        System.out.println("计划时间为" + scheduleTime);

        MyTask myTask1 = new MyTask();
        MyTask myTask2 = new MyTask();


        Timer timer = new Timer();
        Timer timer1 = new Timer();

        Thread.sleep(100);

        timer.schedule(myTask1, new Date(scheduleTime), 10000);
//        timer.schedule(myTask2, new Date(scheduleTime));


        Thread.sleep(10000);
//        timer.schedule(null, new Date(scheduleTime + 10000));
//        timer.cancel();
        Thread.sleep(Integer.MAX_VALUE);
//        System.out.println("End");
    }


}


class MyTask extends TimerTask {

    @Override
    public void run() {
        System.out.println("任务执行了，时间为：" + System.currentTimeMillis());
    }
}
