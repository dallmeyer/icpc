import java.util.Scanner;


public class Dirichlets {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int turn = 1;
		Sieve sieve = new Sieve(1000000);
		
		long a = in.nextLong();
		
		while (a != 0)
		{
			long b = in.nextLong();
			long L = in.nextLong();
			long U = in.nextLong();
			
			int spread = (int) (U - L) + 1;
			
			if (L > 1 && longGCD(a,b) != 1)
			{
				System.out.println("Case " + turn++ + ": 0");				
			}
			else
			{
				boolean[] tnPrimality = new boolean[spread];
				long maxTN = a * U + b;
				
				for (long p : sieve.primes)
				{
					if (p*p > maxTN)
						break;
					
					long gcd = longGCD(a, p);
					long remainder = b % gcd;
					
					if (remainder == 0)
					{
						//solutions to diophantine equation exists
						//a*n + p*y = -b
						
						long[] dioSol = solveDiophantine(a, p, -b);
						long n0 = dioSol[0];
						long d = dioSol[2];
						
						long lowM = ((L-n0)*d) / p;
						long hiM = ((U-n0)*d) / p;
						
						for (long m = lowM ; m <= hiM; m++)
						{
							long n = n0 + m*p/d;
														
							if (n >= L && n <= U)
							{
								int tnIndex = (int) (n - L);
								if (tnPrimality[tnIndex])
									continue;
								
								long tn = a*n + b;
								if (tn != p)
									tnPrimality[tnIndex] = true;
							}
						}
					}
				}
				
				int count = 0;
				for (int i = 0; i < spread; i++)
				{
					long tn = a*(L+i) + b;
					if (tn == 0 || tn == 1)
						continue;
					
					if (!tnPrimality[i])
						count++;
				}
				
				System.out.println("Case " + turn++ + ": " + count);
			}
			
			a = in.nextLong();
		}
	}
	
	/**
	 * This function uses the extended Euclidean algorithm to find
	 * 	the greatest common divisor of two integers, a and b. 
	 * It also finds two integers x and y, such that a*x + b*y = d, 
	 * 	where d is the GCD of a and b.
	 * The output is an array of the form {x, y, d}.
	 */
	private static long[] extEuclid (long a, long b)
	{
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
	 * This function finds a single solution to the diophantine 
	 * 	equation a*x + b*y = c, where a, b, and c are known.
	 * The output is an array of the form {x, y, d}, where d
	 * 	is the GCD of a and b.
	 * The GCD is included in the output so that ALL solutions
	 * 	can be found.
	 * Whenever m is an integer, the following definition will
	 * 	yield a solution (x', y').
	 * x' = x + m * b / d
	 * y' = y + m * a / d
	 */
	private static long[] solveDiophantine(long a, long b, long c)
	{
		long[] e = extEuclid(a, b);
		long k = c / e[2];
		
		//If c is not divisible by the GCD(a,b), no solution exists.
		if (c - k*e[2] != 0)
			return null;
		
		long[] output = {e[0] * k, e[1] * k, e[2]};
		return output;
	}

	private static long longGCD(long a, long b)
	{
		if (a < b)
			return longGCD(b,a);
		
		if (b == 0)
			return 1;
		
		long[] gcd = {a,b};
		long r = gcd[0] % gcd[1];
		
		while (r != 0)
		{
			gcd[0] = gcd[1];
			gcd[1] = r;
			
			r = gcd[0] % gcd[1];
		}
		
		return gcd[1];
	}
}
