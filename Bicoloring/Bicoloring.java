import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class Bicoloring {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int n = in.nextInt();
		mainLoop: while (n != 0)
		{
			ArrayList<Node> v = new ArrayList<Node>();
			for (int i = 0; i < n; i++)
			{
				v.add(new Node());
			}
			int l = in.nextInt();
			for (int i = 0; i < l; i++)
			{
				Node a = v.get(in.nextInt());
				Node b = v.get(in.nextInt());
				a.adj.add(b);
				b.adj.add(a);
			}
			
			LinkedList<Node> q = new LinkedList<Node>();
			ArrayList<Node> visited = new ArrayList<Node>();
			Node a = v.get(0);
			a.color = 1;
			q.add(a);
			visited.add(a);
			while (!q.isEmpty())
			{		
				a = q.removeFirst();
				
				for (Node b : a.adj)
				{
					if (!visited.contains(b))
					{
						b.color = -1 * a.color;
						q.add(b);
						visited.add(b);
					}
					else
					{
						if (b.color == a.color)
						{
							System.out.println("NOT BICOLORABLE.");
							n = in.nextInt();
							continue mainLoop;
						}
					}
				}
			}
			System.out.println("BICOLORABLE.");
			
			n = in.nextInt();
		}
	}
	
	private static class Node
	{
		public int color;
		public ArrayList<Node> adj;
		
		public Node ()
		{
			this.color = 0;
			this.adj = new ArrayList<Node>();
		}
	}

}
