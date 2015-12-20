package thread;

/**
 * 构造出线程的阻塞和等待状态(Blocked, Waiting & Timed Waiting)
 *
 * Created by lingshu on 15/12/20.
 */
public class BlockedWaitingThreadStatus {

    private static final Object monitor = new Object();

    /**
     * 模拟线程的waiting场景
     */
    public static class Waiting extends Thread{

        public void run(){
            // 获取对象锁
            synchronized (monitor){
                System.out.println(Thread.currentThread().getName() + " acquired monitor object lock, now release this lock. ");
                try {
                    // 休息30s，然后再释放对象锁，让其他线程可以获得此对象锁
                    Thread.sleep(30000);//此时线程处于TIMED WAITING状态
                    long start = System.currentTimeMillis();
                    monitor.wait();//释放对象锁，此时线程处于WAITING状态
                    long end = System.currentTimeMillis();
                    System.out.println(Thread.currentThread().getName() + " re-obtain monitor object lock, waited " + (end - start)/1000 + "s.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 模拟线程的notify场景
     */
    public static class Notifying extends Thread{

        public void run(){
            System.out.println(Thread.currentThread().getName() + " waiting to acquire monitor object lock... ");
            long start = System.currentTimeMillis();
            synchronized (monitor){// 进入了临界区，未拿到对象锁，此时当前线程处于BLOCKED状态
                long end = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName() + " acquired monitor object lock, waited " + (end - start)/1000 + "s.");
                try {
                    // 拿到对象锁，并休息50s
                    Thread.sleep(50000);//此时线程处于TIMED WAITING状态
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 通知其他wait的线程并释放对象锁
                monitor.notify();
            }
        }
    }

    public static void main(String[] args){

        /*
         输出结果如下：
         threadA acquired monitor object lock, now release this lock.
         threadB waiting to acquire monitor object lock...
         threadB acquired monitor object lock, waited 30s.
         threadA re-obtain monitor object lock, waited 50s.

         输出第一行和第二行后线程栈如下：

         "threadB" prio=5 tid=0x00007fa18c031800 nid=0x4f03 waiting for monitor entry [0x000070000124c000]
         java.lang.Thread.State: BLOCKED (on object monitor)
         at thread.WaitingThreadStatus$Notifying.run(WaitingThreadStatus.java:36)
         - waiting to lock <0x00000007d55b4810> (a java.lang.Object)

         "threadA" prio=5 tid=0x00007fa18c031000 nid=0x4d03 waiting on condition [0x0000700001149000]
         java.lang.Thread.State: TIMED_WAITING (sleeping)
         at java.lang.Thread.sleep(Native Method)
         at thread.WaitingThreadStatus$Waiting.run(WaitingThreadStatus.java:18)
         - locked <0x00000007d55b4810> (a java.lang.Object)


         输出第一到第三行后线程栈如下：

         "threadB" prio=5 tid=0x00007ff4a1878000 nid=0x4f03 waiting on condition [0x000070000124c000]
         java.lang.Thread.State: TIMED_WAITING (sleeping)
         at java.lang.Thread.sleep(Native Method)
         at thread.WaitingThreadStatus$Notifying.run(WaitingThreadStatus.java:39)
         - locked <0x00000007d55b4810> (a java.lang.Object)

         "threadA" prio=5 tid=0x00007ff4a28c6000 nid=0x4d03 in Object.wait() [0x0000700001149000]
         java.lang.Thread.State: WAITING (on object monitor)
         at java.lang.Object.wait(Native Method)
         - waiting on <0x00000007d55b4810> (a java.lang.Object)
         at java.lang.Object.wait(Object.java:503)
         at thread.WaitingThreadStatus$Waiting.run(WaitingThreadStatus.java:20)
         - locked <0x00000007d55b4810> (a java.lang.Object)
         */
        Thread threadA = new Waiting();
        threadA.setName("threadA");
        threadA.start();
        Thread threadB = new Notifying();
        threadB.setName("threadB");
        threadB.start();
    }
}
