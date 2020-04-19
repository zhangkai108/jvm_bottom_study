# volatile的用途
尽量不要用，如果要用尽量修饰基本类型，不要修饰引用类型，因为它是对引用保持可见性，引用对象字段值发生改变它是观察不到的

## 1.线程可见性
见Visibility.java线程可见性验证

## 2.防止指令重排序

### 问题：DCL单例需不需要加volatile？
需要，因为指令重排序

### CPU的基础知识

* 缓存行对齐
  缓存行64个字节是CPU同步的基本单位，缓存行隔离会比伪共享效率要高
  Disruptor


  MESI

* 伪共享

* 合并写
  CPU内部的4个字节的Buffer

  ```java
  package com.mashibing.juc.c_029_WriteCombining;
  
  public final class WriteCombining {
  
      private static final int ITERATIONS = Integer.MAX_VALUE;
      private static final int ITEMS = 1 << 24;
      private static final int MASK = ITEMS - 1;
  
      private static final byte[] arrayA = new byte[ITEMS];
      private static final byte[] arrayB = new byte[ITEMS];
      private static final byte[] arrayC = new byte[ITEMS];
      private static final byte[] arrayD = new byte[ITEMS];
      private static final byte[] arrayE = new byte[ITEMS];
      private static final byte[] arrayF = new byte[ITEMS];
  
      public static void main(final String[] args) {
  
          for (int i = 1; i <= 3; i++) {
              System.out.println(i + " SingleLoop duration (ns) = " + runCaseOne());
              System.out.println(i + " SplitLoop  duration (ns) = " + runCaseTwo());
          }
      }
  
      public static long runCaseOne() {
          long start = System.nanoTime();
          int i = ITERATIONS;
  
          while (--i != 0) {
              int slot = i & MASK;
              byte b = (byte) i;
              arrayA[slot] = b;
              arrayB[slot] = b;
              arrayC[slot] = b;
              arrayD[slot] = b;
              arrayE[slot] = b;
              arrayF[slot] = b;
          }
          return System.nanoTime() - start;
      }
  
      public static long runCaseTwo() {
          long start = System.nanoTime();
          int i = ITERATIONS;
          while (--i != 0) {
              int slot = i & MASK;
              byte b = (byte) i;
              arrayA[slot] = b;
              arrayB[slot] = b;
              arrayC[slot] = b;
          }
          i = ITERATIONS;
          while (--i != 0) {
              int slot = i & MASK;
              byte b = (byte) i;
              arrayD[slot] = b;
              arrayE[slot] = b;
              arrayF[slot] = b;
          }
          return System.nanoTime() - start;
      }
  }
  
  ```





### volatile如何解决指令重排序

1: volatile i

2: ACC_VOLATILE

3: JVM的内存屏障

4：hotspot实现

bytecodeinterpreter.cpp

```c++
int field_offset = cache->f2_as_index();
          if (cache->is_volatile()) {
            if (support_IRIW_for_not_multiple_copy_atomic_cpu) {
              OrderAccess::fence();

#endif
  }
}
```

