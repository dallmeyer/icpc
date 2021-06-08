import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class TouristGuide {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int count = 1;
		
		int N = in.nextInt(), R = in.nextInt();
		while(N != 0 || R != 0)
		{
			ArrayList<Node> nodes = new ArrayList<Node>();
			for (int i = 0; i < N; i++)
				nodes.add(new Node());
			
			for (int i = 0; i < R; i++)
			{
				Node c1 = nodes.get(in.nextInt()-1), c2 = nodes.get(in.nextInt()-1);
				int p = in.nextInt();
				
				c1.adj.add(c2);
				c1.costs.add(p);
				
				c2.adj.add(c1);
				c2.costs.add(p);
			}
			
			Node S = nodes.get(in.nextInt()-1), D = nodes.get(in.nextInt()-1);
			
			LinkedList<Node> q = new LinkedList<Node>();
			S.min = Integer.MAX_VALUE;
			q.add(S);
			
			while (!q.isEmpty())
			{
				Node current = q.removeFirst();
				int n = current.costs.size();
				
				for (int i = 0; i < n; i++)
				{
					int min = Math.min(current.min, current.costs.get(i));
					Node next = current.adj.get(i);
					if (min > next.min)
					{
						next.min = min;
						q.add(next);
					}
				}
			}
			
			double P = in.nextInt();
			double minP = D.min;
			int trips = 0;
			while (P > 0)
			{
				trips++;
				P -= (minP-1);
			}			
			
			System.out.println("Scenario #" + count);
			System.out.println("Minimum Number of Trips = " + trips);
			System.out.println();
			
			count++;
			N = in.nextInt();
			R = in.nextInt();
		}
	}
	
	private static class Node
	{
		public ArrayList<Node> adj;
		public ArrayList<Integer> costs;
		int min;
		
		public Node()
		{
			adj = new ArrayList<Node>();
			costs = new ArrayList<Integer>();
			min = 0;
		}
	}

}
