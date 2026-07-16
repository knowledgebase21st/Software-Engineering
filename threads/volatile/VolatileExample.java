/**
 * Example:
 * Using the volatile Keyword
 *
 * The volatile keyword ensures that changes made to a shared variable
 * by one thread are immediately visible to all other threads.
 *
 * In this example:
 * - The worker thread continuously checks the value of keepRunning.
 * - The main thread changes keepRunning to false after one second.
 * - Because keepRunning is declared as volatile, the worker thread
 *   immediately observes the updated value and exits the loop.
 *
 * If the volatile keyword is removed, the worker thread may cache the
 * value locally and continue running indefinitely.
 *
 * Time Complexity:
 *      Loop iterations : O(n)
 *      Visibility check: O(1)
 *
 * Author: Sanjay Ghosh
 */
public class VolatileExample {

    /**
     * Shared flag between threads.
     *
     * The volatile keyword guarantees that every read of this variable
     * obtains the latest value written by any thread.
     */
    private static volatile boolean keepRunning = true;

    public static void main(String[] args)
            throws InterruptedException {

        /*
         * Worker thread repeatedly checks the shared flag.
         */
        Thread workerThread = new Thread(() -> {

            System.out.println("Worker thread started.");

            long counter = 0;

            /*
             * Continue processing until the main thread
             * changes the shared flag.
             */
            while (keepRunning) {
                counter++;
            }

            System.out.println(
                    "Worker thread stopped.");

            System.out.println(
                    "Total loop iterations : " + counter);

        });

        workerThread.start();

        /*
         * Allow the worker thread to execute
         * for approximately one second.
         */
        Thread.sleep(1000);

        System.out.println();

        System.out.println(
                "Main thread requesting worker to stop...");

        /*
         * Because keepRunning is volatile,
         * the worker thread immediately
         * observes this update.
         */
        keepRunning = false;

        /*
         * Wait for the worker thread
         * to terminate.
         */
        workerThread.join();

        System.out.println();

        System.out.println("Main program finished.");
    }
}
