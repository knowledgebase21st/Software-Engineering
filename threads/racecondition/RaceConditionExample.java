/**
 * -----------------------------------------------------------------------------
 * RaceConditionExample.java
 * -----------------------------------------------------------------------------
 *
 * Demonstrates a Race Condition in a multithreaded Java program.
 *
 * A Race Condition occurs when multiple threads access and modify
 * the same shared data simultaneously without proper synchronization.
 *
 * In this example:
 *
 * • Two threads increment the same shared counter.
 * • Each thread performs 5,000 increments.
 * • The expected final value is 10,000.
 * • Since increment() is not synchronized, some updates are lost.
 *
 * Because thread scheduling is unpredictable, the final result
 * usually differs each time the program is executed.
 *
 * Expected Value:
 *      10000
 *
 * Actual Value:
 *      Usually less than 10000
 *
 * Time Complexity:
 *      O(n)
 *      where n is the number of increment operations.
 *
 * In different run you will get different output because of race condition.
 * First Run:
 * Expected Count : 10000
 * Actual Count   : 7306
 *
 * Second Run:
 * Expected Count : 10000
 * Actual Count   : 7412
 *
 * Third Run:
 * Expected Count : 10000
 * Actual Count   : 9051
 *
 * Notice that occasionally you may get 10,000. A race condition is nondeterministic—the 
 * incorrect result depends on thread scheduling.
 *
 * -----------------------------------------------------------------------------
 */
public class RaceConditionExample {

    /**
     * Shared counter accessed by multiple threads.
     */
    static class Counter {

        /**
         * Shared variable.
         *
         * Since increment() is not synchronized,
         * concurrent updates may overwrite each other.
         */
        private int count = 0;

        /**
         * Increments the counter.
         *
         * NOTE:
         * count++ is NOT an atomic operation.
         *
         * It internally performs:
         *
         *      Read count
         *      Increment
         *      Write count
         *
         * Another thread may modify the value
         * between these steps.
         */
        public void increment() {
            count++;
        }

        /**
         * Returns the current value.
         *
         * @return Current counter value.
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
