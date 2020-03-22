package com.study.jvm_bottom_study.reference;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

/**
 * 软引用
 * 当内存不够的时候垃圾回收期会回收
 * 主要用在缓存里
 */
public class SoftReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024*1024*10]);
        System.out.println(m.get());
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(m.get());
        byte[] b = new byte[1024 * 1024 * 12];
        System.out.println(m.get());
    }

}
