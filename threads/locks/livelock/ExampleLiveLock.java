/*This is an Example of LiveLock situation
 * When both the objects are yielding but none of them is making progress
 * they keep running 'giving away' without any end, then livelock occurs.
 *
 * In this example , 2 kings , King1 and King2 are about to board a train, but each other is yielding
 * So, the didn't make any progress and keep of yielding each other forever.
 *
 * King1 sees King2 is active → yields
 * King2 sees King1 is active → yields
 *Both keep yielding forever → livelock
 */

class Traveler {
    private String name;
    private boolean active;

    public Traveler(String name) {
        this.name = name;
        this.active = true;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void travelWith(Traveler other) {
        while (this.active) {

            // If other is active, politely yield
            if (other.isActive()) {
                System.out.println(name + ": You go first, " + other.getName());
                try {
                    Thread.sleep(100); // simulate polite delay
                } catch (InterruptedException e) {}

                // yield control and retry
                continue;
            }

            // If other is not active, proceed
            System.out.println(name + ": working now!");
            this.active = false;
        }
    }
}

public class ExampleLiveLock {
    public static void main(String[] args) {

        Traveler king1 = new Traveler("King1");
        Traveler king2 = new Traveler("King2");

        Thread t1 = new Thread(() -> king1.travelWith(king2));
        Thread t2 = new Thread(() -> king2.travelWith(king1));

        t1.start();
        t2.start();
    }
}
