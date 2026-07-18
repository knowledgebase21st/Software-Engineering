import java.util.concurrent.locks.ReentrantLock;

/**
 * -----------------------------------------------------------------------------
 * BankAccountLockExample.java
 * -----------------------------------------------------------------------------
 *
 * Demonstrates how ReentrantLock can be used to safely update
 * shared business data.
 *
 * Scenario:
 *
 * Two ATM machines attempt to withdraw money from the same
 * bank account simultaneously.
 *
 * Without synchronization:
 *      • Both threads may read the same balance.
 *      • Both withdrawals may succeed incorrectly.
 *      • Account balance becomes inconsistent.
 *
 * With ReentrantLock:
 *      • Only one thread accesses the account at a time.
 *      • Balance remains consistent.
 *      • Race conditions are eliminated.
 *
 * Time Complexity:
 *      Deposit   : O(1)
 *      Withdraw  : O(1)
 *
 * -----------------------------------------------------------------------------
 */
public class BankAccountLockExample {

    /**
     * Represents a simple bank account.
     */
    static class BankAccount {

        /**
         * Explicit lock protecting the account.
         */
        private final ReentrantLock lock = new ReentrantLock();

        /**
         * Current account balance.
         */
        private int balance;

        /**
         * Creates a new bank account.
         *
         * @param openingBalance Initial balance.
         */
        public BankAccount(int openingBalance) {
            this.balance = openingBalance;
        }

        /**
         * Withdraws money from the account.
         *
         * Only one thread may execute this method at a time.
         *
         * @param amount Amount to withdraw.
         */
        public void withdraw(int amount) {

            lock.lock();

            try {

                String thread =
                        Thread.currentThread().getName();

                System.out.println(thread
                        + " attempting to withdraw $"
                        + amount);

                if (balance >= amount) {

                    /*
                     * Simulate processing time.
                     */
                    Thread.sleep(1000);

                    balance -= amount;

                    System.out.println(thread
                            + " withdrawal successful.");

                    System.out.println("Remaining Balance = $"
                            + balance);

                } else {

                    System.out.println(thread
                            + " withdrawal failed.");

                    System.out.println("Insufficient funds.");

                }

            } catch (InterruptedException ex) {

                Thread.currentThread().interrupt();

            } finally {

                lock.unlock();

            }
        }

        /**
         * Returns the current balance.
         *
         * @return Account balance.
         */
        public int getBalance() {
            return balance;
        }
    }

    /**
     * Program entry point.
     */
    public static void main(String[] args)
            throws InterruptedException {

        BankAccount account =
                new BankAccount(1000);

        Runnable atmTask = () -> {

            account.withdraw(700);

        };

        Thread atm1 =
                new Thread(atmTask, "ATM-1");

        Thread atm2 =
                new Thread(atmTask, "ATM-2");

        atm1.start();
        atm2.start();

        atm1.join();
        atm2.join();

        System.out.println();

        System.out.println(
                "Final Account Balance = $"
                        + account.getBalance());
    }
}
