华为镜像下载jdk：https://repo.huaweicloud.com/java/jdk/
linux下载arthas：curl -O https://alibaba.github.io/arthas/arthas-boot.jar

更多的启动方式可以参考 help 帮助命令
常用命令：
- dashboard 当前系统的实时数据面板
- thread 查看当前 JVM 的线程堆栈信息
    - -n5 内存占用前五的线程
    - -d 查看死锁的线程
- sc 查看JVM 已加载的类信息 sm sc *com.study.*
- sm 查看已加载类的方法信息 sm *jvm.T15_FullGC_Problem01*
- trace 方法内部调用路径，并输出方法路径上的每个节点上耗时
- monitor 方法执行监控 方法的输入和输出
