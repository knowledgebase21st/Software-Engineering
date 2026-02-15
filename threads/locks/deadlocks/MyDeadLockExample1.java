public class MyDeadLockExample1 {


	public static Object someResource1 = new Object();
	public static Object someResource2 = new Object();
	public static void main (String[] args) {

		System.out.println("Test of DeadLock -------------------");
		TestThread1 t1  = new TestThread1();
		TestThread2 t2  = new TestThread2();

		t1.start();
		t2.start();
	}

    /*Now create a class which will Acquire lock on someResource1 and also wait to Acquire lock on someResource 2*/
    private static class TestThread1 extends Thread {
    
    	public void run() {
    		/*Aquiring lock on someResource1*/
    		synchronized (someResource1) {
    			System.out.println("Thread 1 has locked someResource 1");
    
    			/*Now sleep for a minute to wait and then try to acquire lock on someResource 2*/
    			try {
    				Thread.sleep(60);
    			}
    			catch (InterruptedException ie)
    			{
    				ie.printStackTrace();
    			}
    
    			/*Now going to acquire lock on someResource2*/
    			synchronized(someResource2) {
    				System.out.println("Thread 1 has locked someResource 2");
    			}
    		}
    	}
    } /*End class TestThread1*/
    
    /*Now create a class which will Acquire lock on someResource2 and also wait to Acquire lock on someResource1*/
    private static class TestThread2 extends Thread {
    
    	public void run() {
    		/*Aquiring lock on someResource1*/
    		synchronized (someResource2) {
    			System.out.println("Thread 2 has locked someResource 2");
    
    			/*Now sleep for a minute to wait and then try to acquire lock on someResource 1*/
    			try {
    				Thread.sleep(60);
    			}
    			catch (InterruptedException ie)
    			{
    				ie.printStackTrace();
    			}
    
    			/*Now going to acquire lock on someResource1*/
    			synchronized(someResource1) {
    				System.out.println("Thread 1 has locked someResource 2");
    			}
    		}
    	}
    } /*End class TestThread1*/

} /*End of class MyDeadLockExample1*/
