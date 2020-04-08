package com.study.jvm_bottom_study.highconcurrence.ReentranLock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentranLockDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
    }
}
