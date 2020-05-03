#线程池

## Callable and Future
- Callable类似Runnable,但是有返回值
- Future存储返回值
```java
public class T03_Callable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> c = new Callable() {
            @Override
            public String call() throws Exception {
                return "Hello Callable";
            }
        };

        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(c); //异步

        System.out.println(future.get());//阻塞

        service.shutdown();
    }

}
```
## FutureTask
- 既是任务又是返回值，因为
public class FutureTask<V> implements RunnableFuture<V>;
public interface RunnableFuture<V> extends Runnable, Future<V>;
所以需要返回值的尽量用它别用callable 麻烦

# CompletableFuture
- 管理多个Future的结果
```java
public class T06_01_CompletableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start, end;

        /*start = System.currentTimeMillis();

        priceOfTM();
        priceOfTB();
        priceOfJD();

        end = System.currentTimeMillis();
        System.out.println("use serial method call! " + (end - start));*/

        start = System.currentTimeMillis();

        CompletableFuture<Double> futureTM = CompletableFuture.supplyAsync(()->priceOfTM());
        CompletableFuture<Double> futureTB = CompletableFuture.supplyAsync(()->priceOfTB());
        CompletableFuture<Double> futureJD = CompletableFuture.supplyAsync(()->priceOfJD());

        CompletableFuture.allOf(futureTM, futureTB, futureJD).join();
        //CompletableFuture.anyOf()任何一个返回结果就继续执行许多任务的管理

        CompletableFuture.supplyAsync(()->priceOfTM())
                .thenApply(String::valueOf)
                .thenApply(str-> "price " + str)
                .thenAccept(System.out::println);


        end = System.currentTimeMillis();
        System.out.println("use completable future! " + (end - start));

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double priceOfTM() {
        delay();
        return 1.00;
    }

    private static double priceOfTB() {
        delay();
        return 2.00;
    }

    private static double priceOfJD() {
        delay();
        return 3.00;
    }

    /*private static double priceOfAmazon() {
        delay();
        throw new RuntimeException("product not exist!");
    }*/

    private static void delay() {
        int time = new Random().nextInt(500);
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("After %s sleep!\n", time);
    }
}
```


##线程池分成两种
- 分为ThreadPoolExecutor和ForkJoinPool
- ThreadPoolExecutor
    - 参数
    ```java
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(
                2, 核心线程数 
                4, 最大线程数
                60, 线程存活时间，是除了核心线程外的 
                TimeUnit.SECONDS, 存活时间单位
                new ArrayBlockingQueue<Runnable>(4), 是哪种阻塞队列
                Executors.defaultThreadFactory(),创造线程池的工厂
                new ThreadPoolExecutor.CallerRunsPolicy());执行拒绝策略
  当线程队列和等待队列都满了之后，就执行拒绝策略
  System.out.println(tpe.getQueue());//打印等待队列
  Abort抛异常
  Discard抛弃
  DiscardOld抛弃最老的
  CallerRuns谁提交谁执行
阻塞队列：
SynchronousQueue 容量为0 你来一个必须被消费了你才能再放一个数据，这个put并不是存，add方法才是存，add直接报错说明没有容量
LinkedTransferQueue transfer方法当有take在等待时就先唤醒，如果没有，则加入到等待队列里，并阻塞 与SynchronousQueue的put方法区别是这个会存到队列里
DelayQueue 按顺序执行，一般按照时间执行某些任务PriorityQueue 和DelayQueue差不多，它是一种树结构，DelayQueue就是基于PriorityQueue实现的
LinkedBlockingQueue put会阻塞住等待take掉才会继续执行，大小不会扩容的，默认最大值就是integer最大值
ArrayBlockingQueue 和LinkedBlockingQueue类似，必须指定初始大小

   ```
- Executors线程池工具类
    - ExecutorService service = Executors.newCachedThreadPool();

