import java.math.BigInteger;
import java.util.Scanner;


public class DistinctSubsequeces {
	
	
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		
		int N = in.nextInt();
		for (int i = 0; i < N; i++)
		{
			String X = in.next(), Z = in.next();
			BigInteger ans = solve(X.toCharArray(), Z.toCharArray());
			System.out.println(ans);
		}
	}
	
	public static BigInteger solve(char[] input, char[] jam)
	{		
		// Easy catch
		if(input.length < jam.length)
		{
			return BigInteger.ZERO;
		}
		
		
		int ROW = jam.length;
		int COL = input.length;
		BigInteger[][] dp = new BigInteger[ROW+1][COL+1];
		
		for (int i = 0; i <= ROW; i++)
		{
			for (int j = 0; j <= COL; j++)
			{
				dp[i][j] = BigInteger.ZERO;
			}
		}
		
		for (int j = 0; j < COL; j++)
		{
			if (input[j] == jam[ROW-1])
				dp[ROW-1][j] = BigInteger.ONE;
		}
		
		for (int j = COL-1; j >= 0; j--)
		{
			for (int i = ROW-1; i >= 0; i--)
			{
				dp[i][j] =  dp[i][j].add(dp[i][j+1]);
				
				if (input[j] == jam[i])
					dp[i][j] = dp[i][j].add(dp[i+1][j+1]);
			}
		}
		
		return dp[0][0];
	}



}
