/**
 * -----------------------------------------------------------------------------
 * RaceConditionSynchronized.java
 * -----------------------------------------------------------------------------
 *
 * Demonstrates how the synchronized keyword prevents a Race Condition.
 *
 * A Race Condition occurs when multiple threads update shared data
 * simultaneously without proper synchronization.
 *
 * In this example:
 *
 * • Two threads increment the same shared counter.
 * • Each thread performs 5,000 increments.
 * • The increment() method is synchronized.
 * • Only one thread can execute increment() at a time.
 *
 * As a result:
 *
 * • No updates are lost.
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
public class RaceConditionSynchronized {

    /**
     * Shared counter accessed by multiple threads.
     */
    static class Counter {

        /**
         * Shared counter.
         */
        private int count = 0;

        /**
         * Thread-safe increment operation.
         *
         * The synchronized keyword ensures that only one thread
         * can execute this method at any given time.
         */
        public synchronized void increment() {
            count++;
        }

        /**
         * Returns the current counter value.
         *
         * @return Counter value.
         */
        public int getCount() {
            return count;
        }
    }

    /**
     * Program entry point.
     */
    public static void main(String[] args)
            throws InterruptedException {

        Counter counter = new Counter();

        /*
         * Each thread increments the counter
         * 5,000 times.
         */
        Runnable task = () -> {

            for (int i = 0; i < 5000; i++) {
                counter.increment();
            }

        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");

        thread1.start();
        thread2.start();

        /*
         * Wait for both threads to complete.
         */
        thread1.join();
        thread2.join();

        System.out.println("Expected Count : 10000");
        System.out.println("Actual Count   : " + counter.getCount());
    }
}
