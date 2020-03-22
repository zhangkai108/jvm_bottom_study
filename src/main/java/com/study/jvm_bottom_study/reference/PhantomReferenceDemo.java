package com.study.jvm_bottom_study.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * 虚引用
 */
public class PhantomReferenceDemo {
    /*private static final List<Object> LIST = new LinkedList<>();
    private static final ReferenceQueue<M> QUEUE = new ReferenceQueue<>();
    public static void main(String[] args) throws InterruptedException {
        LinkedList<Object> list = new LinkedList<>();
        PhantomReference<M> m = new PhantomReference<>(new M(),QUEUE);
        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());

        ThreadLocal<M> tl = new ThreadLocal<>();
        tl.set(new M());
        tl.remove();
    }*/

}
