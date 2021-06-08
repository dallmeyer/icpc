import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class Doublets {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		ArrayList<String> nodes = new ArrayList<String>();
		ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
		String s = in.nextLine();
		
		while (!s.equals(""))
		{
			nodes.add(s);
			adj.add(new ArrayList<Integer>());
			s = in.nextLine();
		}
		
		for (int i = 0; i < nodes.size()-1; i++)
		{
			String a = nodes.get(i);
			for (int j = i+1; j < nodes.size(); j++)
			{				
				String b = nodes.get(j);
				
				if (isDoublet(a,b))
				{
					adj.get(i).add(j);
					adj.get(j).add(i);
				}
			}
		}
		
		while(in.hasNext())
		{
			String x = in.next();
			String y = in.next();			
			int xi = nodes.indexOf(x);	
			int yi = nodes.indexOf(y);
			
			ArrayList<Integer> path = new ArrayList<Integer>();
			ArrayList<Integer> visited = new ArrayList<Integer>();
			path.add(xi);
			visited.add(xi);
			
			LinkedList<ArrayList<Integer>> queue = new LinkedList<ArrayList<Integer>>();
			queue.add(path);
			while (true)
			{
				if (queue.isEmpty())
				{
					path = null;
					break;
				}
				
				path = queue.removeFirst();
				int newest = path.get(path.size()-1);
				if (newest == yi)
				{
					break;
				}
				
				ArrayList<Integer> nodeAdj = adj.get(newest);
				for (int n : nodeAdj)
				{
					if (!visited.contains(n))
					{
						ArrayList<Integer> pathClone = (ArrayList<Integer>) path.clone();
						pathClone.add(n);
						visited.add(n);
						queue.add(pathClone);
					}
				}
			}
			
			if (path != null)
			{
				for (Integer n : path)
				{
					System.out.println(nodes.get(n));
				}
				System.out.println();
			}
			else
			{
				System.out.println("No solution.");
				System.out.println();
			}
		}
		
		in.close();
	}
	
	private static boolean isDoublet(String a, String b)
	{
		if (a.length() != b.length())
			return false;
		
		int diff = 0;
		for (int  i = 0; i < a.length() && diff < 2; i++)
		{
			if (a.charAt(i) != b.charAt(i))
				diff++;
		}
		
		return diff == 1;
	}
}
