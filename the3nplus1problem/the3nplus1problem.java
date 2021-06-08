

import java.math.BigInteger;
import java.util.Scanner;

public class the3nplus1problem 
{

	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		int[] map = new int[1000001];
		for (int i = 0 ; i < 1000001; i++)
		{
			map[i] = 0;
		}
		
		while (in.hasNext())
		{
			int i = in.nextInt();
			int j = in.nextInt();
			int max = 0;
			boolean flip = false;
			
			if (i > j)
			{
				int temp = i;
				i = j;
				j = temp;
				
				flip = true;
			}
			
			for (int a = i; a <= j; a++)
			{

				
				int len = 1;
				BigInteger x = BigInteger.valueOf(a);
				while (x.compareTo(BigInteger.ONE) != 0)
				{					
					if (x.intValue() > 0 && x.intValue() < 1000000 && map[x.intValue()] != 0)
					{
						len += map[x.intValue()] - 1;
						break;
					}
					
					len++;
					if (x.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0)
					{
						x = x.divide(BigInteger.valueOf(2));
					}
					else
					{
						x = x.multiply(BigInteger.valueOf(3)).add(BigInteger.ONE);
					}
				}
				map[a] = len;
						
				if (len > max)
				{
					max = len;
				}
			}
			
			if (flip)
			{
				System.out.println(j + " " + i + " " + max);
			}
			else
			{
				System.out.println(i + " " + j + " " + max);
			}
		}
	}
}
