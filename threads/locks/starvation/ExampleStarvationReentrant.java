/* This is an example of Starvation 
 * Here the Greedy thread acquires lock repeatedly while the 
 * Starving thread waits
 */

import java.util.concurrent.locks.ReentrantLock;

public class ExampleStarvationReentrant {

    private static final ReentrantLock lock = new ReentrantLock();
    private static final int ITERATION = 10000;

    static class GreedyWorker extends Thread {

        @Override
        public void run() {

            for (int icount=0;icount<ITERATION; icount++) {

                lock.lock();

                try {
                    System.out.println("Greedy thread acuired lock");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class StarvingWorker extends Thread {

        @Override
        public void run() {

            for (int icount=0;icount<ITERATION; icount++) {

                lock.lock();

                try {
                    System.out.println("Starving thread acuired lock");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {

        new GreedyWorker().start();
        new GreedyWorker().start();
        new GreedyWorker().start();

        new StarvingWorker().start();
    }
}
