

import java.math.BigInteger;
import java.util.Scanner;

public class the3nplus1helper {

	public static void main(String[] args) {
		int map[] = new int[1000001];
		int maxMap[] = new int[1000001];

		for (int a = 0; a < 1000001; a++)
		{
			map[a] = 0;
			maxMap[a] = 0;
		}
		
		for (int a = 1; a <= 1000000; a++)
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
			if (len > maxMap[a-1])
			{
				maxMap[a] = len;
			}
			else
			{
				maxMap[a] = maxMap[a-1];
			}
		}
		
		Scanner in = new Scanner(System.in);
		
		while (in.hasNext()){
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
			
			if (i == 1)
			{
				max = maxMap[j];
			}
			else
			{
				for (int a = i; a <= j; a++)
				{		
					int temp = map[a];
					if (temp > max)
					{
						max = temp;
					}
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
