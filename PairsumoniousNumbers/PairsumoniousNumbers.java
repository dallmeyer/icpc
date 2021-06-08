import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class PairsumoniousNumbers {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		while (in.hasNext())
		{
			int n = in.nextInt();
			ArrayList<Integer> x = new ArrayList<Integer>();
			for (int i = 0; i < n; i++)
			{
				x.add(0);
			}
			int m = (n*(n-1))/2;
			ArrayList<Integer> y = new ArrayList<Integer>();
			
			for (int i = 0; i < m; i++)
			{
				y.add(in.nextInt());
			}
			
			Collections.sort(y);
			
			boolean solExists = false;
			if (n == 2)
			{
				solExists = true;
				x.add(0);
				x.add(y.get(0));
			}
			else if (n == 3)
			{
				Integer ab = y.remove(0);
				Integer ac = y.remove(0);
				Integer bc = y.get(0);
				if ((ab+ac-bc) % 2 == 0)
				{
					Integer x0 = (ab+ac-bc)/2;
					x.set(0, x0);
					x.set(1,ab-x0);
					x.set(2,ac-x0);
				
					solExists = true;
				}
			}
			else
			{
				Integer ab = y.remove(0);
				Integer ac = y.remove(0);
				Integer bc = y.get(0);
				
				Integer x0 = (ab+ac-bc)/2;
				x.set(0, x0);
				x.set(1,ab-x0);
				x.set(2,ac-x0);
				
				jLoop: for (int j = 0; j < n-2; j++)
				{
					ArrayList<Integer> yy = (ArrayList<Integer>) y.clone();
					bc = yy.remove(j);	//try j as x1+x2
					
					if ((ab+ac-bc) % 2 == 1)
						continue jLoop;
						
					x0 = (ab+ac-bc)/2;
					x.set(0, x0);
					x.set(1,ab-x0);
					x.set(2,ac-x0);
										
					for (int i = 3; i < n; i++)
					{
						bc = yy.remove(0);
						Integer xi = bc - x0;
						x.set(i, xi);
						
						for (int k = 1; k < i; k++)
						{
							Integer tempX = x.get(k)+xi;
							if (!yy.contains(tempX))
							{
								continue jLoop;
							}
							else
							{
								yy.remove(yy.indexOf(tempX));
							}
						}
						
						if (i == n-1)
						{
							solExists = true;
							break jLoop;
						}
					}
				}
			}
			
			if (solExists)
			{
				for (int i = 0; i < n; i++)
				{
					System.out.print(x.get(i));
					if (i < n-1)
					{
						System.out.print(" ");
					}
				}
			}
			else
			{
				System.out.print("Impossible");
			}
			System.out.println();
		}
	}

}
