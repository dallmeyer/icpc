package ptTryouts;

import java.util.Scanner;

public class C {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		boolean sieve[] = sieveToN(32000);
		
		int T = in.nextInt();
		for (int t = 0; t < T; t++)
		{
			int n = in.nextInt();
			String s = "";
			int sums = 0;
			
			for (int i = 1; 2*i <= n; i++)
			{
				if (sieve[i])
				{
					if (sieve[n-i])
					{
						sums++;
						s += ("" + i + "+" + (n-i) + "\n");
					}
				}
			}
			
			System.out.println(n + " has " + sums + " representation(s)");
			System.out.println(s);
		}
	}
	
	public static boolean[] sieveToN(int N) {
		boolean s[] = new boolean[N+1];
		s[0]=false;
		s[1]=false;
		for (int i = 2; i < N+1; i++)
			s[i]=true;
		
		for (int i = 2; i < N+1; i++) {
			if (s[i]) {
				int m=2;
				while(i*m < N+1) {
					s[i*m]=false;
					m++;
				}
			}
		}
		
		return s;
	}

}
