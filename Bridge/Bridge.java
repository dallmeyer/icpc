import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Bridge {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int N = in.nextInt();
		for (int i = 0; i < N; i++)
		{			
			if (i > 0)
				System.out.println();
			
			int n = in.nextInt();
			ArrayList<Integer> left = new ArrayList<Integer>();
			for (int j = 0; j < n; j++)
			{
				left.add(in.nextInt());
			}
			
			Collections.sort(left);
			Collections.reverse(left);
			if (left.size() == 1)
			{
				System.out.println(left.get(0) + "\n" + left.get(0));
			}
			else if (left.size() == 2)
			{
				System.out.println((left.get(0)+left.get(1)) + "\n" + left.get(1) + " " + left.get(0));
			}
			else
			{
				Node a = algoA((ArrayList<Integer>) left.clone());
				Node b = algoB((ArrayList<Integer>) left.clone());
				Node sol = null;
				
				if (a.cost < b.cost)
				{
					sol = a;
				}
				else
				{
					sol = b;
				}
				
				System.out.println(sol.cost);
				System.out.println(sol.path);
			}
		}
	}
	
	private static Node algoA(ArrayList<Integer> left)
	{
		String path = "";
		int cost = 0;
		
		Integer a = left.remove(left.size()-1);	//fastest
		Integer b = left.remove(left.size()-1); //second fastest
		
		//send a,b to right, bring a back to begin algorithm
		path += (a + " " + b + "\n" + a + "\n");
		cost += b+a;
		
		while(left.size() > 0)
		{
			int x = left.remove(0);
			
			if (left.size() == 0)
			{
				path += (a + " " + x + "\n");
				cost += x;
			}
			else
			{
				int y = left.remove(0);
				path += (y + " " + x + "\n");
				cost += x;
				path += (b + "\n" + a + " " + b + "\n");
				cost += b+b;
				if (left.size() != 0)
				{
					path += (a + "\n");
					cost += a;
				}
			}
		}
		
		return new Node(cost, path);
	}
	
	private static Node algoB(ArrayList<Integer> left)
	{
		String path = "";
		int cost = 0;
		
		Integer a = left.remove(left.size()-1);
		
		while (!left.isEmpty())
		{
			int x = left.remove(0);
			
			path += (a + " " + x);
			path += "\n";
			path += a + "\n";
			
			cost += x+a;
		}
		
		return new Node(cost, path);
	}
	
	private static class Node
	{
		public int cost;
		public String path;
		
		public Node (int cost, String path)
		{
			this.cost = cost;
			this.path = path;
		}
		
	}

}
