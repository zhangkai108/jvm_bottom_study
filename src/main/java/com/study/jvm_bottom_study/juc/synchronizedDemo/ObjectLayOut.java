package com.study.jvm_bottom_study.juc.synchronizedDemo;

import com.study.jvm_bottom_study.juc.volatileDemo.DCLDemo;
import org.openjdk.jol.info.ClassLayout;

public class ObjectLayOut {

    int i = 3;
    public void test(){
        System.out.println("777");
        System.out.println("777");
        System.out.println("777");
        System.out.println("777");
    }
    public static void main(String[] args) {
        //证明了方法不占空间
        Object o = new Object();
        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
        Object d = new DCLDemo();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println("=========================================");
        System.out.println(ClassLayout.parseInstance(d).toPrintable());
    }
}
