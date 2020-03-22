# cpu
## 计算机组成图如下：
![计算机组成](https://github.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/computer.png)
- 内存：暂时存储程序以及数据的地方（cpu要调用的指令也存在这个地方）
- 总线：cpu和内存数据交换的桥梁
## CPU组成：
- 程序计数器（Program Counter，PC）：用来指出下一条指令在主存储器中的地址
- Registers(各种其他寄存器)：存放从内存中拿来的即将执行的指令和数据
- ALU：是算术逻辑单元，对数据进行运算
- cache：![多核cpu缓存图](https://github.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/multiCoreCpu.png)因为cpu速度太快大概是缓存的100倍，所以要先把数据缓存起来，cpu计算取值先从L1，
没有再到L2、L3内存找，L1、L2一个核共享，L3多核共享，内存多cpu共享
## 超线程（如四核八线程）
- 一个运算单元对应多个线程,cache line的概念缓存行对齐伪共享(把变量前后用空字符填满64位，也就是8个字节)；
- 实现缓存一致性协议(比如MESI协议，intel用的，又称缓存锁)，是实现volatile的原理，只要一个线程里的值变了，就让其他线程里的该值变为无效，并通知去内存重新读取，有些地方无法完成的就得用总线锁
但voliate只保证可见性，并不保证原子性，volatile还能防止指令重排序


