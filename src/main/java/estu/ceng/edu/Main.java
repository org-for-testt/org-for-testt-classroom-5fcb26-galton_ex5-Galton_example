package estu.ceng.edu;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    @Option(name = "-numThread", required = true)
    public int numThreads = 1000;  // Number of threads (balls)

    @Option(name = "-numBins", required = true)
    public int numBins = 20;  // Number of bins (columns in the Galton board)

    private List<Integer> results = new ArrayList<>();  // List to store bin results

    // Start the Galton Board simulation
    public void startGaltonBoard() throws InterruptedException {
        GaltonBoard.create(numBins);  // Initialize GaltonBoard with the specified number of bins

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        
        // Simulate the balls being dropped into the Galton Board
        for (int i = 0; i < numThreads; i++) {
            executorService.execute(new Ball());  // Assuming Ball is a class that simulates the ball's movement
        }

        executorService.shutdown();
        executorService.awaitTermination(2000, TimeUnit.MILLISECONDS);  // Wait for all threads to finish

        // Collect results (bin values)
        for (int i = 0; i < numBins; i++) {
            int value = GaltonBoard.getInstance().getBinValue(i);
            results.add(value);  // Store bin values in the results list
            System.out.printf("%-3d\t%d\n", i, value);  // Print bin values
        }

        // Display additional information
        System.out.println("Number of requested threads: " + numThreads);
        int sum = results.stream().mapToInt(Integer::intValue).sum();  // Calculate sum of bin values
        System.out.println("Sum of Bin values: " + sum);
        if (numThreads == sum) {
            System.out.println("Nice work! Both of them are equal");
        } else {
            System.out.println("Fail! Both of them are not equal.");
        }
    }

    // Get the results of the Galton Board simulation (bin values)
    public List<Integer> getResults() {
        return results;  // Return the list of bin values
    }

    // Main method to start the program
    public static void main(String[] args) throws InterruptedException, CmdLineException {
        Main perform = new Main();
        CmdLineParser parser = new CmdLineParser(perform);
        parser.parseArgument(args);
        perform.startGaltonBoard();  // Start the Galton Board simulation
    }
}
