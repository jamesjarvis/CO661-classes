import java.util.ArrayList;
import java.util.*;

import java.util.concurrent.*;
import java.util.concurrent.ThreadLocalRandom;

class Worker implements Callable<Double> {

    List<Double> kChunk;

    public Double call() {

        Double sum = 0.0;

        for (Double k : kChunk) {
            // Calculate the value for this value of k
            sum = sum + (Math.pow(-1, k) / ((k * 2) + 1));
        }

        // Send the result back
        return sum;
    }

    public Worker(List<Double> kChunk) {
        this.kChunk = kChunk;
    }

}

class ApproximatePi {

    public static void main(String args[]) {
        // Input and outputs
        int l = 10000000;
        ArrayList<Double> inputs = new ArrayList<Double>(l);
        // Initialise list of inputs
        for (double i = 0.0; i < l; i++) {
            inputs.add(i);
        }

        int chunkSize = 1000;
        // Channel for every chunk
        ArrayList<Future<Double>> tasks = new ArrayList<>(l / chunkSize);

        // Split
        for (int k = 0; k < l / chunkSize; k++) {
            // Creater worker and its future
            Worker worker = new Worker(inputs.subList(k * chunkSize, (k + 1) * chunkSize));
            FutureTask<Double> future = new FutureTask<>(worker);
            // Spawn the future
            (new Thread(future)).start();
            tasks.add(future);
        }

        Double output = 0.0;

        try {
            // Join
            for (int k = 0; k < l / chunkSize; k++) {
                output = tasks.get(k).get() + output;
            }
            output = output * 4;
            System.out.println("Approximately, Pi = " + output);
        } catch (Exception e) {
        }

    }

}
