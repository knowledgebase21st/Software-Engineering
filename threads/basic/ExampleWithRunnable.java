// Define a task by implementing the Runnable interface
class MyRunnable implements Runnable {
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

public class ExampleWithRunnable {
    public static void main(String[] args) {
        // Instantiate the task
        MyRunnable task = new MyRunnable();

        // Pass the same task instance to multiple distinct threads
        Thread thread1 = new Thread(task, "Thread-A");
        Thread thread2 = new Thread(task, "Thread-B");

        // Start execution
        thread1.start();
        thread2.start();
    }
}

