package ptTryouts;

import java.util.ArrayList;
import java.util.Scanner;

public class E {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int T = in.nextInt();
		for (int t = 0; t < T; t++)
		{
			int N = in.nextInt();
			ArrayList<Node> path = new ArrayList<Node>();
			
			int x = 0, y = 0;
			Node n = new Node(x,y);
			path.add(n);			
			
			for (int i = 0; i < N; i++)
			{
				char c = in.next().charAt(0);
				switch (c)
				{
				case 'S' : y--;
					break;
				case 'E' : x++;
					break;
				case 'N' : y++;
					break;
				case 'W' : x--;
					break;
				}
				
				Node nextN = new Node(x,y);
				int z = path.indexOf(nextN);
				
				if (z >= 0)
				{
					int size = path.size();
					for (int j = size-1; j > z; j--)
					{
						path.remove(j);
					}		
				}
				else
				{
					path.add(nextN);
				}
			}
			
			System.out.println(path.size() - 1);
		}
	}
	
	public static class Node
	{
		public int x,y;
		
		public Node(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public boolean equals(Object o)
		{
			if (o instanceof Node)
			{
				Node n = (Node) o;
				if (this.x == n.x && this.y == n.y)
					return true;
			}
			return false;
		}
	}
	
}
