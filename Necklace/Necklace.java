import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


public class Necklace {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int T = in.nextInt();
		mainLoop: for (int t = 0; t < T; t++)
		{
			ArrayList<ArrayList<Bead>> lists = new ArrayList<ArrayList<Bead>>();
			for (int i = 0; i < 51; i++)
			{
				lists.add(new ArrayList<Bead>());
			}
				
			int n = in.nextInt();
			for (int i = 0; i < n; i++)
			{
				int x = in.nextInt();
				int y = in.nextInt();
				Bead b = new Bead(x, y);
				lists.get(x).add(b);
				lists.get(y).add(b);
			}
			ArrayList<Bead> necklace = new ArrayList<Bead>();
			boolean necklaceEmpty = true;
			Bead b = null;
			for (int i = 0; i < 51; i++)
			{
				int s = lists.get(i).size();
				if (s % 2 != 0)
				{
					System.out.println("Case #" + (t+1));
					System.out.println("some beads may be lost\n");
					continue mainLoop;
				}
				if (s > 0 && necklaceEmpty)
				{
					necklaceEmpty = false;
					b = lists.get(i).get(0);
				}	
			}

			lists.get(b.b).remove(b);
			lists.get(b.a).remove(b);
			necklace.add(b);
			
			System.out.println("Case #" + (t+1));
			if (!dfs(lists, n, necklace))
			{
				System.out.println("some beads may be lost\n");
			}
		}
	}
	
	private static void printNecklace(ArrayList<Bead> necklace)
	{
		for (int i = 0; i < necklace.size(); i++)
		{
			Bead b = necklace.get(i);
			System.out.println(b.a + " " + b.b);
		}
		System.out.println();
	}

	private static boolean dfs(ArrayList<ArrayList<Bead>> lists, int n, ArrayList<Bead> necklace)
	{
		HashSet<Bead> used = new HashSet<Bead>();
		
		if (necklace.size() == n)
		{
			Bead a = necklace.get(0);
			Bead b = necklace.get(necklace.size()-1);
			
			if (a.a == b.b)
			{
				printNecklace(necklace);
				return true;
			}
			else
			{
				System.out.println("some beads may be lost\n");
				return false;
			}
		}
		else
		{
			Bead b = necklace.get(necklace.size()-1);
			int bColor = b.b;
			ArrayList<Bead> adj = lists.get(bColor);
			for (int i = 0; i < adj.size(); i++)
			{
				Bead nextB = adj.remove(i);
				if (used.contains(nextB) || used.contains(nextB.flip()))
				{
					continue;
				}
				used.add(nextB);
				
				if (nextB.a == bColor)
				{
					lists.get(nextB.b).remove(nextB);
					necklace.add(nextB);
					
					if (dfs(lists, n, necklace))
					{
						return true;
					}
					
					necklace.remove(necklace.size()-1);
					lists.get(nextB.a).add(nextB);
					lists.get(nextB.b).add(nextB);
				}
				else
				{
					lists.get(nextB.a).remove(nextB);
					nextB = nextB.flip();
					necklace.add(nextB);
					
					if (dfs(lists, n, necklace))
					{
						return true;
					}
					
					necklace.remove(necklace.size()-1);
					lists.get(nextB.a).add(nextB);
					lists.get(nextB.a).add(nextB);
				}				
			}
		}	
		
		return false;
	}
	
	private static class Bead
	{
		public int a,b;
		
		public Bead(int a, int b)
		{
			this.a = a;
			this.b = b;
		}
		
		public int hashCode()
		{
			return (a*100)+b;
		}
		
		public Bead flip()
		{
			return new Bead(this.b, this.a);
		}
	}
}
