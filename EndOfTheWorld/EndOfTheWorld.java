import java.util.ArrayList;
import java.util.Scanner;


public class EndOfTheWorld {
	static long[] t = new long[64];
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);	
		tHanoi(63);
		
		String s = in.next();
		while (!s.equals("X"))
		{
			ArrayList<Integer> a = new ArrayList<Integer>();
			ArrayList<Integer> b = new ArrayList<Integer>();
			ArrayList<Integer> c = new ArrayList<Integer>();
			
			int n = -1;
			for (int i = 0; i < s.length(); i++)
			{
				switch (s.charAt(i))
				{
				case 'A' :
					a.add(i);
					n = i;
					break;
				case 'B' :
					b.add(i);
					break;
				case 'C' :
					c.add(i);
					n = i;
					break;
				}
			}
			
			if (n == -1)
				System.out.println(0);
			else
				System.out.println(find(n, a, b, c));
			
			s = in.next();
		}
	}
	
	private static long find(int n, ArrayList<Integer> a, ArrayList<Integer> b, ArrayList<Integer> c)
	{
		if (n == 0)
			return t[n];
		
		if (a.contains(n))
		{
			int i = n-1;
			for ( ; i >= 0; i--)
			{
				if (!a.contains(i))
					return t[n] - find(i, a, b, c);
			}
		}
		else if (b.contains(n))
		{
			int i = n-1;
			for ( ; i >= 0; i--)
			{
				if (!b.contains(i))
					return t[n] - find(i, a, b, c);
			}			
		}
		else if (c.contains(n))
		{
			int i = n-1;
			for ( ; i >= 0; i--)
			{
				if (!c.contains(i))
					return t[n] - find(i, a, b, c);
			}			
		}
		
		return t[n];
	}
	
	private static long tHanoi(int i)
	{
		if (i == 0)
		{
			t[i] = 1;
		}
		else
		{
			t[i] = 2*tHanoi(i-1) + 1;
		}
		
		return t[i];
	}

}
