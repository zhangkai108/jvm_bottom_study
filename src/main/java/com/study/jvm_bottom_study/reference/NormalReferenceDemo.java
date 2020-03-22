package com.study.jvm_bottom_study.reference;

import java.io.IOException;

/**
 * 强引用相关
 * 有强引用垃圾回收期就不会回收
 */
public class NormalReferenceDemo {
    public static void main(String[] args) throws IOException {

        M m = new M();
        m = null;
        System.gc();
        System.in.read();

    }

}
