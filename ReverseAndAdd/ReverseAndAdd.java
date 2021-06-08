import java.util.Scanner;


public class ReverseAndAdd {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int N = in.nextInt();
		for (int i = 0; i < N; i++)
		{
			int a = 0;
			long x = in.nextInt();
			
			while (!isPalindrome(x) && a < 1000)
			{
				a++;
				x = revAdd(x);
			}
			
			System.out.println(a + " " + x);
		}
	}
	
	private static boolean isPalindrome(long x)
	{
		String s = x + "";
		int l = s.length();
		int l2 = l/2;
		
		for (int i = 0; i < l2; i++)
		{
			if (s.charAt(i) != s.charAt(l-i-1))
				return false;
		}
		
		return true;
	}

	private static long revAdd(long x)
	{
		StringBuilder b = new StringBuilder(x+"").reverse();
		long y = Long.parseLong(b.toString());
		
		return x+y;
	}
}
