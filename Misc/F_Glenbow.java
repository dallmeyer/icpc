import java.util.Scanner;


public class F_Glenbow {

	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		long[][] pascals = new long[4000][4000];
		
		pascals[0][0] = 1;
		
		for (int i = 1; i < 4000; i++)
		{
			pascals[i][0] = 1;
			for (int j = 1; j < 4000; j++)
			{
				pascals[i][j] = pascals[i-1][j-1] + pascals[i-1][j];
			}
		}
		
		int tc = 0;
		while (true)
		{
			tc++;
			int L = in.nextInt();
			if (L == 0)
				break;
			
			long ans = 0;
			
			if (L == 4)
			{
				ans = 1;
			}
			else if (L > 3 && L % 2 == 0)
			{
				int numOs = (L - 4) / 2,
					numRs = L - numOs;
				ans = pascals[numRs - 1][numOs] + 2*pascals[numRs - 1][numOs - 1];
			}
						
			System.out.println("Case " + tc + ": " + ans);
		}
	}	
}
