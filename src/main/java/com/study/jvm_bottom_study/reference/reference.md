#引用（强软弱需）
##强引用
##软引用
##弱引用
![弱引用](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/weakReference.jpg)
ThreadLocal和当前线程有关，取得是当前线程带有的一个map然后存进去，
Spring的声明式事务用到了，保证同一个Connection
 Entry extends WeakReference<ThreadLocal<?>>用虚引用是因为，当前线程只要结束ThreadLocal指向的就剩一个弱引用，垃圾回收器见到就会回收，
 不然一直存在一个强引用，垃圾回收期永远回收不了会造成内存泄漏。
```java
public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }
```
```java
ThreadLocalMap getMap(Thread t) {
        return t.threadLocals;
    }
```
```java
  private void set(ThreadLocal<?> key, Object value) {

            Entry[] tab = table;
            tab[i] = new Entry(key, value);
            int sz = ++size;
            if (!cleanSomeSlots(i, sz) && sz >= threshold)
                rehash();
        }
```
```java
 static class Entry extends WeakReference<ThreadLocal<?>> {
            Object value;
            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }
```
##虚引用
堆外内存管理（jvm可以直接将数据写到属于os的内存中去，但是gc不能直接监控os的内存，所以需要借助虚引用，不回收会造成内存泄漏）
![堆外内存](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/offheapmemory.png)
jvm 有专门的gc监控队列，当指向堆外内存在jvm的对象被回收时，就会把相关信息加入到队列，专门的gc发现队列有信息时，就把堆外内存回收
