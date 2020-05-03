package com.study.jvm_bottom_study.reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * 弱引用
 * ThreadLocal.set的东西只有当前线程里才能取到，所以Spring里的事务拿的就是本地线程，控制一样的Connection不然ab互相调用，事务对象不一样会出问题
 */
public class WeakReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        WeakReference<M> m = new WeakReference<>(new M());

        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());

        ThreadLocal<M> tl = new ThreadLocal<>();
        tl.set(new M());
        tl.remove();
    }

}
