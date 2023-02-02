package java;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        int a = 4 >> 1;
        test17();
    }

    public static void  test1() throws InterruptedException {
        final boolean[] ddzj = new boolean[1];
        ddzj[0] = true;


        class mtpx extends ThreadPoolExecutor {
            public mtpx(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
                super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
            }

            public mtpx(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
                super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
            }

            public mtpx(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
                super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
            }

            public mtpx(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
                super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                if (ddzj[0]) {
                    Thread.currentThread().interrupt();
                    System.out.println("打断自己");
                }
            }
        }


        ThreadPoolExecutor es = new mtpx(
                1, 3, 0L, TimeUnit.HOURS, new ArrayBlockingQueue<>(10));

        try {
            es.execute(() -> {
                System.out.println("第一个任务:" + Thread.currentThread());
            });

            Thread.sleep(500000000);
            ddzj[0] = false;

            es.execute(() -> {
                System.out.println("第二个任务:" + Thread.currentThread());
            });

        } catch (Throwable t) {
            System.out.println(t);
        } finally {
            es.shutdown();
        }

        es.awaitTermination(10L, TimeUnit.HOURS);
    }

    public static void  test2() {
        ExecutorService es = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;

        System.out.println(tpe.getPoolSize());
        es.shutdown();
    }

    static void test3() throws InterruptedException {

        class TT extends TimerTask {

            @Override
            public void run() {
                throw new RuntimeException("ccc");
            }
        }

        Timer timer = new Timer();

        timer.schedule(new TT(), 1);

        Thread.sleep(2000);

        timer.schedule(new TT(), 1);

        Thread.sleep(2000);

    }

    static void test4() {
        System.out.println(Pattern.compile(".+_trust").matcher("abc_trust").matches());
    }

    static void test5() throws InterruptedException {
        ScheduledThreadPoolExecutor ses = new ScheduledThreadPoolExecutor(10);
        try {
            ses.prestartAllCoreThreads();
            ses.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
            ses.scheduleAtFixedRate(() -> {
                System.out.println("task run: " + ses.getPoolSize());
            }, 0L, 5L, TimeUnit.SECONDS);
        } finally {
//            System.out.println("shutdown");
            ses.shutdown();
        }
        System.out.println("main 退出。");
    }

    static void test6() {
        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        try {
            for (int i = 1; i <= 10; i++) {
                new Thread(() -> {
                    lock.lock(); // lock 1
                    try {
                        System.out.println("先: " + Thread.currentThread().getName());
                    } finally {
                        lock.unlock();
                    }

                    Thread.yield();

                    lock.lock();  // lock 2
                    try {
                        System.out.println("再: " + Thread.currentThread().getName());
                    } finally {
                        lock.unlock();
                    }
                }, i + "").start();
            }
            while (lock.getQueueLength() != 10) {}
            System.out.println("所有线程都已阻塞在 lock 1 处。");
        } finally {
            System.out.println("main线程释放锁。");
            lock.unlock();
        }
    }

    static void test7() throws InterruptedException {
        /*
          将getQueuedThreads方法变为public。
          这样就可以打印出线程排队顺序。
         */
        class ReentrantReadWriteLock2 extends ReentrantReadWriteLock {
            private ReentrantReadWriteLock2(boolean f) {
                super(f);
            }

            @Override
            public Collection<Thread> getQueuedThreads() {
                List<Thread> list = new ArrayList<>(super.getQueuedThreads());
                Collections.reverse(list);
                return list;
            }
        }

        /*
          重写toString方法，方便看输出。
         */
        class Thread2 extends Thread {
            Thread2(Runnable r, String name) {
                super(r, name);
            }

            @Override
            public String toString() {
                return getName();
            }
        }

        ReentrantReadWriteLock2 rrwl = new ReentrantReadWriteLock2(true);
        Lock r = rrwl.readLock();
        Lock w = rrwl.writeLock();

        AtomicInteger num = new AtomicInteger(1);
        ExecutorService es = Executors.newCachedThreadPool(runnable -> new Thread2(runnable,
                num.getAndIncrement() + ""));

        final int threadCount = 30;

        try {
            w.lock();
            r.lock();
            try {
                for (int i = 1; i <= threadCount; i++) {
                    es.execute(() -> {
                        String name = Thread.currentThread().getName();

                        // 单数编号线程取读锁，双号线程取写锁。
                        int i2 = Integer.valueOf(Thread.currentThread().getName());
                        boolean danOrShuang = i2 % 2 == 0;
                        Lock lock = danOrShuang ? w : r;
                        long sleepTime = new Random().nextInt(1000);
                        String log = (danOrShuang ? "- 写 - " : "- 读 - ") + name ;

                        // 造成一定随机延时，以打乱取锁顺序。
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        lock.lock();
                        try {
                            System.out.println(log);
                        } finally {
                            lock.unlock();
                        }
                    });
                }
            } finally {
                // 等待所有线程都已阻塞在锁上
                while (rrwl.getQueueLength() != threadCount) {}
                System.out.println(rrwl.getQueuedThreads());

                System.out.println("main 线程释放读写锁");
                /*
                由于想同时释放读、写线程。
                因此这里先放读锁，后放写锁。
                 */
                r.unlock();
                w.unlock();
            }
        } finally {
            es.shutdown();
            es.awaitTermination(1, TimeUnit.DAYS);
        }
    }

    /**
     * Java 8 的 Stream API，在并行模式下，用的是 ForkJoinPool.common。
     * 一个全局线程池。其中工作线程的最大数量是CPU核心数。
     * 工作线程数是不会超过核心数的。
     *
     * 因此如果Task中出现阻塞、或者耗费很多时间。那么会影响整个系统的Lambda并行运行。
     *
     * 下面程序中，将工作线程数设置为1。
     * 然后先提交一个长时间运行任务。
     * 再提交一个非阻塞任务。
     * 结果是，直到第一个任务处理完毕，工作线程空闲，第二个任务一直得不到运行。
     *
     * @throws Exception
     */
    static void test8() throws Exception {
        ForkJoinTask<Void> t1 = new RecursiveAction() {
            @Override
            protected void compute() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("阻塞任务");
            }
        }.fork();

        System.out.println(ForkJoinPool.commonPool().toString());

        ForkJoinTask<Void> t2 = new RecursiveAction() {
            @Override
            protected void compute() {
                System.out.println("非阻塞任务");
            }
        }.fork();

        System.out.println(ForkJoinPool.commonPool().toString());

        t1.join();
        t2.join();
        System.out.println("main退出");

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(1, TimeUnit.DAYS);
    }


    static class Value {
        private int i;

        Value(int i) {
            this.i = i;
        }

        /**
         * 排除AtomicReference依赖equals方法的可能性。
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o) {
            System.out.println("equals");
            if (o == null) {
                return false;
            } else if (!(o instanceof Value)) {
                return false;
            } else {
                return this.i == ((Value) o).i;
            }
        }
    }

    private static AtomicReference<Value> ar = new AtomicReference<>();

    /**
     * 结论：
     * 1、AtomicReference指向对象的内部属性变化，是不影响CAS操作的。
     * 2、CAS比较的是引用本身有没有变化。
     * 3、因此业务逻辑一定只能依赖"引用是否变化"。
     * 但是如果指向对象是不可变的，那么引用不变就意味着对象不变。当然前提是不考虑ABA问题。
     */
    static void test9() {
        ar.set(new Value(1));

        Value old = ar.get();

        old.i = 2;

        Value news = new Value(3);

        boolean isSuccess = ar.compareAndSet(old, news);

        System.out.println(isSuccess);
    }


    static void test10() {
        int a = 1;
        System.out.println(a == (a = 2));
        System.out.println(a);
    }

    static void test11() {
        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();

        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
//        queue.offer("e");
    }

    static void test12() {
        String antiMaskStr = "0.0.255.255";

        String[] antiMaskArray = antiMaskStr.split("\\.");

        for (int i = 0; i < antiMaskArray.length; i++) {
            antiMaskArray[i] = (255 - Integer.valueOf(antiMaskArray[i])) + "";
        }

        System.out.println(Arrays.toString(antiMaskArray));
    }


    /**
     * 必须为volatile。
     */
    volatile int v = 0;

    static void test13() {
        AtomicIntegerFieldUpdater<Main> aifu = AtomicIntegerFieldUpdater.newUpdater(Main.class,"v");

        int newValue = aifu.addAndGet(new Main(), 2);

        System.out.println(newValue);
    }

    static void test14() {
        int a = 0;
        System.out.println(a == (a = 2)); // false

        int b = 0;
        System.out.println((b = 2) == b); // true
    }

    static void test15() {
        Executors.newScheduledThreadPool(1).schedule(() -> {
            System.out.println("123");
        }, 2, TimeUnit.SECONDS);
    }

    static void test16() {
        LinkedTransferQueue<String> ltq = new LinkedTransferQueue<>();

        ltq.add("a");
        ltq.add("b");
        ltq.add("c");

        new Thread(() -> {
            try {
                System.out.println("transfer阻塞");
                ltq.transfer("d");
                System.out.println("transfer返回");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("队列中的元素" + ltq);

        // d元素其实已经入队列了
        // 但是调用transfer方法的线程一直阻塞，直到元素d被取出。
        // 并且通过什么方法取出d元素都可以，不一定是通过阻塞的take和poll方法。

        System.out.println("取出：" + ltq.remove());
        System.out.println("取出：" + ltq.remove());
        System.out.println("取出：" + ltq.remove());

        System.out.println("取出：" + ltq.remove());

        System.out.println("");
    }


    /**
     * barrierAction 会被最后一个到达的线程执行。
     * 然后所有线程都会被放开。
     *
     * 如果在栅栏打开之前的任何阶段出现异常情况，则栅栏变为broken状态。
     * 1、有阻塞在await、await(time)方法上的线程被打断。
     * 2、有阻塞在await(time)方法上的线程超时。
     * 3、在有线程等待的情况下，栅栏被reset。
     * 4、在执行 barrierAction 时出现异常
     *
     * 被打断的线程抛InterruptedException，其他被影响的线程抛BrokenBarrierException。
     */
    static void test17() {
        CyclicBarrier cb = new CyclicBarrier(3, () -> {
            System.out.println(Thread.currentThread() + ": aaaaa");
        });

        Runnable r = () -> {
            try {
                System.out.println(Thread.currentThread() + ": await");
                cb.await();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread() + ": InterruptedException");
            } catch (BrokenBarrierException e) {
                System.out.println(Thread.currentThread() + ": BrokenBarrierException");
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        Thread t3 = new Thread(r);

        t1.start();
        t2.start();
        t3.start();
    }


}
