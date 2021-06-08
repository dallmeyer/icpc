import java.util.ArrayList;
import java.util.Scanner;


public class CarlTheAnt {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int numCases = in.nextInt();
		for (int tc = 1; tc <= numCases; tc++)
		{
			int n = in.nextInt(), m = in.nextInt(), d = in.nextInt();
			
			ArrayList<Ant> burrow = new ArrayList<Ant>();
			Segment b = new Segment(0,0,0,0);
			ArrayList<Segment> segs = new ArrayList<Segment>();
			int x1 = 0, y1 = 0, x2, y2;
			int totalLen = 0;
			for (int i = 0; i < n; i++)
			{
				x2 = in.nextInt();
				y2 = in.nextInt();
				
				Segment oldSeg;
				if (segs.size() == 0)
				{
					oldSeg = b;
				}
				else
				{
					oldSeg = segs.get(segs.size()-1);
				}
				Segment s1 = null;
				if (x1 == x2)
				{
					int len = Math.abs(y1-y2);
					int step = (y2-y1) / len;
					y2 = y1 + step;
					
					for (int j = 0; j < len; j++)
					{
						Segment s = new Segment(x1,y1,x2,y2);
						if (j == 0)
						{
							s1 = s;
						}
						else
						{
							segs.get(segs.size()-1).adj.add(s);
						}
						
						for (int k = 0; k < totalLen; k++)
						{
							Segment o = segs.get(k);
							if (s.follows(o))
							{
								o.adj.add(s);
							}
						}
						

						segs.add(s);

						x1 = x2;
						y1 = y2;

						y2 += step;
					}
					totalLen += len;
				}
				else
				{
					int len = Math.abs(x1-x2);
					int step = (x2-x1) / len;
					x2 = x1 + step;
					
					for (int j = 0; j < len; j++)
					{
						Segment s = new Segment(x1,y1,x2,y2);
						if (j == 0)
						{
							s1 = s;
						}
						else
						{
							segs.get(segs.size()-1).adj.add(s);						
						}
						
						for (int k = 0; k < totalLen; k++)
						{
							Segment o = segs.get(k);
							if (s.follows(o))
							{
								o.adj.add(s);
							}
						}
						
						segs.add(s);
						
						x1 = x2;
						y1 = y2;

						x2 += step;
					}
					totalLen += len;
				}
				oldSeg.adj.add(s1);
			}
			
			int t = 0;
			int antNum = 0;
			ArrayList<Ant> ants = new ArrayList<Ant>();
			ArrayList<Integer> finishOrder = new ArrayList<Integer>();
			
			
			Ant carl = new Ant(antNum, b);
			ants.add(carl);
			antNum++;
			
			int carlTime = -1, lastTime = -1;
			
			while (ants.size() > 0)
			{
				boolean[] canMove = new boolean[antNum];
				for (int i = 0; i < antNum; i++)
				{
					canMove[i] = true;
				}
				
				for (int i = 0; i < ants.size(); i++)
				{
					Ant a1 = ants.get(i);
					Segment s = a1.seg;
					if (s.nextSeg() != null && s.nextSeg().occupiedBy != null)
						canMove[i] = false;
					
					for (int j = i+1; j < ants.size(); j++)
					{
						Ant a2 = ants.get(j);
						if (a1.seg.blocks(a2.seg))
						{
							canMove[j] = false;
						}
					}
					
					if (canMove[i])
					{
						s.lastVisited = t;
						s.occupiedBy = null;
						a1.seg = s.nextSeg();
						
						if (a1.seg == null)
						{
							ants.remove(a1);
							i--;
							if (a1.num == 0)
							{
								carlTime = t+1;
							}
							if (a1.num == m-1)
							{
								lastTime = t+1;
							}
							
							finishOrder.add(a1.num);
						}
						else
						{
							a1.seg.occupiedBy = a1;
						}
					}
				}
				
				if (burrow.size() > 0 && b.nextSeg().occupiedBy == null)
				{
					Ant a = burrow.remove(0);

					ants.add(a);
					a.seg = b.nextSeg();
					a.seg.occupiedBy = a;
				}
				
				if (t > 0 && antNum < m && t % d == 0)
				{
					Ant a = new Ant(antNum, b);
					burrow.add(a);
					antNum++;
				}
				
				t++;
			}
			
			System.out.println("Case " + tc + ":");
			System.out.println("Carl finished the path at time " + carlTime);
			System.out.println("The ants finished in the following order:");
			String s = "";
			for (Integer i : finishOrder)
			{
				s += i + " ";
			}
			s.trim();
			System.out.println(s);
			System.out.println("The last ant finished the path at time " + lastTime);
			if (tc != numCases)
				System.out.println();
		}
	}
	
	private static class Ant
	{
		int num;
		Segment seg;
		
		public Ant(int n, Segment s)
		{
			this.num = n;
			this.seg = s;
		}
	}
	
	private static class Segment
	{
		private int x1, y1, x2, y2;
		Ant occupiedBy = null;
		ArrayList<Segment> adj;
		int lastVisited;
		
		public Segment(int x1, int y1, int x2, int y2)
		{
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			
			lastVisited = -1;
			
			adj = new ArrayList<Segment>();
		}
		
		public boolean follows(Segment s)
		{
			return s.x2 == x1 && s.y2 == y1;
		}
		
		public boolean blocks(Segment s)
		{
			return x2 == s.x2 && y2 == s.y2;
		}
		
		public Segment nextSeg()
		{
			if (adj.size() == 0)
				return null;
			Segment mostRecent = adj.get(0);
			int mostRecentTime = -1;
			for (int i = 1; i < adj.size(); i++)
			{
				Segment s = adj.get(i);
				if (s.lastVisited != -1 && s.lastVisited > mostRecentTime)
				{
					mostRecent = s;
				}
			}
			
			return mostRecent;
		}
	}

}
