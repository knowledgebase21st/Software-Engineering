import java.util.concurrent.atomic.AtomicInteger;

/**
 * -----------------------------------------------------------------------------
 * RaceConditionAtomicInteger.java
 * -----------------------------------------------------------------------------
 *
 * Demonstrates how AtomicInteger prevents Race Conditions without using
 * the synchronized keyword.
 *
 * AtomicInteger belongs to the java.util.concurrent.atomic package and
 * provides atomic operations such as increment, decrement and compare-and-set.
 *
 * Unlike a normal integer, AtomicInteger performs updates using
 * Compare-And-Swap (CAS), allowing multiple threads to update a shared
 * counter safely without explicit locking.
 *
 * In this example:
 *
 * • Two threads increment the same AtomicInteger.
 * • Each thread performs 5,000 increments.
 * • No synchronization is required.
 * • The final counter value is always correct.
 *
 * Expected Value:
 *      10000
 *
 * Actual Value:
 *      10000
 *
 * Time Complexity:
 *      O(n)
 *      where n is the number of increment operations.
 *
 * -----------------------------------------------------------------------------
 */
public class RaceConditionAtomicInteger {

    /**
     * Shared thread-safe counter.
     */
    private static final AtomicInteger counter =
            new AtomicInteger(0);

    /**
     * Program entry point.
     */
    public static void main(String[] args)
            throws InterruptedException {

        /*
         * Each thread increments the counter
         * 5,000 times.
         */
        Runnable task = () -> {

            for (int i = 0; i < 5000; i++) {

                /*
                 * Thread-safe atomic increment.
                 */
                counter.incrementAndGet();

            }

        };

        Thread thread1 =
                new Thread(task, "Thread-1");

        Thread thread2 =
                new Thread(task, "Thread-2");

        thread1.start();
        thread2.start();

        /*
         * Wait for both threads to finish.
         */
        thread1.join();
        thread2.join();

        System.out.println("Expected Count : 10000");
        System.out.println("Actual Count   : "
                + counter.get());
    }
}
