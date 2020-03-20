package com.study.jvm_bottom_study.highconcurrence.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * CAS锁会导致ABA问题
 *
 * @param param
 * @author 张凯（工号：300379）
 * @since 2020/3/17 9:46
 * @version 1.0.0
 * @copyright 南京亚信网络科技公司
 */
public class AtomicReferenceDemo {
    static AtomicReference<Integer> atomicReference = new AtomicReference<Integer>(100);
    public static void main(String[] args) {
        new Thread(() -> {
            // 执行ABA操作
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();

        new Thread(()->{
            // 暂停1秒钟t2线程，保证t1完成了一次ABA操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 2019)+"\t" + atomicReference.get());
        }, "t2").start();
    }
}
