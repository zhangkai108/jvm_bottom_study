#容器分类

## Collection
### List


### Set
### Queue
- 为高并发做准备的
- 多线程情况下 多考虑queue 少考虑list
static Queue<String> tickets = new ConcurrentLinkedQueue<>();
- BlockingQueue 天生的生产者消费者模型
- Queue接口方法
    - add()和offer()区别:
    add()和offer()都是向队列中添加一个元素。一些队列有大小限制，因此如果想在一个满的队列中加入一个新项，调用 add() 方法就会抛出一个 unchecked 异常，而调用 offer() 方法会返回 false。因此就可以在程序中进行有效的判断！
    - poll()和remove()区别：
    remove() 和 poll() 方法都是从队列中删除第一个元素。如果队列元素为空，调用remove() 的行为与 Collection 接口的版本相似会抛出异常，但是新的 poll() 方法在用空集合调用时只是返回 null。因此新的方法更适合容易出现异常条件的情况。
    - element() 和 peek() 区别：
    element() 和 peek() 用于在队列的头部查询元素。与 remove() 方法类似，在队列为空时， element() 抛出一个异常，而 peek() 返回 null。
- BlockingQueue接口方法 用来做生产者消费者 底层用的LockSupport.park()
    - put         添加一个元素                       如果队列满，则阻塞
    - take        移除并返回队列头部的元素    
- static BlockingQueue<String> strs = new LinkedBlockingQueue<>();
操作都是线程安全 原子的

## Map
- 并发发展
1. Vector HashTable自己加了synchronized线程安全，现在基本不用
2. static Map<UUID, UUID> m = Collections.synchronizedMap(new HashMap<UUID, UUID>());
3. ConcurrentHashMap
- TreeMap是排好序的 底层用的是红黑树
1. Map<String, String> map = new ConcurrentSkipListMap<>(); //高并发并且排序
2. 跳表数据结构是从最初始的链表再抽一些关键元素形成链表
