import java.math.BigInteger;
import java.util.Scanner;


public class Expressions {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		BigInteger lookup[][] = new BigInteger[301][151];
		for (int n = 0; n < 301; n+=2)
		{
			for (int d = 0; d < 151; d++)
			{
				if (d == 0)
				{
					lookup[n][d] = BigInteger.ZERO;
				}
				else if (n == 0 || d == 1)
				{
					lookup[n][d] = BigInteger.ONE;
				}
				else
				{
					BigInteger sum = BigInteger.ZERO;
					for (int i = 0; i < n; i += 2)
					{
						sum = sum.add(lookup[i][d-1].multiply(lookup[n-2-i][d]));
					}
					lookup[n][d] = sum;
				}
			}
		}
		
		while (in.hasNext())
		{
			int n = in.nextInt();
			int d = in.nextInt();
			
			BigInteger ans = lookup[n][d].subtract(lookup[n][d-1]);
			System.out.println(ans);
		}
	}

}
