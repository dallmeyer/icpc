import java.util.Scanner;


public class CodeJam {
	
	
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		
		String welc = " welcome to code jam";
		
		int N = Integer.parseInt(in.nextLine());
		for (int i = 0; i < N; i++)
		{
			String input = " " + in.nextLine();
			int ans = solve(input.toCharArray(), welc.toCharArray());
			System.out.printf("Case #%d: %04d%n", (i +1), ans);
		}
	}
	
	public static int solve(char[] input, char[] jam)
	{
		
		
		// Easy catch
		if(input.length < jam.length)
		{
			return 0;
		}
		
		
		int ROW = jam.length;
		int COL = input.length;
		int[][] dp = new int[ROW][COL];
		
		
		for (int j = 1; j < COL; j++)
		{
			if (input[j] == jam[1])
			{
				dp[1][j] = dp[1][j-1] + 1;
			}
			else
			{
				dp[1][j] = dp[1][j-1];
			}
		}
		
		for (int i = 2; i < ROW; i++)
		{
			for (int j = 1; j < COL; j++)
			{
				if (input[j] == jam[i])
				{
					dp[i][j] = (dp[i-1][j-1] + dp[i][j-1]) % 10000;
				}
				else
				{
					dp[i][j] = dp[i][j-1] % 10000;
				}
			}
		}
		
//		for (int i = 0; i < ROW; i++)
//		{
//			for (int j = 0; j < COL; j++)
//			{
//				System.out.print(dp[i][j] + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//		System.out.println();
		
		return dp[ROW-1][COL-1] % 10000;
	}


}
