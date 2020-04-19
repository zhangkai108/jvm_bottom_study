# LockSupport java实现的锁都借助了他的park和unpark方法
- 传统的wait阻塞线程得配合synchronized中锁定的对象，LockSupport却不需要
```java
想确切保证顺序就需要两个等待
public class T04_NotifyFreeLock {

	//添加volatile，使t2能够得到通知
	volatile List lists = new ArrayList();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}
	
	public static void main(String[] args) {
		T04_NotifyFreeLock c = new T04_NotifyFreeLock();
		
		final Object lock = new Object();
		
		new Thread(() -> {
			synchronized(lock) {
				System.out.println("t2启动");
				if(c.size() != 5) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("t2 结束");
				//通知t1继续执行
				lock.notify();
			}
			
		}, "t2").start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		new Thread(() -> {
			System.out.println("t1启动");
			synchronized(lock) {
				for(int i=0; i<10; i++) {
					c.add(new Object());
					System.out.println("add " + i);
					
					if(c.size() == 5) {
						lock.notify();//但是notify不会释放锁
						//释放锁，让t2得以执行
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "t1").start();
		
		
	}
}

```