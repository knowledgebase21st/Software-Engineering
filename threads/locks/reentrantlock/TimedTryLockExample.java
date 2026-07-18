import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * -----------------------------------------------------------------------------
 * TimedTryLockExample.java
 * -----------------------------------------------------------------------------
 *
 * Demonstrates how ReentrantLock.tryLock(timeout) prevents a thread from
 * waiting indefinitely for a shared resource.
 *
 * In many enterprise applications (web servers, payment systems,
 * inventory services, etc.), blocking forever while waiting for a lock
 * can reduce throughput and responsiveness.
 *
 * tryLock(timeout) allows a thread to wait only for a specified period.
 * If the lock is not available within that time, the thread can
 * perform alternative work, retry later, or return an error.
 *
 * In this example:
 *
 *  Thread-A acquires the lock and holds it for 3 seconds.
 *  Thread-B waits for only 1 second.
 *  Since Thread-A still owns the lock, Thread-B times out.
 *
 * Expected Output:
 *
 * Thread-A attempting to acquire the lock...
 * Thread-A acquired the lock.
 *
 * Thread-B attempting to acquire the lock...
 * Thread-B timed out while waiting for the lock.
 *
 * Thread-A completed its work.
 * Thread-A released the lock.
 *
 * Time Complexity:
 *      Lock acquisition : O(1)
 *      Unlock           : O(1)
 *
 * -----------------------------------------------------------------------------
 */
public class TimedTryLockExample {

    /**
     * Shared ReentrantLock.
     */
    private static final ReentrantLock lock = new ReentrantLock();

    /**
     * Simulates work requiring exclusive access.
     */
    private static void performTask() {

        String threadName = Thread.currentThread().getName();

        System.out.println(threadName
                + " attempting to acquire the lock...");

        try {

            /*
             * Wait for up to one second to obtain the lock.
             */
            if (lock.tryLock(1, TimeUnit.SECONDS)) {

                try {

                    System.out.println(threadName
                            + " acquired the lock.");

                    /*
                     * Simulate long-running work.
                     */
                    Thread.sleep(3000);

                    System.out.println(threadName
                            + " completed its work.");

                } finally {

                    lock.unlock();

                    System.out.println(threadName
                            + " released the lock.");
                }

            } else {

                /*
                 * Lock could not be acquired within timeout.
                 */
                System.out.println(threadName
                        + " timed out while waiting for the lock.");
            }

        } catch (InterruptedException ex) {

            System.out.println(threadName
                    + " was interrupted while waiting.");

            Thread.currentThread().interrupt();
        }
    }

    /**
     * Program entry point.
     */
    public static void main(String[] args)
            throws InterruptedException {

        Thread threadA =
                new Thread(TimedTryLockExample::performTask, "Thread-A");

        Thread threadB =
                new Thread(TimedTryLockExample::performTask, "Thread-B");

        threadA.start();

        /*
         * Give Thread-A a small head start so it acquires
         * the lock first.
         */
        Thread.sleep(100);

        threadB.start();

        threadA.join();
        threadB.join();

        System.out.println("\nDemo completed.");
    }
}
