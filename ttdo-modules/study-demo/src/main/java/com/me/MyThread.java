package com.me;

public class MyThread extends Thread {

    private int count = 5;

    public MyThread(String name) {
        super();
        this.setName(name);
    }

    @Override
    public void run() {


        super.run();


//        try {
            System.out.println("run begin");
//            Thread.sleep(200000);
            System.out.println("run end");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


//        for (int i = 0; i < 50000000; i++) {
//
//            if (this.interrupted()) {
//                throw new RuntimeException();
//            }

//            System.out.println("i = " + (i + 1));
//        }
        //        while (count > 0) {
//            count--;
//            System.out.println("由" + this.currentThread().getName() + "  计算， " + count);
//        }
    }
}
