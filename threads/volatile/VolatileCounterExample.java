/**
 * Example:
 * volatile Does NOT Guarantee Atomicity
 *
 * This example demonstrates one of the most common misconceptions
 * about the volatile keyword.
 *
 * Although volatile guarantees that all threads see the latest value
 * of a shared variable, it does NOT make compound operations atomic.
 *
 * The statement:
 *
 *      counter++;
 *
 * consists of three separate operations:
 *
 *      1. Read the current value
 *      2. Increment the value
 *      3. Write the updated value
 *
 * If multiple threads execute these steps simultaneously,
 * some updates may be lost.
 *
 * Expected Result:
 *      The final counter value will usually be less than
 *      the expected value.
 *
 * Time Complexity:
 *      O(n)
 *
 * Author: Sanjay Ghosh
 */
public class VolatileCounterExample {

    /**
     * Shared counter.
     *
     * volatile guarantees visibility but NOT atomicity.
     */
    private static volatile int counter = 0;

    /** Number of increments performed by each thread. */
    private static final int ITERATIONS = 100000;

    public static void main(String[] args)
            throws InterruptedException {

        /*
         * Thread 1 increments the counter.
         */
        Thread thread1 = new Thread(() -> {

            for (int i = 0; i < ITERATIONS; i++) {
                counter++;
            }

        });

        /*
         * Thread 2 increments the counter.
         */
        Thread thread2 = new Thread(() -> {

            for (int i = 0; i < ITERATIONS; i++) {
                counter++;
            }

        });

        thread1.start();
        thread2.start();

        /*
         * Wait for both threads to complete.
         */
        thread1.join();
        thread2.join();

        System.out.println("Expected Counter : "
                + (ITERATIONS * 2));

        System.out.println("Actual Counter   : "
                + counter);
    }
}
