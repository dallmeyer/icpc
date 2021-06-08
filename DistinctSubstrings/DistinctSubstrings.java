import static java.lang.Math.max;

import java.util.Arrays;
import java.util.Scanner;


public class DistinctSubstrings {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int n = in.nextInt();
		in.nextLine();
		for (int i = 0; i < n; i++)
		{
			String s = in.nextLine();
			SuffixArray sufArr = new SuffixArray(s);
			int[] sa = sufArr.getSuffixArray();
			int[] lcp = sufArr.getLCP();

			int len = s.length();
			int ds = (len - sa[0]);
			for (int j = 1; j < len; j++)
			{
				ds += (len - sa[j] - lcp[j]);
			}
			
			System.out.println(ds);
		}
	}
	
	/*
	 * Suffix Array ADT
	 */
	static class SuffixArray
	{
	    char [] A;      // the string
	    int [][] P;     // the DP table resulting from MM's algorithm
	    int [] rank;    // the inverted suffix array, or rank array
	    int [] sa;      // the suffix array (populated on demand)
	    int [] lcp;     // the LCP table (populated on demand)

	    SuffixArray(String s) { this(s.toCharArray()); }

	    SuffixArray(char [] A) {
	        this.A = A;
	        this.P = computeSuffixArray(A);
	        this.rank = P[P.length - 1];
	    }

	    /* Return inverted suffix array, aka rank table */
	    int [] getRank() {
	        return rank;
	    }

	    /* Return suffix array */
	    int [] getSuffixArray() {
	        if (sa == null) sa = invertArray(rank);
	        return sa;
	    }

	    /* Compute and return the LCP array */
	    int [] getLCP() {
	        if (lcp == null) lcp = computeLCP(getSuffixArray(), getRank(), A);
	        return lcp;
	    }

	    /* Computes the longest common prefix of suffix i and j */
	    int lcp(int i, int j) {
	        int n = A.length;
	        if (i == j)
	            return n - i;

	        int len = 0;
	        for (int k = P.length - 1; k >= 0 && i < n && j < n; k--) {
	            if (P[k][i] == P[k][j]) {
	                i += 1 << k;
	                j += 1 << k;
	                len += 1 << k;
	            }
	        }

	        return len;
	    }

	    /* End of public API */
	    /*---------------------------------------------------------------*/
	    private static class Entry implements Comparable<Entry> {
	        int nr0, nr1, p;

	        Entry() { this.p = -1; }

	        @Override
	        public int compareTo(Entry o) {
	            int c = Integer.compare(this.nr0, o.nr0);
	            if (c != 0)
	                return c;
	            return Integer.compare(this.nr1, o.nr1);
	        }
	    }

	    /* Computes inverted suffix array for a string
	     * Returns array P, where P[i][j] is the rank 
	     * of the suffix of length 2^i starting the jth character.
	     *
	     * The time complexity for this is O(n logn logn) because
	     * it uses the built-in sort.  It uses a DP scheme that
	     * retains the DP table for levels 0 .. log n.
	     * It is possible to improve this so that only the last
	     * two level are maintained, but then it is no longer
	     * possible to answer LCP(i, j) queries for arbitrary
	     * i, j. 
	     *
	     * I believe this is Manbers and Myers algorithm. 
	     * Implementation based on:
	     * http://www.stanford.edu/class/cs97si/suffix-array.pdf
	     */
	    private int[][] computeSuffixArray(char []A) {
	        int n = A.length;
	        int MAXLG = 2 + 31 - Integer.numberOfLeadingZeros(n);

	        Entry[] M = new Entry[n];
	        for (int i = 0; i < n; i++)
	            M[i] = new Entry();
	            
	        // MAXLG is the log of text length
	        int[][] P = new int[MAXLG][n];
	        for (int i = 0; i < n; i++)
	            P[0][i] = (int) A[i];

	        int level = 1;
	        for (int skip = 1; skip < n; level++, skip *= 2) {
	            for (int i = 0; i < n; i++) {
	                M[i].nr0 = P[level - 1][i];
	                M[i].nr1 = (i + skip < n) 
	                    ? P[level - 1][i + skip] : -1000;
	                M[i].p = i;
	            }
	            Arrays.sort(M);

	            for (int i = 0; i < n; i++) {
	                P[level][M[i].p] = (i > 0 
	                    && M[i].nr0 == M[i - 1].nr0 
	                    && M[i].nr1 == M[i - 1].nr1) 
	                    ? P[level][M[i - 1].p] : i;
	            }
	        }

	        return Arrays.copyOf(P, level);
	    }

	    /* Kasai's algorithm. */
	    private int [] computeLCP(int [] sa, int [] rank, char [] A) {
	        int n = sa.length;
	        int [] lcp = new int[n];
	        int h = 0;
	        for (int i = 0; i < n; i++) {
	            int k = rank[i];
	            if (k > 0) {
	                int j = sa[k - 1];
	                while (max(i + h, j + h) < n && A[i + h] == A[j + h])
	                    h++;

	                lcp[k] = h;
	                if (h > 0)
	                    h--;
	            } else {
	                lcp[k] = -1;
	            }
	        }
	        return lcp;
	    }
	}

	static int [] invertArray(int [] a) {
	    int [] inv = new int[a.length];
	    for (int i = 0; i < a.length; i++)
	        inv[a[i]] = i;
	    return inv;
	}

}
