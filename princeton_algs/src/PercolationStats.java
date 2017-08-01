/**
 * Created by nambrot on 6/21/17.
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;
    public PercolationStats(int n, int trials) {
        results = new double[trials];
        int i = 0;
        while (i < trials) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                p.open(row, col);
            }
            double divider = (double) n * (double) n;
            double ratio = p.numberOfOpenSites() / divider;
            results[i] = ratio;
            i += 1;
        }
    }    // perform trials independent experiments on an n-by-n grid
    public double mean() {
        return StdStats.mean(results);
    }                         // sample mean of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }                        // sample standard deviation of percolation threshold
    public double confidenceLo() {
        return mean() - 1.96 * stddev();
    }                  // low  endpoint of 95% confidence interval
    public double confidenceHi()  {
        return mean() + 1.96 * stddev();
    }               // high endpoint of 95% confidence interval

    public static void main(String[] args)  {
        PercolationStats s = new PercolationStats(10, 10);
        System.out.println("Mean: " + s.mean());
        System.out.println("StdDev: " + s.stddev());
        System.out.println("95% Confidence Interval: [" + s.confidenceLo() + ",  " + s.confidenceHi() + "]");
    }       // test client (described below)
}
