import java.util.LinkedList;
import java.util.concurrent.locks.*;
import java.util.concurrent.ThreadLocalRandom;

// *****************************************************************************
class Renter implements Runnable {

    private Lender lender;
    private int interestedIn;
    private String name;

    public Renter(Lender lender, String name, int interestedIn) {
        this.lender = lender;
        this.interestedIn = interestedIn;
        this.name = name;
    }

    public void run() {
        System.out.println(name + " going to get car " + interestedIn);
        Car car = this.lender.rentCar(this.interestedIn);

        System.out.println(name + " got car  " + interestedIn);
        // Distance
        int distance = ThreadLocalRandom.current().nextInt(1, 100);
        car.drive(distance);

        System.out.println(name + " returning car " + interestedIn);
        this.lender.returnCar(car, this.interestedIn);
    }
}

// *****************************************************************************
class Car {

    private Integer mileage = 0;

    void drive(Integer distance) {
        try {
            // Do the driving
            Thread.sleep(distance);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Add up the mileage
        mileage += distance;
    }

    // getter
    public Integer mileage() {
        return mileage;
    }
}

// *****************************************************************************
class Lender {

    private LinkedList<Lock> locks;
    private LinkedList<Car> cars;
    private Integer totalMileage = 0;

    public Lender(int numCars) {
        this.locks = new LinkedList<Lock>();
        this.cars = new LinkedList<Car>();
        // Set up cars with associated locks
        for (int i = 0; i < numCars; i++) {
            locks.push(new ReentrantLock(true));
            cars.push(new Car());
        }
    }

    // rentCar takes an `int` and returns a `Car`
    public Car rentCar(int id) {
        this.locks.get(id).lock();
        return cars.get(id);
    }

    // returnCar takes a `Car` and adds up the total
    // mileage of cars in the fleet
    public synchronized int returnCar(Car c, int id) {
        try {
            cars.set(id, c);
            totalMileage = this.getTotalMileage();
            return totalMileage;
        } finally {
            this.locks.get(id).unlock();
        }
    }

    public int getTotalMileage() {
        int tempMileage = 0;
        for (int i = 0; i < cars.size(); i++) {
            tempMileage += cars.get(i).mileage();
        }
        return tempMileage;
    }

}

// *****************************************************************************
public class Class2 {

    public static void main(String args[]) {
        // Set up size of car rental company
        int numCars = 3;
        Lender lender = new Lender(numCars);

        // Start 10 renters interested in various cars
        for (int i = 0; i < 10; i++) {
            // Choose random car between 0 and 2
            int interestedInCar = ThreadLocalRandom.current().nextInt(0, numCars);

            // TODO - Create a renter and start it in a new thread
            Thread t = new Thread(new Renter(lender, "lender " + i, interestedInCar));
            t.start();
        }


    }
}