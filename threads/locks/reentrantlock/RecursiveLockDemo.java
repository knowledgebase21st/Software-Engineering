import java.util.concurrent.locks.ReentrantLock;

/**
 * -----------------------------------------------------------------------------
 * RecursiveLockDemo.java
 * -----------------------------------------------------------------------------
 *
 * Demonstrates how a ReentrantLock allows the same thread to acquire
 * the same lock multiple times without causing a deadlock.
 *
 * Every successful call to lock() increases the internal Hold Count.
 * Every call to unlock() decreases the Hold Count.
 *
 * The lock is completely released only when the Hold Count reaches zero.
 *
 * This behavior makes ReentrantLock ideal for recursive methods that
 * require synchronized access to shared resources.
 *
 * Example Output:
 *
 * Number: 3 | Current Hold Count: 1
 * Number: 2 | Current Hold Count: 2
 * Number: 1 | Current Hold Count: 3
 * Number: 0 | Current Hold Count: 4
 * Unlocked at level 0 | Remaining Hold Count: 3
 * Unlocked at level 1 | Remaining Hold Count: 2
 * Unlocked at level 2 | Remaining Hold Count: 1
 * Unlocked at level 3 | Remaining Hold Count: 0
 *
 * Time Complexity:
 *      O(n)
 *      where n is the recursion depth.
 *
 * -----------------------------------------------------------------------------
 */
public class RecursiveLockDemo {

    /**
     * A shared ReentrantLock.
     *
     * Since it is reentrant, the same thread may acquire it
     * multiple times.
     */
    private static final ReentrantLock lock = new ReentrantLock();

    /**
     * Recursively counts down while repeatedly acquiring
     * the same lock.
     *
     * @param number Current recursion depth.
     */
    public static void recursiveCountdown(int number) {

        /*
         * Acquire the lock.
         *
         * If this thread already owns the lock,
         * the Hold Count is incremented.
         */
        lock.lock();

        try {

            System.out.println(
                    "Number: "
                    + number
                    + " | Current Hold Count: "
                    + lock.getHoldCount());

            /*
             * Base condition.
             */
            if (number == 0) {
                return;
            }

            /*
             * Recursive call.
             *
             * The same thread acquires
             * the lock again.
             */
            recursiveCountdown(number - 1);

        } finally {

            /*
             * Release one level of locking.
             *
             * Every unlock() decreases
             * the Hold Count by one.
             */
            lock.unlock();

            System.out.println(
                    "Unlocked at level "
                    + number
                    + " | Remaining Hold Count: "
                    + lock.getHoldCount());
        }
    }

    /**
     * Program entry point.
     */
    public static void main(String[] args) {

        System.out.println("Starting Recursive ReentrantLock Demo...\n");

        recursiveCountdown(3);

        System.out.println("\nDemo completed.");
    }
}
