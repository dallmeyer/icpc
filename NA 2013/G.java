package ptTryouts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class G {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		while (in.hasNext())
		{
			int n = in.nextInt();
			HashMap<Integer, ArrayList<Stone>> map = new HashMap<Integer, ArrayList<Stone>>();
			for (int i = 0; i < n; i++)
			{
				int a = in.nextInt(),
						b = in.nextInt(),
						h = in.nextInt();
				
				Stone s = new Stone(a,b,h);
				if (map.containsKey(a))
				{
					map.get(a).add(s);
				}
				else
				{
					map.put(a, new ArrayList<Stone>());
					map.get(a).add(s);
				}
				if (map.containsKey(b))
				{
					map.get(b).add(s);
				}
				else
				{
					map.put(b, new ArrayList<Stone>());
					map.get(b).add(s);
				}
			}
			
			int S = in.nextInt();
			int E = in.nextInt();
			
			LinkedList<ArrayList<Stone>> q = new LinkedList<ArrayList<Stone>>();
			ArrayList<Stone> starters = map.get(S);
			Collections.sort(starters);
			
			for (Stone stone: starters)
			{
				if (stone.a != S)
					stone = stone.flip();
				
				ArrayList<Stone> x = new ArrayList<Stone>();
				x.add(stone);
				q.add(x);
			}
			ArrayList<Stone> x = null;
			while (!q.isEmpty())
			{
				x = q.pop();
				Stone last = x.get(x.size()-1);
				
				if (last.b == E)
				{
					
					break;
				}
				
				ArrayList<Stone> adj = map.get(last.b);
				Collections.sort(adj);
				
				for (Stone stone : adj)
				{
					ArrayList<Stone> xx = deepClone(x);
					if (stone.a != last.b)
						stone = stone.flip();
					
					xx.add(stone);
					q.add(xx);
				}
			}
			
			double d = 0;
			for (Stone s: x)
			{
				d += s.cost();
			}
			System.out.printf("%1.2f\n", (.02*d));
		}
	}
	
	private static ArrayList<Stone> deepClone(ArrayList<Stone> a)
	{
		ArrayList<Stone> b = new ArrayList<Stone>();
		for (Stone s: a)
			b.add((Stone) s.clone());
		
		return b;
	}
	
	private static class Stone implements Comparable<Stone>
	{
		int a, b, h;
		
		public Stone(int a, int b, int h)
		{
			this.a = a;
			this.b = b;
			this.h = h;
		}
		
		public Stone clone()
		{
			return new Stone(a,b,h);
		}
		
		public Stone flip()
		{
			return new Stone(b, a, h);
		}
		
		public double cost()
		{
			return ((double) (b+a)*h)/2;
		}

		public int compareTo(Stone o) {
			double d = this.cost() - o.cost();
			if (d < 0)
				return -1;
			if (d==0)
				return 0;
			
			return 1;
		}
	}

}
