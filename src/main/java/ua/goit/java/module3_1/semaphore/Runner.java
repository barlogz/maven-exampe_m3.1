package ua.goit.java.module3_1.semaphore;

public class Runner {
    private static SemaphoreImpl semaphore;

    public static void main(String[] args) {
        semaphore = new SemaphoreImpl(1);
        try {
            new Runner().testAcquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testAcquire() throws Exception {
        int threads = 3;
        semaphore = new SemaphoreImpl(1);

        for (int i = 0; i < threads; i++) {
            new Thread(new WorkerForAcquire()).start();
        }

        while (WorkerForAcquire.counter < threads) {
            Thread.sleep(3000);
        }
    }

    private static class WorkerForAcquire implements Runnable {
        static int counter = 0;

        @Override
        public void run() {
            try {
                semaphore.acquire();
                Thread.sleep(1000);
                semaphore.release();
                counter++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
