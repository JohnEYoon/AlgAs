/**
 * Homework Assignment #4: Percolation
 *
 * - PercolationStats for simulation of percolation
 *
 * @ Student ID :
 * @ Name       :
 **/

import java.util.Random;

public class PercolationStats {

    private int mT;             // number of experiments to run
    private double mMean;       // mean of percolation threshold
    //private double mStddev;     // standard deviation of percolation threshold

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {

        if (N <= 0 || T <= 0)
            throw new java.lang.IllegalArgumentException(
                            "N and T must be greater than 1");
        mT = T;

        Random random = new Random(System.currentTimeMillis());
        
        double odd=0; //probability of the site opened
        int count=0;// count: number of repetition
        int Osite=0;// Osite: number of opened site
        int index;
        int i, j; //row: i, column: j
        Percolation per;
        
        mMean=0;
        for(count=0;count<T;count++)
        {
        	per = new Percolation(N);
	        Osite=0;
        	//until percolates
	        while(!per.percolates()) {
	        	index=random.nextInt(N*N)+1;// index: 1~N*N
	        	i=index/N+1;
	        	j=index%N;
	        	if(j==0) {
	        		i--;
	        		j++;
	        	}//if: index=k*N
	        	if(!per.isOpen(i, j)) {
	        		per.open(i, j);
	        		Osite++;
	        	}
	        }
	        
		System.out.println((Osite/(double)(N*N)));
	        odd+=(double)(Osite/(double)(N*N));
        }
	mMean=odd/(double)T;
	System.out.println("mMean:"+mMean );

    /*********************************
     * PUT YOUR CODE HERE
     *********************************/

    }

    // sample mean of percolation threshold
    public double mean() {
        return mMean;
    }

    // sample standard deviation of percolation threshold
    /*
    public double stddev() {
        return mStddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mMean - 1.96 * mStddev / Math.sqrt(mT);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mMean + 1.96 * mStddev / Math.sqrt(mT);
    }
     */
    public static void main(String[] args)    // test client(described below)
    {
        PercolationStats percStats;
        int N = 0;
        int T = 0;
        long start = System.currentTimeMillis();
        double time;

        // you must get two inputs N and T
        if (args.length > 0) {
            try {
                N = Integer.parseInt(args[0]);  // get N
                T = Integer.parseInt(args[1]);  // get T
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " and " 
                                            + args[1] + " must be integers.");
                return;
            }
        }

        // run experiment
        percStats = new PercolationStats(N, T);

        // print result
        System.out.println("mean                    = " + percStats.mean());
        
        /*
        System.out.println("stddev                  = " + percStats.stddev());
        System.out.println("95% confidence interval = "
                + percStats.confidenceLo() + ", " + percStats.confidenceHi());
        */
        time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.printf("total time = %f sec (N = %d, T = %d)\n", time, N, T);
    }
}


