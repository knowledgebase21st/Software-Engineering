/*This is an Example where LiveLock situation is fixed
 * Do a priority based fix
 */
import java.util.Random;
class TravelerFixed {
    private int  id;
    private String name;
    private boolean active;

    private Random random = new Random();
    public TravelerFixed(int id, String name) {
        this.id = id;
	this.name = name;
        this.active = true;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void travelWith(TravelerFixed other) {
        while (this.active) {

            // If other is active, politely yield
            if (id < other.getID()) {
                System.out.println(name + ": I am going first, " + other.getName());
		break;
            }

            // If other is not active, proceed
            this.active = false;
        }
    }
}

public class ExampleLiveLockFixed {
    public static void main(String[] args) {

        TravelerFixed king1 = new TravelerFixed(1, "King1");
        TravelerFixed king2 = new TravelerFixed(2, "King2");

        Thread t1 = new Thread(() -> king1.travelWith(king2));
        Thread t2 = new Thread(() -> king2.travelWith(king1));

        t1.start();
        t2.start();
    }
}
