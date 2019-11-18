import java.util.ArrayList;
import java.util.*;

import java.util.concurrent.*;
import java.util.concurrent.ThreadLocalRandom;

class Worker implements Callable<Double> {

    Double k;

    public Double call() {
        // Calculate the value for this value of k
        Double sum = (Math.pow(-1, k) / ((k * 2) + 1));
        // Send the result back
        return sum;
    }

    public Worker(Double k) {
        this.k = k;
    }

}

class ApproximatePi {

    public static void main(String args[]) {
        // Input and outputs
        int l = 10000;

        // Channel for every chunk
        ArrayList<Future<Double>> tasks = new ArrayList<>(l);

        // Split
        for (Double k = 0.0; k < l; k++) {
            // Creater worker and its future
            Worker worker = new Worker(k);
            FutureTask<Double> future = new FutureTask<>(worker);
            // Spawn the future
            (new Thread(future)).start();
            tasks.add(future);
        }

        Double output = 0.0;

        try {
            // Join
            for (int k = 0; k < l; k++) {
                output = tasks.get(k).get() + output;
            }
            output = output * 4;
            System.out.println("Approximately, Pi = " + output);
        } catch (Exception e) {
        }

    }

}
