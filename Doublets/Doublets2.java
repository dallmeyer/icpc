import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class Doublets2 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		ArrayList<String> dict = new ArrayList<String>();
		String s = in.nextLine();
		while (!s.equals(""))
		{
			dict.add(s);
			s = in.nextLine();
		}
		
		ArrayList<ArrayList<Integer>> map = new ArrayList<ArrayList<Integer>>();
		int len = dict.size();
		for (int i = 0; i < len; i++)
		{
			map.add(new ArrayList<Integer>());
		}
		
		for (int i = 0; i < len; i++)
		{
			String a = dict.get(i);
			for (int j = i+1; j < len; j++)
			{
				String b = dict.get(j);
				
				if (isDoublet(a, b))
				{
					map.get(i).add(j);
					map.get(j).add(i);
				}
			}
		}		
		
		while (in.hasNext())
		{
			LinkedList<Node> q = new LinkedList<Node>();
			ArrayList<Integer> visited = new ArrayList<Integer>();
			String a = in.next();
			String b = in.next();
			int aa = dict.indexOf(a);
			int bb = dict.indexOf(b);
			
			Node n = new Node(aa, null);
			q.add(n);
			
			while (!q.isEmpty())
			{
				n = q.removeFirst();
				if (n.s == bb)
				{
					break;
				}
				
				ArrayList<Integer> adj = map.get(n.s);
				for (int i : adj)
				{
					if (!visited.contains(i))
					{
						Node nn = new Node(i, n);
						q.add(nn);
					}
				}
				
				n = null;
			}
			if (n == null)
			{
				System.out.println("No solution.\n");
			}
			else
			{
				LinkedList<Integer> sol = new LinkedList<Integer>();
				
				while (n != null)
				{
					sol.add(n.s);
					n = n.prev;
				}
				while (!sol.isEmpty())
				{
					System.out.println(dict.get(sol.removeLast()));
				}
				System.out.println();
			}
		}			
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
	
	private static class Node
	{
		public int s;
		public Node prev;
		
		public Node(int s, Node p){
			this.s = s;
			this.prev = p;
		}
	}

}
