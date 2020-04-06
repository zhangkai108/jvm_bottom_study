#GC
Root Search:跟查找算法

回收算法：
- mark-sweep:标记清除 ![mark-sweep](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/marksweep.png)
- copying:拷贝![copying](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/copying.png)
- mark-compact:标记压缩![mark-compact](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/markcompact.png)

GC类型![GCType](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/YGCFGC.png)
对象gc的流程![GCProcess](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/objectgc.png)

jdk1.8 默认ps+po![ps+po](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/1.8gc.png)

分代回收：新生代：老年代=1：2
young区的垃圾回收器：
- serial：是单线程的，SWT(stop the world所有的工作线程停止),采用copying算法回收,几十兆内存，所以现在看不到了
- parallel scavenge（1.8默认）：a stop-the-world ,copying collector which use multiple GC threads，可清除几个G
- parnew：和parallel一样：但是可以组合CMS工作
old区的垃圾回收器：
- serial old:工作在老年代的serial，采用标记清楚或者标记压缩算法
- parallel old（1.8默认）：工作在老年代的 parallel scavenge
- CMS(courrent mark sweep,可跟工作线程同时工作，承上启下，开始并发回收)：可清除几十个G![CMS](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/cmsgc.png)
三个标记算法+写屏障：三色标记-错标-Increamental Update-Remark
    - 初始标记：标记根对象
    - 并发标记：顺着跟对象找
    - 重新标记：将标记错的对象再改回去

## CMS 三色标记算法
- 黑：自己已经标记，fields已标记完
- 灰：自己已经标记，还没来得及标记fields
- 白：没有遍历到的节点
 
 黑色标记完成之后，后来又指白色，就会有漏标，等Increamental Update来纠错
    
一般组合：serial+serial old（少见了），parallel scavenge + parallel old，parnew+CMS

分区回收：
- G1+写屏障:上百G内存(物理上没分代逻辑上可以分代) ：三色标记+SATB算法 ![CMS](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/G1.png)
                                                                        
- ZGC+读屏障:4T内存(物理上没分代逻辑上也没分代)：coloredPrinters算法 jdk11才出来
- Shenandoah+读屏障：和ZGC是竞争关系
- Epsilon：啥也不干，不进行任何gc（调试用/确认不用gc参与就能干完活）




##  jvm常用命令：
- jmap 查看对立面的对象信息，有OOM则从可以从linux上dump下来对象信息 用jvisualvm分析
- jstack 看线程死锁
- java -XX:+UseG1GC -XX:MaxGCPauseMillis=200m -jar ...修改为G1垃圾回收器
- jstat命令可以查看堆内存各部分的使用量，以及加载类的数量

CMS 处理过程有七个步骤：
1. 初始标记(CMS-initial-mark) ,会导致swt；
2. 并发标记(CMS-concurrent-mark)，与用户线程同时运行；
3. 预清理（CMS-concurrent-preclean），与用户线程同时运行；
4. 可被终止的预清理（CMS-concurrent-abortable-preclean） 与用户线程同时运行；
5. 重新标记(CMS-remark) ，会导致swt；
6. 并发清除(CMS-concurrent-sweep)，与用户线程同时运行；
7. 并发重置状态等待下次CMS的触发(CMS-concurrent-reset)，与用户线程同时运行；