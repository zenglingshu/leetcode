package thread;

/**
 * 构造出线程的死锁状态(DeadLock)
 *
 * Created by lingshu on 15/12/20.
 */
public class DeadLockThreadStatus {

    /**
     * 简单的对象，只有一个name属性
     */
    public static class ObjectLock{
        private String name;
        public ObjectLock(String name){
            this.name = name;
        }

        public String name(){
            return name;
        }
    }

    /**
     * 此线程run时，需要先获取一个对象锁，然后获取另一个对象锁
     */
    public static class DeadLock extends Thread{
        private final ObjectLock objectA;
        private final ObjectLock objectB;

        public DeadLock(ObjectLock objectA, ObjectLock objectB){
            this.objectA = objectA;
            this.objectB = objectB;
        }

        public void run(){
            synchronized (objectA){
                System.out.println(Thread.currentThread().getName() + " acquired object lock : " + objectA.name() +
                ", want to acquire " + objectB.name());
                synchronized (objectB){
                    System.out.println(Thread.currentThread().getName() + " acquired object lock : " + objectB.name() +
                            ", done.");
                }
            }
        }
    }

    public static void main(String[] args){
        /*
        死锁时程序输出结果:

        threadA acquired object lock : objectLockA, want to acquire objectLockB
        threadB acquired object lock : objectLockB, want to acquire objectLockA

         使用jstack输出结果：

         Found one Java-level deadlock:
         =============================
         "threadB":
         waiting to lock monitor 0x00007f9ce4802cb8 (object 0x00000007d55b4200, a ThreadStatus$ObjectLock),
         which is held by "threadA"
         "threadA":
         waiting to lock monitor 0x00007f9ce4805758 (object 0x00000007d55b4250, a ThreadStatus$ObjectLock),
         which is held by "threadB"

         Java stack information for the threads listed above:
         ===================================================
         "threadB":
         at ThreadStatus$DeadLock.run(ThreadStatus.java:32)
         - waiting to lock <0x00000007d55b4200> (a ThreadStatus$ObjectLock)
         - locked <0x00000007d55b4250> (a ThreadStatus$ObjectLock)
         "threadA":
         at ThreadStatus$DeadLock.run(ThreadStatus.java:32)
         - waiting to lock <0x00000007d55b4250> (a ThreadStatus$ObjectLock)
         - locked <0x00000007d55b4200> (a ThreadStatus$ObjectLock)

         Found 1 deadlock.
         */
        ObjectLock objectLockA = new ObjectLock("objectLockA");
        ObjectLock objectLockB = new ObjectLock("objectLockB");
        Thread threadA = new DeadLock(objectLockA, objectLockB);
        threadA.setName("threadA");
        threadA.start();
        Thread threadB = new DeadLock(objectLockB, objectLockA);
        threadB.setName("threadB");
        threadB.start();
    }
}
