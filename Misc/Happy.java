import java.util.Scanner;


public class Happy {

	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int casenum = 1;
		while (true)
		{
			int L = in.nextInt();
			int P = in.nextInt();
			int V = in.nextInt();
			if (L == 0 && P == 0 && V == 0)
				break;
			
			System.out.printf("Case %d: %d%n", casenum, solve(L, P, V));
			casenum++;
		}
	}
	
	public static int solve(int L, int P, int V)
	{
		int ans = 0;
		while (V > 0)
		{
			int val = Math.min(V, L);
			ans += val;
			V -= val;
			V -= Math.max((P - L), 0);
			
		}
		
		return ans;
	}
}
