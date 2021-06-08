import java.util.Arrays;
import java.util.Scanner;


public class AutomaticTrading {
	
	private static int MAXLG = 30;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		String s = in.next();
		while (!s.equals("*"))
		{
			int n = s.length();
			
			char[] A = s.toCharArray();
			Entry[] M = new Entry[n];
			for (int a = 0; a < n; a++)
			{
				M[a] = new Entry();
			}
			int[][] P = new int[MAXLG][n];
			int maxP = 0;
			
			for (int a = 0; a < n; a++)
			{
				P[0][a] = (int) A[a];
			}
			for (int level = 1, skip = 1; skip < n; level++, skip *= 2)
			{
				maxP++;
				
				for (int a = 0; a < n; a++)
				{
					M[a].nr[0] = P[level-1][a];
					M[a].nr[1] = (a + skip < n) ? P[level-1][a+skip] : -1000;
					M[a].p = a;
				}
				
				Arrays.sort(M);
				
				for (int a = 0; a < n; a++)
				{
					P[level][M[a].p] = 
							(a > 0 && M[a].nr[0] == M[a-1].nr[0] && M[a].nr[1] == M[a-1].nr[1])
							? P[level][M[a-1].p] : a;
				}
			}
			
			int q = in.nextInt();
			qLoop: for (int qi = 0; qi < q; qi++)
			{
				int i = in.nextInt(),
					j = in.nextInt();
				
				if (i == j)
				{
					System.out.println(n - i);
					continue qLoop;
				}
				
				int len = 0;
				for (int k = maxP - 1; k >= 0 && i < n && j < n; k--)
				{
					if (P[k][i] == P[k][j])
					{
						i += 1 << k;
						j += 1 << k;
						len += 1 << k;
					}
				}
				
				System.out.println(len);
			}
			
			s = in.next();
		}
	}
	
	private static class Entry implements Comparable<Entry>
	{
		int[] nr;
		int p;
		
		public Entry()
		{
			this.nr = new int[2];
			this.p = -1;
		}
		
		public String toString()
		{
			return Arrays.toString(nr) + " " + p;
		}

		public int compareTo(Entry o) {
			return this.nr[0] == o.nr[0] ? (this.nr[1] < o.nr[1] ? -1 : 1) : (this.nr[0] < o.nr[0] ? -1 : 1);
		}		
	}
	
	

}
