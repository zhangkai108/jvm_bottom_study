package com.study.jvm_bottom_study.juc.casDemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题的解决
 *
 * @author 张凯（工号：300379）
 * @since 2020/3/17 9:46
 * @version 1.0.0
 * @copyright 南京亚信网络科技公司
 */
public class ABASolveDemo {
    //static AtomicReference<Integer> atomicReference = new AtomicReference<Integer>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);
    public static void main(String[] args) {
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第1次版本号："+stamp);
            // 暂停1秒钟t1线程，保证t2拿到版本号与t1相同
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 执行ABA操作
            atomicStampedReference.compareAndSet(100, 101,stamp,stamp+1);
            stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第2次版本号："+stamp);
            atomicStampedReference.compareAndSet(101, 100, stamp,stamp+1);
//            System.out.println(Thread.currentThread().getName()+"\t第3次版本号："+atomicStampedReference.getStamp());
        }, "t1").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第1次版本号："+stamp);
            // 暂停3秒钟t2线程，保证t1完成了一次ABA操作
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp+1);
            System.out.println(Thread.currentThread().getName()+"\t执行结果："+result+"\t最新版本号："+atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"\t当前实际最新值："+atomicStampedReference.getReference());
        }, "t2").start();
    }
}