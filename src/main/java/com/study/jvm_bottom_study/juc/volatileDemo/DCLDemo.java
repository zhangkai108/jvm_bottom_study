package com.study.jvm_bottom_study.juc.volatileDemo;

/**
 * 0 new #3  第一步1还是0
 * 3 dup
 * 4 invokespecial #4  赋值
 * 7 astore_1 赋引用
 * 8 return
 *
 * 如果指令重排序，4和7发生交换，则还没赋值就给引用了，引用调用会出问题
 * DCL双重检查单例就会出现这个问题所以需要加上volatile防止指令重排序
 *
 */
public class DCLDemo {
    int i = 2;

    public static void main(String[] args) {
        DCLDemo d = new DCLDemo();
    }
}
