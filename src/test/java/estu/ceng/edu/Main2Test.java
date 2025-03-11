package estu.ceng.edu;

import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;

public class Main2Test {

    @Test
    public void testMain2() throws CmdLineException, InterruptedException {
        // Create a Main instance
        Main main = new Main();

        // Simulate command-line arguments
        String[] args = {"-numThread", "1500", "-numBins", "20"};

        // Pass the arguments to the Main class using CmdLineParser
        CmdLineParser parser = new CmdLineParser(main);
        parser.parseArgument(args);

        // Run the method
        main.startGaltonBoard();

        // Add assertions to check if the number of threads is correctly set
        assertEquals(1500, main.numThreads); // assuming numThreads is an attribute in Main

        // Get the simulation results from the Galton Board
        List<Integer> results = main.getResults(); // Ensure this method is implemented properly

        // Statistical check to verify if the output follows a normal distribution
        assertTrue("Results do not follow a normal distribution", checkNormalDistribution(results));
    }

    // Check if the list of results follows a normal distribution
    private boolean checkNormalDistribution(List<Integer> results) {
        if (results == null || results.size() < 2) {
            return false; // Not enough data
        }

        // Compute the mean and variance of the data
        double mean = results.stream().mapToInt(Integer::intValue).average().orElse(0);
        double variance = results.stream()
                                  .mapToDouble(i -> Math.pow(i - mean, 2))
                                  .average()
                                  .orElse(0);
        double stdDev = Math.sqrt(variance);

        // Remove outliers beyond 3 standard deviations
        results.removeIf(x -> Math.abs(x - mean) > 3 * stdDev);

        // Kolmogorov-Smirnov test can be used to check for normal distribution
        KolmogorovSmirnovTest ksTest = new KolmogorovSmirnovTest();

        // Create a normal distribution with the computed mean and variance
        Random random = new Random();
        double[] expected = new double[results.size() * 10];  // Generate more expected samples

        for (int i = 0; i < expected.length; i++) {
            expected[i] = random.nextGaussian() * stdDev + mean;
        }

        // Sort both the expected and actual data arrays
        double[] sortedResults = results.stream()
                                         .mapToDouble(Integer::doubleValue)
                                         .sorted()
                                         .toArray();

        double[] sortedExpected = DoubleStream.of(expected)
                                              .sorted()
                                              .toArray();

        // Perform the Kolmogorov-Smirnov test
        double ksStatistic = ksTest.kolmogorovSmirnovTest(sortedExpected, sortedResults);

        // Compute critical value for KS test (D_alpha = 1.36 / sqrt(n))
        double criticalValue = 1.36 / Math.sqrt(results.size());

        return ksStatistic < criticalValue; // If ksStatistic is below critical value, data is normal
    }
}
