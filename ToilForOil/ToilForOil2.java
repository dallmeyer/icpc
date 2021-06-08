import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class ToilForOil2 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int caveNum = 1;
		int n = in.nextInt();
		while (n != 0)
		{
			ArrayList<Point> points = new ArrayList<Point>();	//all points, in order CCW
			LinkedList<Point> q =  new LinkedList<Point>();	//queue of points sorted by y, then x
			ArrayList<Line2D.Double> edges = new ArrayList<Line2D.Double>();	//all edges, in order CCW
			HashSet<Point> holes = new HashSet<Point>();
			
			double 	minX = Double.MAX_VALUE,
					maxX = Double.MIN_VALUE;
			for (int i = 0; i < n; i++)
			{
				double x = in.nextInt();
				double y = in.nextInt();
				boolean h = in.nextInt() == 1;
				Point p = new Point(x, y);
				
				if (x > maxX)
					maxX = x;
				if (x < minX)
					minX = x;
				
				points.add(p);
				q.add(points.get(i));
				
				if (h)
					holes.add(p);
				
				if (i > 0)
					edges.add(new Line2D.Double(points.get(i-1), points.get(i)));
			}
			edges.add(new Line2D.Double(points.get(n-1),points.get(0)));	//add last edge
			
			Collections.sort(q);
			
			ArrayList<Trapezoid> traps = new ArrayList<Trapezoid>();
			DisjointSets disjSets = new DisjointSets(n*n);
			
			Line2D.Double oldSweep = null;
			
			while (!q.isEmpty())
			{
				Point p = q.pop();
				if (oldSweep != null && p.y == oldSweep.y1)
					continue;
				
				Line2D.Double sweep = new Line2D.Double(minX, p.y, maxX, p.y);
				
				if (oldSweep != null)
				{
					ArrayList<EdgePointTuple> intersects = new ArrayList<EdgePointTuple>();
					for (Line2D.Double e: edges)
					{
						if (sweep.intersectsLine(e))
						{
							if (e.y1 == e.y2)
							{
								intersects.add(new EdgePointTuple(e, new Point((e.getX1()+e.getX2())/2, e.getY1())));
							}
							else
							{
								Point i = getIntersection(sweep, e);
								intersects.add(new EdgePointTuple(e, i));
							}
						}
					}
					
					Collections.sort(intersects);
					
					for (int i = 1; i < intersects.size(); i++)
					{
						EdgePointTuple a = intersects.get(i-1);
						EdgePointTuple b = intersects.get(i);
						
						if (b.line.y1 == b.line.y2)
						{
							while (b.line.y1 == b.line.y2)
							{
								if (holes.contains(b.line.getP1()) || holes.contains(b.line.getP2()))
								{
									for (int j = i+1; j < intersects.size(); j++)
									{
										EdgePointTuple c = intersects.get(j);
										
										if (c.line.y1 > p.y || c.line.y2 > p.y)
										{
											holes.add(getIntersection(sweep, c.line));
											if (c.line.y1 < p.y || c.line.y2 < p.y)
											{
												break;
											}
										}
									}
								}
								
								intersects.remove(b);
								b = intersects.get(i);
							}
							
							if (a.line.y1 >= p.y && a.line.y2 >= p.y)
							{
								intersects.remove(a);
								i--;
								
								if (b.line.y1 >= p.y && b.line.y2 >= p.y)
								{
									intersects.remove(b);
								}
							}
							else if (b.line.y1 >= p.y && b.line.y2 >= p.y)
							{
									intersects.remove(b);
							}
						}
						else if (a.p.equals(b.p))
						{
							if (a.line.y1 >= p.y && a.line.y2 >= p.y)
							{
								intersects.remove(a);
								i--;
								
								if (b.line.y1 >= p.y && b.line.y2 >= p.y)
								{
									intersects.remove(b);
								}
							}
							else if (b.line.y1 >= p.y && b.line.y2 >= p.y)
							{
									intersects.remove(b);
							}
						}
					}
					
					int numTraps = intersects.size();
					ArrayList<Trapezoid> tempTraps = new ArrayList<Trapezoid>();
					
					for (int j = 0; j < numTraps; j+=2)
					{
						EdgePointTuple 	a = intersects.get(j),
										b = intersects.get(j+1);
						
						if (oldSweep.intersectsLine(a.line))								
						{
							Point ll = getIntersection(oldSweep, a.line);
							Point lr = getIntersection(oldSweep, b.line);
					
							Trapezoid t = new Trapezoid(ll, lr, a.p, b.p);
		
							if (holes.contains(ll) || holes.contains(lr))
							{
								t.isFilled = false;
							}
							if (holes.contains(a.p) || holes.contains(b.p))
							{
								holes.add(a.p);
								holes.add(b.p);
							}
							
							tempTraps.add(t);
						}
					}
					
					int oldSize = traps.size();
					traps.addAll(tempTraps);
					
					for (int j = 0; j < tempTraps.size(); j++)
					{
						Trapezoid t1 = tempTraps.get(j);
						for (int k = 0; k < traps.size(); k++)
						{
							Trapezoid t2 = traps.get(k);
							if (t1 == t2 || t1.connected(t2) || t2.connected(t1))
								disjSets.union(oldSize+j, k);
						}
					}
					
					for (int j = 0; j < tempTraps.size(); j++)
					{
						Trapezoid t1 = tempTraps.get(j);
						for (int k = 0; k < traps.size(); k++)
						{
							Trapezoid t2 = traps.get(k);
							if (!t2.isFilled && disjSets.find(k) == disjSets.find(oldSize+j))
							{
								t1.isFilled = false;
								break;
							}
						}
					}
				}
				else	//oldSweep = null because first run
				{
					ArrayList<EdgePointTuple> intersects = new ArrayList<EdgePointTuple>();
					for (Line2D.Double e: edges)
					{
						if (sweep.intersectsLine(e))
						{
							if (e.y1 == e.y2)
							{
								intersects.add(new EdgePointTuple(e, new Point((e.getX1()+e.getX2())/2, e.getY1())));
							}
							else
							{
								Point i = getIntersection(sweep, e);
								intersects.add(new EdgePointTuple(e, i));
							}
						}
					}
					
					Collections.sort(intersects);
					
					for (int i = 1; i < intersects.size(); i++)
					{
						//EdgePointTuple a = intersects.get(i-1);
						EdgePointTuple b = intersects.get(i);
						
						if (b.line.y1 == b.line.y2)
						{
							while (b.line.y1 == b.line.y2)
							{
								//this is crucial part when holes exist on bottom (min y value, first sweep)
								if (holes.contains(b.line.getP1()) || holes.contains(b.line.getP2()))
								{
									holes.add(new Point(b.line.x1, b.line.y1));
									holes.add(new Point(b.line.x2, b.line.y2));
								}
								
								intersects.remove(b);
								b = intersects.get(i);
							}
						}
					}
				}
				
				oldSweep = sweep;
			}
			
			double areaSum = 0;
			for (Trapezoid t : traps)
			{
				if (t.isFilled)
					areaSum += t.area();
			}
			
			System.out.println("Cave " + caveNum + ": Oil capacity = " + Math.round(areaSum));
			System.out.println();
			n = in.nextInt();
			caveNum++;
		}
	}
	
	private static double det(double x1, double y1, double x2, double y2) {
		return x1 * y2 - y1 * x2 + 0.0;
	}
	
	private static Point getIntersection(Line2D.Double a, Line2D.Double b)
	{
		double d = det(a.x1 - a.x2, a.y1 - a.y2, b.x1 - b.x2, b.y1 - b.y2);
		double x12 = det(a.x1, a.y1, a.x2, a.y2);
		double x34 = det(b.x1, b.y1, b.x2, b.y2);
		
		// assert d != 0 (lines are known to intersect and are not at right angle)
		double x = det(x12, a.x1-a.x2, x34, b.x1-b.x2) / d;
		double y = det(x12, a.y1-a.y2, x34, b.y1-b.y2) / d;
		return new Point(x+0.0, y+0.0);
	}

	
	private static class EdgePointTuple implements Comparable<EdgePointTuple>
	{
		Line2D.Double line;
		Point p;
		
		public EdgePointTuple(Line2D.Double l, Point p)
		{
			this.line = l;
			this.p = p;
		}

		@Override
		public int compareTo(EdgePointTuple o) {
			return p.compareTo(o.p);
		}
	}
	
	private static class Point extends Point2D.Double implements Comparable<Point>
	{
		
		public Point(double x, double y)
		{
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Point o) {
			if (y == o.y)
			{
				if (x < o.x)
					return -1;
				else if (x > o.x)
					return 1;
				else
					return 0;
			}
			else if (y < o.y)
				return -1;
			else 
				return 1;
		}
		
		public int hashCode()
		{
			return super.hashCode(); 
		}
		
		public boolean equals(Object o)
		{
			if (o instanceof Point)
			{
				Point p = (Point) o;
				return (this.x == p.x && this.y == p.y);
			}
			return false;
		}
	}
	
	private static class DisjointSets
	{
		int N;
		int[] arr;
		
		public DisjointSets(int N)
		{
			this.N = N;
			arr = new int[N];
			for (int i = 0; i < N; i++)
			{
				arr[i] = -1;
			}
		}
		
		public void union(int a, int b)
		{
			int root1 = find(a);
			int root2 = find(b);
			
			if (root1 == root2)
				return;
			
			if (arr[root2] < arr[root1])
			{
				arr[root1] = root2;
			}
			else
			{
				if (arr[root1] == arr[root2])
				{
					arr[root1]--;
				}
				arr[root2] = root1;
			}
			
			N--;
		}
		
		public int find(int a)
		{
			try {
			if (arr[a] < 0)
				return a;
			
			arr[a] = find(arr[a]);
			}
			catch (Exception e)
			{
				System.exit(0);
			}

			return arr[a];
		}
	}

	private static class Trapezoid
	{
		public Line2D.Double top, bottom;
		public boolean isFilled;
		
		public Trapezoid(Point ll, Point lr, Point ul, Point ur)
		{
			this.top = new Line2D.Double(ul.x, ul.y, ur.x, ur.y);
			this.bottom = new Line2D.Double(ll.x, ll.y, lr.x, lr.y);
			isFilled = true;
		}
		
		public double area()
		{
			double b1 = top.getP1().distance(top.getP2());
			double b2 = bottom.getP1().distance(bottom.getP2());
			double h = top.getY1() - bottom.getY1();
			
			return (b1 + b2) * h / 2;
		}
		
		public boolean connected(Trapezoid other)
		{
			if (top.y1 == other.bottom.y1)
			{
				return 	(top.x1 >= other.bottom.x1 && top.x1 <= other.bottom.x2) ||
						(top.x2 >= other.bottom.x1 && top.x2 <= other.bottom.x2);
			}
			else if (bottom.y1 == other.top.y1)
			{
				return  (bottom.x1 >= other.top.x1 && bottom.x1 <= other.top.x2) ||
						(bottom.x2 >= other.top.x1 && bottom.x2 <= other.top.x2);
			}
			else
				return false;
		}
		
		public String toString()
		{
			String s = "";
			s += top.x1 + "," + top.y1 + " - " + top.x2 + "," + top.y2 + "\n";
			s += bottom.x1 + "," + bottom.y1 + " - " + bottom.x2 + "," + bottom.y2 + "\n";
			s += isFilled + "\n";
			return s;
		}
	}
}
