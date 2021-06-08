import java.util.Arrays;
import java.util.Scanner;


public class ColoringSocks {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int S = in.nextInt();
		int C = in.nextInt();
		int K = in.nextInt();
		
		int socks[] = new int[S];
		
		int low = Integer.MAX_VALUE;
		
		for (int i = 0; i < S; i++)
		{
			int x = in.nextInt();
			
			if (x < low)
				low = x;
			
			socks[i] = x;
		}
		
		Arrays.sort(socks);
		
		int n = 0, m = 0;
		for (int i = 0; i < socks.length; i++)
		{
			if (m == C)
			{
				n++;
				m = 0;
				low = socks[i];
			}				
			
			int x = socks[i];
			
			if (x - low <= K)
			{
				m++;	
			}
			else
			{
				n++;
				m = 1;
				low = x;
			}
		}
		
		if (m > 0)
			n++;
		
		System.out.println(n);
		
	}

}
