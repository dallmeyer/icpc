
import java.util.ArrayList;
import java.util.Scanner;

public class StacksOfFlapjacks {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		while (in.hasNext())
		{
			String stack = in.nextLine();
			Scanner s = new Scanner(stack);
			int n = 0;
			int x[] = new int[30];
			
			x[n] = s.nextInt();
			System.out.print(x[n++]);
			while (s.hasNext())
			{
				x[n] = s.nextInt();
				System.out.print(" " + x[n++]);
			}
			System.out.print("\n");
			
			
			x = flip(x, n, 1, n);
			System.out.println("0");
		}
	}
	
	private static int[] flip(int[] x, int n, int z, int t)
	{
		if (n == 0)
		{
			return x;
		}
		
		int max = 0;
		int maxi = -1;
		
		for (int i = 0; i < n; i++)
		{
			if (x[i] > max)
			{
				max = x[i];
				maxi = i;
			}
		}
		
		if (maxi == n-1)
			return flip(x, n-1, z+1, t);
		
		if (maxi != 0)
		{
			System.out.print((t-maxi) + " ");
			//flip to top
			for (int i = 0; i <= maxi/2; i++)
			{
				int temp = x[i];
				x[i] = x[maxi - i];
				x[maxi - i] = temp;
			}
		}
		
		//flip to 'bottom'
		System.out.print(z + " ");
		for (int i = 0; i < n/2; i++)
		{
			int temp = x[i];
			x[i] = x[n - i - 1];
			x[n - i - 1] = temp;
		}
		
		return flip(x, n-1, z+1, t);
	}

}
