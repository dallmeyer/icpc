import java.math.BigInteger;
import java.util.Scanner;


public class SelfDescribingSequence {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		BigInteger f[] = new BigInteger[10000000];
		f[0] = BigInteger.valueOf(0);
		f[1] = BigInteger.valueOf(1);
		f[2] = BigInteger.valueOf(3);
		f[3] = BigInteger.valueOf(5);
		
		BigInteger i = BigInteger.valueOf(6);
		BigInteger k = BigInteger.valueOf(4);
		BigInteger pk = BigInteger.valueOf(0);
		
		BigInteger max = BigInteger.valueOf(2147400000).multiply(BigInteger.valueOf(2));
		
		while (i.compareTo(max) < 0)
		{
			BigInteger fk = findRange(k, f);
			i = i.add(fk);
			
			f[k.intValue()] = i.subtract(BigInteger.ONE);
			k = k.add(BigInteger.valueOf(1));
		}
		
		BigInteger sum = BigInteger.ZERO;//
		//int n = in.nextInt();
		//while (n > 0)
		for (int j = 1; j  < 1000000; j++)//
		{
			sum = sum.add(findRange(BigInteger.valueOf(j*j*j), f));//
			//System.out.println(findRange(n, f));
			//n = in.nextInt();
		}
		System.out.println(sum);//
	}

	private static BigInteger findRange(BigInteger x, BigInteger f[])
	{
		BigInteger i = BigInteger.ZERO;
		BigInteger iMax = f[i.intValue()];
		i = i.add(BigInteger.ONE);
		while (iMax.compareTo(x) < 0)
		{
			iMax = f[i.intValue()];
			i = i.add(BigInteger.ONE);
		}
		
		return i.subtract(BigInteger.ONE);
	}
	
}
