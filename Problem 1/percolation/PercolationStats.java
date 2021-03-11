import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private int trialsx;
    private double[] noofopen; 
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        else {
            trialsx =  trials;
            noofopen = new double[trials];
            for (int i = 0; i < trials; i++) {
                Percolation test = new Percolation(n);
                while (!test.percolates()) {
                    test.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
                }
                noofopen[i] = test.numberOfOpenSites() / (double) (n*n);
            }
        }
    }
    
    public double mean() {
        return StdStats.mean(noofopen);
    }
    
    public double stddev() {
        return StdStats.stddev(noofopen);
    }
    
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trialsx));
    }
    
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trialsx));
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(n, trials);
        String mean = String.valueOf(test.mean());
        String stddev = String.valueOf(test.stddev());
        String cLo = String.valueOf(test.confidenceLo());
        String cHi = String.valueOf(test.confidenceHi());
            
            
        StdOut.println("mean                    = " + mean);
        StdOut.println("stddev                  = " + stddev);
        StdOut.println("95% confidence interval = [" + cLo + ", " + cHi + "]");
    }
    
}