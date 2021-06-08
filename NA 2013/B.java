package ptTryouts;

import java.math.BigInteger;
import java.util.Scanner;

public class B {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		double threeLog = Math.log(3);
		
		int T = in.nextInt();
		for (int t = 0; t < T; t++)
		{
			BigInteger n = in.nextBigInteger();
			if (n.compareTo(BigInteger.ZERO) == 0)
			{
				System.out.println("left pan: ");
				System.out.println("right pan: ");
				System.out.println();
				continue;
			}
			
			int x = 20;
			BigInteger[] threePows = new BigInteger[x];
			
			threePows[0] = BigInteger.ONE;
			for (int i = 1; i < x; i++)
			{
				threePows[i] = threePows[i-1].multiply(BigInteger.valueOf(3));
			}
			
			int ternary[] = new int[x];
			for (int i = x-1; i >= 0; i--)
			{
				BigInteger z = threePows[i].multiply(BigInteger.valueOf(2));
				if (n.compareTo(z) >= 0)
				{
					n = n.subtract(z);
					ternary[i] = 2; 
				}
				else if (n.compareTo(threePows[i]) >= 0)
				{
					n = n.subtract(threePows[i]);
					ternary[i] = 1;
				}
				else
				{
					ternary[i] = 0;
				}
			}
			
			for (int i = 0; i < x; i++)
			{
				if (ternary[i] > 1)
				{
					ternary[i] -= 3;
					ternary[i+1]++;
				}
			}
			
			System.out.print("left pan: ");
			for (int i = x-1; i >= 0; i--)
			{
				if (ternary[i] == -1)
					System.out.print(threePows[i] + " ");
			}
			System.out.print("\nright pan: ");
			for (int i = x-1; i >= 0; i--)
			{
				if (ternary[i] == 1)
				{
					System.out.print(threePows[i] + " ");
				}
			}
			System.out.print("\n\n");
		}
	}

}
