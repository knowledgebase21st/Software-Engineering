/* This is an example of Starvation Free Threads
 * We used ReentrantLock(true) , The lock maintains a queue and grants access in roughly FIFO order.
 * Every thread eventually gets turn
 */


import java.util.concurrent.locks.ReentrantLock;

public class ExampleStarvationFree {

    private static final ReentrantLock lock = new ReentrantLock(true); // FAIR LOCK
    private static final int ITERATION = 10000;								       

    static class Worker extends Thread {

        private final String name;

        public Worker(String name) {
            this.name = name;
        }

        @Override
        public void run() {


		for (int iCount=0; iCount<ITERATION; iCount++) {
                lock.lock();

                try {
                    System.out.println(name + " got lock");

                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {

        new Worker("Thread-A").start();
        new Worker("Thread-B").start();
        new Worker("Thread-C").start();
    }
}
