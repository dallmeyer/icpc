import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


public class ShuffleHash {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int cases = in.nextInt();
		for (int c = 0; c < cases; c++)
		{
			int 	s = in.nextInt(),
					n = in.nextInt();
			
			//stores song history
			int[] h = new int[n];
			
			//tracks if shuffle can occur at some index mod s
			boolean[] b = new boolean[s];
			Arrays.fill(b, true);
			
			//tracks frequency of songs in last s songs
			HashMap<Integer,Integer> f = new HashMap<Integer,Integer>();
			
			for (int i = 0; i < n+s; i++)
			{
				if (i >= s)
				{
					int temp = f.remove(h[i-s]);	//remove from left side of window
					if (temp > 1)
					{
						f.put(h[i-s], temp-1);
					}
				}
				
				if (i < n)
				{
					int x = in.nextInt();
					h[i] = x;
				
					if (f.containsKey(x))			//add to right side of window
					{
						f.put(x, f.get(x)+1);
					}
					else
					{
						f.put(x, 1);
					}
				}
				
				if (b[i % s])			//check if valid only if not already invalid
				{
					boolean valid;
					if (i < Math.min(s,n))
					{
						valid = f.size() == i+1;
					}
					else if (i < Math.max(s, n))
					{
						valid = f.size() == Math.min(s,n);
					}
					else
					{
						valid = f.size() == s+n-i-1;
					}
					
					if (!valid)
					{
						b[i % s] = false;
					}
				}
			}
			
			int numPositions = 0;
			for (int i = 0; i < s; i++)
			{
				if (b[i])
					numPositions++;
			}
			
			System.out.println(numPositions);
		}
	}

}
