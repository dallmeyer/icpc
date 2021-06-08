import java.util.ArrayList;
import java.util.Scanner;


public class BalloonsInABox {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int count = 1;
		int n = in.nextInt();
		while (n != 0)
		{
			Trip cornerA = new Trip(in.nextInt(), in.nextInt(), in.nextInt());
			Trip cornerB = new Trip(in.nextInt(), in.nextInt(), in.nextInt());
			
			ArrayList<Trip> points = new ArrayList<Trip>();
			for (int i = 0; i < n; i++)
			{
				points.add(new Trip(in.nextInt(), in.nextInt(), in.nextInt()));
			}
			
			
			
			count++;
			n = in.nextInt();
		}
	}
	
	private static class Trip
	{
		int x, y, z;
		
		public Trip(int x, int y, int z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public double dist(Trip o)
		{
			double distSq = (x-o.x)*(x-o.x) + (y-o.y)*(y-o.y) + (z-o.z)*(z-o.z);
			return Math.sqrt(distSq);
		}
	}

}
