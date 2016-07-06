package ua.goit.java.module3_1.semaphore;

public class SemaphoreImpl implements Semaphore{
    private volatile int permitsAvailable;
    private final Object lock;

    public SemaphoreImpl(int permitsAvailable) {
        this.permitsAvailable = permitsAvailable;
        this.lock = new Object();
    }

    @Override
    public void acquire() throws InterruptedException {
        acquire(1);
    }

    @Override
    public void acquire(int permits) throws InterruptedException {
        synchronized (lock){
            while (true) {
                System.out.println(Thread.currentThread().getName() + " is checking available acquire.");
                if (permitsAvailable - permits >= 0) {
                    System.out.println(Thread.currentThread().getName() + " was acquired.");
                    permitsAvailable -= permits;
                    Thread.sleep(5000);
                    break;
                } else {
                    System.out.println(Thread.currentThread().getName() + " is waiting.");
                    lock.wait();
                    System.out.println(Thread.currentThread().getName() + " woke up.");
                    Thread.sleep(1000);
                }
            }
        }
    }

    @Override
    public void release() {
        release(1);
    }

    @Override
    public void release(int permits) {
        synchronized (lock) {
            if (permitsAvailable + permits > 0) {
                permitsAvailable += permits;
                lock.notifyAll();
                System.out.println("Threads were notified by " + Thread.currentThread().getName());
            } else {
                System.out.println("Threads were not notified, available permits: " + permitsAvailable);
            }
        }
    }

    @Override
    public int getAvailablePermits() {
        return permitsAvailable;
    }
}
