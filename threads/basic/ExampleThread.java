/*This is an example of Thread by extending Thread class */

class TestThread  extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(Thread.currentThread().getName() + " processing: " + i);
            try {
                Thread.sleep(500); // Pause thread execution briefly
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

public class ExampleThread {
    public static void main(String[] args) {
        // Create instances of TestThread
        TestThread thread1 = new TestThread();
        TestThread thread2 = new TestThread();

        // Start execution 
	// this internally calls the run() method
        thread1.start();
        thread2.start();
    }
}

