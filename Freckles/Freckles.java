import java.util.ArrayList;
import java.util.Scanner;


public class Freckles {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int N = in.nextInt();
		for (int i = 0; i < N; i++)
		{
			int n = in.nextInt();
			ArrayList<State> list = new ArrayList<State>();
			
			for (int j = 0; j < n; j++)
			{
				list.add(new State(in.nextDouble(), in.nextDouble()));
			}
			
			ArrayList<State> con = new ArrayList<State>();
			double d = 0;
			State x = list.remove(0);
			con.add(x);
			
			while (!list.isEmpty())
			{
				double min = Double.MAX_VALUE;
				int iMin = -1;
				
				for (int k = 0; k < con.size(); k++)
				{
					x = con.get(k);
					
					for (int j = 0; j < list.size(); j++)
					{
						double dist = x.distTo(list.get(j));
						if (dist < min)
						{
							min = dist;
							iMin = j;
						}
					}
				}
					
				con.add(list.remove(iMin));
				d += min;
			}
			
			System.out.printf("%.2f\n", d);
			if (i < N-1)
				System.out.println();
		}
	}
	
	private static class State
	{
		public double x,y;
		
		public State(double x, double y)
		{
			this.x=x;
			this.y=y;
		}
		
		public double distTo(State b)
		{
			double xx = this.x-b.x, yy = this.y-b.y;
			double dist = Math.sqrt(xx*xx+yy*yy);
			return dist;
		}
	}

}
