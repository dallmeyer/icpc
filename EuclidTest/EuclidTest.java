
public class EuclidTest {

	public static void main(String[] args)
	  {
		/*
		 * the current setup finds a number x such that:
		 *	x = 2 mod 5, x = 3 mod 7, x = 4 mod 9, and x = 5 mod 11
		 * note that the values in mods must be mutually prime
		 */
	    long[] constraints = {5,11}; //put modular contraints here
	    long[] mods = {12,18}; //put moduli here
	    
	    //M is the product of the mods
	    int M = 1;
	    for(int i = 0; i < mods.length; i++)
	      M *= mods[i];
	    
	    long[] multInv = new long[constraints.length];
	    
	    /*
	     * this loop applies the Euclidean algorithm to each pair of M/mods[i] and mods[i]
	     * since it is assumed that the various mods[i] are pairwise coprime,
	     * the end result of applying the Euclidean algorithm will be
	     * gcd(M/mods[i], mods[i]) = 1 = a(M/mods[i]) + b(mods[i]), or that a(M/mods[i]) is
	     * equivalent to 1 mod (mods[i]). This a is then the multiplicative
	     * inverse of (M/mods[i]) mod mods[i], which is what we are looking to multiply
	     * by our constraint constraints[i] as per the Chinese Remainder Theorem
	     * euclidean(M/mods[i], mods[i])[0] returns the coefficient a
	     * in the equation a(M/mods[i]) + b(mods[i]) = 1
	     */
	    for(int i = 0; i < multInv.length; i++)
	      multInv[i] = euclidean(M/mods[i], mods[i])[0];
	    
	    int x = 0;
	    
	    //x = the sum over all given i of (M/mods[i])(constraints[i])(multInv[i])
	    for(int i = 0; i < mods.length; i++)
	      x += (M/mods[i])*constraints[i]*multInv[i];
	    
	    x = leastPosEquiv(x, M);
	    
	    System.out.println("x is equivalent to " + x + " mod " + M);
	    
	    
	    System.out.println(CRT(constraints, mods));
	  }
	
	
	private static long[] extEuclid (long a, long b) {
		  long s0 = 1, s1 = 0, sTemp;
		  long t0 = 0, t1 = 1, tTemp;
		  long r0 = a, r1 = b, rTemp;
		  long q;

		  while (r1 != 0)	{
		    q = r0 / r1;

		    rTemp = r1;
		    r1 = r0 - q*r1;
		    r0 = rTemp;

		    sTemp = s1;
		    s1 = s0 - q*s1;
		    s0 = sTemp;

		    tTemp = t1;
		    t1 = t0 - q*t1;
		    t0 = tTemp;
		  }

		  long[] output = {s0, t0, r0};
		  return output;
		}

		/**
		 * For pairwise coprime integers m_0, m_1, ...
		 * And system of congruences 
		 * x = a_0 mod m_0
		 * x = a_1 mod m_1
		 * ...
		 * CRT finds smallest positive x (mod M) where
		 * M = m_0 * m_1 * ...
		 */
		private static long CRT(long[] a, long[] m)
		{
		    long M = 1;
		    for (int i = 0; i < m.length; i++)
		        M *= m[i];
		    
		    long x = 0;
		    for (int i = 0; i < m.length; i++) 
		        x += (M/m[i])*a[i]*extEuclid(M/m[i], m[i])[0];
		    
		    x = (long) (Math.ceil(-1.0 * x / M) * M + x);
		    
		    return (x == 0) ? M : x;
		}
	
	
	
	
	 /*
	   * performs the Euclidean algorithm on a and b to find a pair of coefficients
	   * (stored in the output array) that correspond to x and y in the equation
	   * ax + by = gcd(a,b)
	   * constraint: a > b
	   */
	  public static long[] euclidean(long l, long mods)
	  {
	    if(mods > l)
	    {
	      //reverse the order of inputs, run through this method, then reverse outputs
	      long[] coeffs = euclidean(mods, l);
	      long[] output = {coeffs[1], coeffs[0]};
	      return output;
	    }

	    long q = l/mods;
	    //a = q*b + r --> r = a - q*b
	    long r = l -q*mods;
	    
	    //when there is no remainder, we have reached the gcd and are done
	    if(r == 0)
	    {
	      long[] output = {0, 1};
	      return output;
	    }
	    
	    //call the next iteration down (b = qr + r_2)
	    long[] next = euclidean(mods, r);
	    
	    long[] output = {next[1], next[0] - q*next[1]};
	    return output;
	  }
	  
	  //finds the least positive integer equivalent to a mod m
	  public static int leastPosEquiv(int a, int m)
	  {
	    //a eqivalent to b mod -m <==> a equivalent to b mod m
	    if(m < 0)
	      return leastPosEquiv(a, -1*m);
	    //if 0 <= a < m, then a is the least positive integer equivalent to a mod m
	    if(a >= 0 && a < m)
	      return a;
	    
	    //for negative a, find the least negative integer equivalent to a mod m
	    //then add m
	    if(a < 0)
	      return -1*leastPosEquiv(-1*a, m) + m;
	    
	    //the only case left is that of a,m > 0 and a >= m
	    
	    //take the remainder according to the Division algorithm
	    int q = a/m;
	    
	    /*
	     * a = qm + r, with 0 <= r < m
	     * r = a - qm is equivalent to a mod m
	     * and is the least such non-negative number (since r < m)
	     */
	    return a - q*m;
	  }
}
