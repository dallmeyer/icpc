import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class ToilForOil {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int caveNum = 1;
		int n = in.nextInt();
		while (n != 0)
		{
			ArrayList<Double> y = new ArrayList<Double>();
			ArrayList<Double> x = new ArrayList<Double>();
			ArrayList<Boolean> h = new ArrayList<Boolean>();
			Line2D.Double[] edges = new Line2D.Double[n];
			
			double maxX = Double.MIN_VALUE;
			double minX = Double.MAX_VALUE;
			
			for (int i = 0; i < n; i++)
			{
				Double xx = in.nextDouble();
				if (xx > maxX)
					maxX = xx;
				if (xx < minX)
					minX = xx;
				
				x.add(xx);				
				y.add(in.nextDouble());
				h.add(in.nextInt() == 1);
				
				if (i > 0)
				{
					edges[i-1] = new Line2D.Double(x.get(i-1), y.get(i-1), x.get(i), y.get(i));
				}
			}
			edges[n-1] = new Line2D.Double(x.get(n-1), y.get(n-1), x.get(0), y.get(0));
			
			ArrayList<Double> sweeps = new ArrayList<Double>();
			for (int i = 0; i < n; i++)
			{
				double yy = y.get(i);
				if (!sweeps.contains(yy))
				{
					sweeps.add(yy);
				}
			}			
			Collections.sort(sweeps);
			
			ArrayList<Trapezoid> traps = new ArrayList<Trapezoid>();
			DisjointSets disjSets = new DisjointSets(3*n);
			
			for (int i = 1; i < sweeps.size(); i++)
			{				
				double yHigh = sweeps.get(i) - 0.001;
				double yLow = sweeps.get(i-1) + 0.001;
				
				ArrayList<Double> intersectionsLow = new ArrayList<Double>();
				ArrayList<Double> intersectionsHigh = new ArrayList<Double>();
			
				Line2D.Double sweepHigh = new Line2D.Double(minX, yHigh, maxX, yHigh);
				Line2D.Double sweepLow = new Line2D.Double(minX, yLow, maxX, yLow);
				
				for (int j = 0; j < n; j++)
				{
					if (sweepHigh.intersectsLine(edges[j]) && edges[j].getY2() != edges[j].getY1())
					{
						double xInt = edges[j].getX1() + (sweeps.get(i)-edges[j].getY1()) * (edges[j].getX2()-edges[j].getX1()) / (edges[j].getY2()-edges[j].getY1());
						intersectionsHigh.add(xInt);
					}
					if (sweepLow.intersectsLine(edges[j]) && edges[j].getY2() != edges[j].getY1())
					{
						double xInt = edges[j].getX1() + (sweeps.get(i-1)-edges[j].getY1()) * (edges[j].getX2()-edges[j].getX1()) / (edges[j].getY2()-edges[j].getY1());
						intersectionsLow.add(xInt);
					}
				}
				
				Collections.sort(intersectionsHigh);
				Collections.sort(intersectionsLow);
				
				int numTraps = intersectionsHigh.size() / 2;
				ArrayList<Trapezoid> tempTraps = new ArrayList<Trapezoid>();
				
				for (int j = 0; j < numTraps; j++)
				{
					double ll = intersectionsLow.get(2*j);
					double lr = intersectionsLow.get(2*j + 1);					
					
					double ul = intersectionsHigh.get(2*j);
					double ur = intersectionsHigh.get(2*j + 1);	
			
					Trapezoid t = new Trapezoid(ll, lr, sweeps.get(i-1), ul, ur, sweeps.get(i));
					for (int k = 0; k < n; k++)
					{
						if (h.get(k) &&	(t.bottom.getP1().equals(new Point2D.Double(x.get(k), y.get(k))) ||
								t.bottom.getP2().equals(new Point2D.Double(x.get(k), y.get(k)))))
						{
							t.isFilled = false;
							break;
						}
					}
					
					tempTraps.add(t);
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
			
			double areaSum = 0;
			for (Trapezoid t : traps)
			{
				if (t.isFilled)
					areaSum += t.area();
			}
			
			System.out.println("Cave " + caveNum + ": Oil capacity = " + ((int) areaSum));
			System.out.println();
			n = in.nextInt();
			caveNum++;
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
		
		public Trapezoid(double ll, double lr, double ly, double ul, double ur, double uy)
		{
			this.top = new Line2D.Double(ul, uy, ur, uy);
			this.bottom = new Line2D.Double(ll, ly, lr, ly);
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
			s += top.x1 + " to " + top.x2 + " at " + top.y1 + "\n";
			s += bottom.x1 + " to " + bottom.x2 + " at " + bottom.y1;
			return s;
		}
	}
}
