import java.util.Arrays;
import java.util.Scanner;


public class Shuffle {

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
			int[] f = new int[s];
			
			for (int i = 0; i < n+s; i++)
			{
				if (i >= s)
				{
					f[h[i-s]-1]--;			//remove from left side of window
				}
				
				if (i < n)
				{
					int x = in.nextInt();
					h[i] = x;
				
					f[x-1]++;				//add to right side of window
				}
				
				if (b[i % s])				//check if valid only if not already invalid
				{
					boolean valid = true;
					for (int j = 0; j < s; j++)
					{
						if (f[j] > 1)
						{
							valid = false;
							break;
						}
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
