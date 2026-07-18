import java.util.concurrent.locks.ReentrantLock;

/**
 * -----------------------------------------------------------------------------
 * FairLockExample.java
 * -----------------------------------------------------------------------------
 *
 * Demonstrates the Fair Lock policy provided by ReentrantLock.
 *
 * A Fair Lock grants access to threads approximately in the order
 * they requested the lock (FIFO - First In, First Out).
 *
 * A Non-Fair Lock (default behavior) may allow newly arriving threads
 * to "jump ahead" of waiting threads, improving throughput but
 * potentially causing thread starvation.
 *
 * Fair Lock:
 *      ReentrantLock lock = new ReentrantLock(true);
 *
 * Non-Fair Lock:
 *      ReentrantLock lock = new ReentrantLock();
 *
 * In this example:
 *
 *  • Five worker threads compete for the same lock.
 *  • Because the lock is fair, threads obtain the lock
 *    approximately in the order they started waiting.
 *
 * Expected Output (Approximate):
 *
 * Worker-1 is waiting...
 * Worker-2 is waiting...
 * Worker-3 is waiting...
 * Worker-4 is waiting...
 * Worker-5 is waiting...
 *
 * Worker-1 acquired the lock.
 * Worker-1 released the lock.
 *
 * Worker-2 acquired the lock.
 * Worker-2 released the lock.
 *
 * Worker-3 acquired the lock.
 * ...
 *
 * Time Complexity:
 *      lock()      O(1)
 *      unlock()    O(1)
 *
 * -----------------------------------------------------------------------------
 */
public class FairLockExample {

    /**
     * Fair ReentrantLock.
     *
     * Passing "true" enables FIFO scheduling.
     */
    private static final ReentrantLock lock =
            new ReentrantLock(true);

    /**
     * Simulates work performed inside the critical section.
     */
    private static void performTask() {

        String threadName = Thread.currentThread().getName();

        System.out.println(threadName
                + " is waiting for the lock...");

        lock.lock();

        try {

            System.out.println(threadName
                    + " acquired the lock.");

            /*
             * Simulate some work.
             */
            Thread.sleep(1000);

            System.out.println(threadName
                    + " completed its work.");

        } catch (InterruptedException ex) {

            Thread.currentThread().interrupt();

        } finally {

            lock.unlock();

            System.out.println(threadName
                    + " released the lock.");
        }
    }

    /**
     * Program entry point.
     */
    public static void main(String[] args)
            throws InterruptedException {

        Thread[] workers = new Thread[5];

        for (int i = 0; i < workers.length; i++) {

            workers[i] =
                    new Thread(
                            FairLockExample::performTask,
                            "Worker-" + (i + 1));

            workers[i].start();

            /*
             * Small delay so the waiting order
             * is easy to observe.
             */
            Thread.sleep(200);
        }

        for (Thread worker : workers) {
            worker.join();
        }

        System.out.println("\nFair Lock demonstration completed.");
    }
}
