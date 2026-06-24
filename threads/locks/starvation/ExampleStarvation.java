/* This is a simple example of Starvation
 * where the priority is set. The High Priority Thread is acquiring locks first 
 */

public class ExampleStarvation {

    private static final Object lock = new Object();

    static class HighPriorityWorker extends Thread {

        public HighPriorityWorker() {
            setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void run() {

            for (int icount=0;icount<5000;icount++) {
                synchronized (lock) {
                    System.out.println("High Priority Thread running");
                }
            }
        }
    }

    static class LowPriorityWorker extends Thread {

        public LowPriorityWorker() {
            setPriority(Thread.MIN_PRIORITY);
        }

        @Override
        public void run() {

            for (int icount=0;icount<5000;icount++) {
                synchronized (lock) {
                    System.out.println("Low Priority Thread running");
                }
            }
        }
    }

    public static void main(String[] args) {

        new HighPriorityWorker().start();
        new LowPriorityWorker().start();
    }
}
