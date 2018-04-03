/**
 * Homework Assignment #4: Percolation
 *
 *  - Percolation data structure
 *
 * @ Student ID : 20120195
 * @ Name       : Yoon Hyowon
 **/

import java.util.Scanner;
import java.io.File;

public class Percolation {

    private static final boolean SITE_BLOCKED = false;
    private static final boolean SITE_OPEN = true;
    private boolean[] sites;          // sites[i] = state of site i
    private int mN;                   // remember the input N
    private int topIdx;               // idx of the special top
    private int bottomIdx;            // idx of the speical bottom
    private WeightedQuickUnionUF uf;
    /*********************************
     * YOU CAN ADD MORE HERE
     *********************************/

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("N must be >0");
        mN = N;
        sites=new boolean[mN*mN+2];
        for(int i=0;i<mN*mN+2;i++){
            sites[i]=SITE_BLOCKED;
            uf=new WeightedQuickUnionUF(mN*mN+2);
            }
        topIdx=0;
        bottomIdx=mN*mN+1;
        sites[topIdx]=SITE_OPEN;
        sites[bottomIdx]=SITE_OPEN;
    }

    private void checkIndex(int i, int j) {
        if (i < 1 || i > mN)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j < 1 || j > mN)
            throw new IndexOutOfBoundsException("column index j out of bounds");
    }

    // open site(row i, column j) if it is not open already
    public void open(int i, int j) {
        int idx=(i-1)*mN+j;
    	//site index number(1~mN*mN)
    	if(!isOpen(i,j)){
    		sites[idx]=SITE_OPEN;
    		if(i==1) {
    			uf.union(idx, topIdx);
    		}//if: at the top
    		if(i==mN) {
    			uf.union(idx, bottomIdx);
    		}//if: at the bottom
            if(i!=mN){
    		    if(isOpen(i+1,j)) { 
    			    uf.union(idx, i*mN+j);
    		    }
            }//if: (i+1, j) is open & not at the bottom
            if(i!=1){
    		    if(isOpen(i-1,j)) {
    			    uf.union(idx, (i-2)*mN+j);
    		    }
            }//if: (i-1, j) is open & not at the top
    		if(isOpen(i,j+1)&&j!=mN) {
    			uf.union(idx,(i-1)*mN+j+1);
    		}//if: (i, j+1) is open & not at the edge
    		if(isOpen(i,j-1)&&j!=1) {
    			uf.union(idx, (i-1)*mN+j-1);
    		}//if: (i, j-1) is open & not at the edge
    	}
    }
        
    // is site(row i, column j) open?
    public boolean isOpen(int i, int j) {
        int idx=(i-1)*mN+j;
    	if(sites[idx])
    		return true;
    	else
    		return false;   // TODO: modify
    }

    // is site(row i, column j) full?
    public boolean isFull(int i, int j) {
    	int idx=(i-1)*mN+j;
    	if(uf.connected(idx, topIdx))
    		return true;
    	else
    		return false;   // TODO: modify
    }

    // does the system percolate?
    public boolean percolates() {
    	if(uf.connected(topIdx, bottomIdx))
    		return true;
    	else
    		return false;   // TODO: modify
    }

    // test client(optional)
    public static void main(String[] args)
    {
        Scanner in;
        int N = 0;
        long start = System.currentTimeMillis();

        try {
            // get input file from argument
            in = new Scanner(new File(args[0]), "UTF-8");
        } catch (Exception e) {
            System.out.println("invalid or no input file ");
            return;
        }

        N = in.nextInt();         // N-by-N percolation system
        System.out.printf("N = %d\n", N);

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(N);

        while (in.hasNext()) {
            int i = in.nextInt();   // get i for open site (i,j)
            int j = in.nextInt();   // get j for open site (i,j)
            perc.open(i, j);        // open site (i,j)
            System.out.printf("open(%d, %d)\n", i, j);
        }
        if (perc.percolates()) {
            System.out.println("This system percolates");
        } else {
            System.out.println("This system does NOT percolate");
        }

        double time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.println("running time = "+ time + " sec");
    }
}
