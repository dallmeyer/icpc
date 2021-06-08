package ptTryouts;

import java.util.HashMap;
import java.util.Scanner;

public class H {

	static int[] fourthMaps = {16,8,24,9,8,0,-8,7,24,16,68,8,9,1,8,4,8,0,16,1,0,-8,-16,-1,-8,-16,-60,0,7,-1,0,4,24,16,32,17,16,8,0,15,68,60,256,16,8,0,16,1,9,1,17,2,1,-7,-15,0,8,0,16,1,4,-4,0,0};
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		HashMap<Integer, Trip> map = new HashMap<Integer, Trip>();
		int q = 0;
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				for (int k = 0; k < 4; k++)
				{
					if (!map.containsKey(fourthMaps[q]))
					{
						map.put(fourthMaps[q], new Trip(i,j,k));
					}
					q++;
				}
			}
		}
		
		int T = in.nextInt();
		for (int t = 0; t < T; t++)
		{
			int n = in.nextInt();
			
			if (map.containsKey(n))
			{
				Trip f = map.get(n);
				System.out.println("4 " + lookup(f.i)+ " 4 " + lookup(f.j) + " 4 " + lookup(f.k)+ " 4 = " + n);
			}
			else
			{
				System.out.println("no solution");
			}
		}
	}
	
	private static class Trip
	{
		public int i,j,k;
		
		public Trip(int i, int j, int k)
		{
			this.i = i;
			this.j = j;
			this.k = k;
		}
	}
	
	private static char lookup(int i)
	{
		switch(i)
		{
		case 0: return '+'; 
		case 1: return '-';
		case 2: return '*';
		case 3: return '/';
		}
		
		return ' ';
	}

}
