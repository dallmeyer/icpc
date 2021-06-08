public class RandomGraph {
	
	public static void main(String[] args)
	{
		int n = 3, p = 620;
		
		probability(n, p);
	}
	
	public static double probability(int n, int p)
	{
		double chooseN_4 = choose(n, 4);
		double choose6_3 = choose(6, 3);
		return chooseN_4 * 36 * p / (choose6_3 * 1000);
		
	}
	
	private static double choose(int n, int k)
	{
		double ans = 1;
		for (int i = 0; i < k; i++)
		{
			ans *= (n-i);
			ans /= (i+1);
		}
		
		return ans;
	}
	
}