package com.study.jvm_bottom_study.reference;

public class M {
    //重写finalize会导致OOM内存溢出
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
        super.finalize();
    }
}
