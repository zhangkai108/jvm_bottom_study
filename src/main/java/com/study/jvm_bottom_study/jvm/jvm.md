#GC
Root Search:跟查找算法

回收算法：
- mark-sweep:标记清除 ![mark-sweep](https://github.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/marksweep.png)
- copying:拷贝![copying](https://github.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/copying.png)
- mark-compact:标记压缩![mark-compact](https://github.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/markcompact.png)

GC类型![GCType](https://github.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/YGCFGC.png)
对象gc的流程![GCProcess](https://github.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/objectgc.png)

jdk1.8 默认ps+po![ps+po](https://github.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/1.8gc.png)

分代回收：新生代：老年代=1：2
young区的垃圾回收器：
- serial：是单线程的，SWT(stop the world所有的工作线程停止),采用copying算法回收,几十兆内存，所以现在看不到了
- parallel scavenge（1.8默认）：a stop-the-world ,copying collector which use multiple GC threads，可清除几个G
- parnew：和parallel一样：但是可以组合CMS工作
old区的垃圾回收器：
- serial old:工作在老年代的serial，采用标记清楚或者标记压缩算法
- parallel old（1.8默认）：工作在老年代的 parallel scavenge
- CMS(courrent mark sweep,可跟工作线程同时工作，承上启下，开始并发回收)：可清除几十个G![CMS](https://github.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/cmsgc.png)
三个标记算法+写屏障：三色标记-错标-Increamental Update-Remark
    - 初始标记：标记根对象
    - 并发标记：顺着跟对象找
    - 重新标记：将标记错的对象再改回去
    
一般组合：serial+serial old（少见了），parallel scavenge + parallel old，parnew+CMS

分区回收：
- G1+写屏障:上百G内存(物理上没分代逻辑上可以分代) ：三色标记+SATB算法 ![CMS](https://github.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/G1.png)
- ZGC+读屏障:4T内存(物理上没分代逻辑上也没分代)：coloredPrinters算法 jdk11才出来
- Shenandoah+读屏障：和ZGC是竞争关系
- Epsilon：啥也不干，不进行任何gc（调试用/确认不用gc参与就能干完活）