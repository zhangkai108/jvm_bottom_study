package com.study.jvm_bottom_study.highconcurrence.volatileDemo;

/**
 * volatile可见性验证
 *
 * @author 张凯（工号：300379）
 * @since 2020/3/17 9:46
 * @version 1.0.0
 * @copyright 南京亚信网络科技公司
 */
public class Visibility {
    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(()-> {
            while (flag) {
                //do sth
                System.out.println("start");
            }
            System.out.println("end");
        }, "server").start();


        Thread.sleep(1000);

        flag = false;
    }
}
