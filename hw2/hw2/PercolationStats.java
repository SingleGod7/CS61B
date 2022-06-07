package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int T;
    private double[] simulationResult;
    private double mean;
    private double stddev;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.T = T;
        this.simulationResult = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation simulation = pf.make(N);
            while (!simulation.percolates()) {
                int col = StdRandom.uniform(N);
                int row = StdRandom.uniform(N);
                if (simulation.isOpen(row, col)) {
                    continue;
                }
                simulation.open(row, col);
            }
            simulationResult[i] = (double) simulation.numberOfOpenSites() / (double) (N * N);
        }

        this.mean = this.mean = StdStats.mean(simulationResult);
        this.stddev = this.stddev = StdStats.stddev(simulationResult);
    }

    public double mean() {
        return this.mean;
    }

    public double stddev() {
        return this.stddev;
    }

    public double confidenceLow() {
        return this.mean - 1.96 * this.stddev / Math.sqrt(T);
    }

    public double confidenceHigh() {
        return this.mean + 1.96 * this.stddev / Math.sqrt(T);
    }

    public static void main(String[] args) {
        PercolationFactory b = new PercolationFactory();
        PercolationStats a = new PercolationStats(10, 30, b);
    }
}
