import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class TugOfWar {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int N = in.nextInt();
		for (int i = 0; i < N; i++)
		{
			int n = in.nextInt();
			ArrayList<Integer> x = new ArrayList<Integer>();
			for (int j = 0; j < n; j++)
			{
				x.add(in.nextInt());
			}
			Collections.sort(x);
			Collections.reverse(x);
			
			ArrayList<Integer> a = new ArrayList<Integer>(),
							   b = new ArrayList<Integer>();
			int sumA = 0, sumB = 0;
			
			while (!x.isEmpty())
			{
				if (a.size() < b.size())
				{
					int y = x.remove(x.size()-1);
					sumA += y;
					a.add(y);
				}
				else if (b.size() < a.size())
				{
					int y = x.remove(x.size()-1);
					sumB += y;
					b.add(y);
				}
				else if (sumA > sumB)
				{
					int z = x.remove(0);
					sumB += z;
					b.add(z);
				}
				else
				{
					int z = x.remove(0);
					sumA += z;
					a.add(z);
				}
			}
			
			if (sumA > sumB)
			{
				System.out.println(sumB + " " + sumA);
			}
			else
			{
				System.out.println(sumA + " " + sumA);	
			}
		}
	}

}
