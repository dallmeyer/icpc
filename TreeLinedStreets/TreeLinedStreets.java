import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class TreeLinedStreets {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int caseNum = 1;
		int n = in.nextInt();
		while (n > 0)
		{
			if (caseNum > 1)
			{
				System.out.println();
			}
			ArrayList<Street> streets = new ArrayList<Street>();
			for (int i = 0; i < n; i++)
			{
				int 	x1 = in.nextInt(),
						y1 = in.nextInt(),
						x2 = in.nextInt(),
						y2 = in.nextInt();
				
				Street s = new Street(x1, y1, x2, y2);
				for (Street o : streets)
				{
					s.markIntersections(o);
				}
				streets.add(s);
			}
			
			int numTrees = 0;
			for (int i = 0; i < n; i++)
			{
				Street s = streets.get(i);
				numTrees += s.numTrees();
			}
			
			System.out.println("Map " + caseNum++);
			System.out.print("Trees = " + numTrees);
			
			n = in.nextInt();
		}
	}
	
	private static class Street extends Line2D.Double
	{
		ArrayList<Point> intersections;
		
		
		public Street(int x1, int y1, int x2, int y2)
		{
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			
			intersections = new ArrayList<Point>();
			intersections.add(new Point(x1,y1));
			intersections.add(new Point(x2,y2));
		}
		
		public void markIntersections(Street o)
		{
			if (this.intersectsLine(o))
			{
				Point p = findIntersection(this, o);
				if (p != null)
				{
					intersections.add(p);
					o.intersections.add(p);
				}
			}
		}
		
		public int numTrees()
		{
			Collections.sort(intersections);
			int n = 0;
			
			Point p1 = intersections.get(0);
			for (int i = 1; i < intersections.size(); i++)
			{
				Point p2 = intersections.get(i);
				long d = dist(p1, p2);
				
				if (i == 1 && i == intersections.size()-1)
				{
					d += 50;
				}
				else if (i == 1 || i == intersections.size()-1)
				{
					d += 25;
				}
				
				if (d >= 0)
				{
					int k = (int) (d / 50);
					while (d - k*50 > 0.000001)
						k++;
					while (d - k*50 < 0.000001)
						k--;
					
					n += k;
				}
				p1 = p2;
			}
			
			return n;
		}
	}
	
	private static long dist(Point p1, Point p2)
	{
		long dx = (long) (p1.x - p2.x);
		long dy = (long) (p1.y - p2.y);
		return (long) Math.sqrt(dx*dx + dy*dy);
	}
	
	private static class Point extends Point2D.Double implements Comparable<Point>
	{
		public Point (double x, double y)
		{
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Point o) {
			double dx = this.x - o.x;
			double dy = this.y - o.y;
			if (dx < 0)
			{
				return -1;
			}
			else if (dx > 0)
			{
				return 1;
			}
			else if (dy < 0)
			{
				return -1;
			}
			else if (dy > 0)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
	}
	
	/* Compute point of intersection of infinite line given by (p1, p2)
	 * with (p3, p4)
	 */
	static Point findIntersection(Street s, Street o) 
	{
		if (s.x1 == s.x2)
		{
			if (o.x1 == o.x2)
			{
				return null;
			}
			else
			{
				double a = (o.y2-o.y1) / (o.x2-o.x1);
				double b = o.y1 - a*o.x1;
				double y = a*s.x1 + b;
				
				return new Point(s.x1, y);
			}
		}
		else
		{
			if (o.x1 == o.x2)
			{
				double a = (s.y2-s.y1) / (s.x2-s.x1);
				double b = s.y1 - a*s.x1;
				double y = a*o.x1 + b;
				
				return new Point(o.x1, y);
			}
			else
			{
				double as = (s.y2-s.y1) / (s.x2-s.x1);
				double bs = s.y1 - as*s.x1;
				double ao = (o.y2-o.y1) / (o.x2-o.x1);
				double bo = o.y1 - ao*o.x1;
				
				if (as == ao)
				{
					return null;
				}
				else
				{
					double x = - (bs-bo) / (as-ao);
					double y = as*x + bs;
					return new Point(x, y);
				}
			}
		}
	}


}
