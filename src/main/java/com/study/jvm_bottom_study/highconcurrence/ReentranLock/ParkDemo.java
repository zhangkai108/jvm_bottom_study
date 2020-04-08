package com.study.jvm_bottom_study.highconcurrence.ReentranLock;

import java.util.concurrent.locks.LockSupport;

public class ParkDemo {


    public static void main(String[] args) {
        Thread t1 = new Thread(() -> testSync());
        t1.setName("t1");
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main ------------------------");
        LockSupport.unpark(t1);

    }

    public static void  testSync(){
        System.out.println(Thread.currentThread().getName());
        LockSupport.park();
        System.out.println("t1 start-------------------------");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t1 end-----------------");
    }
}
