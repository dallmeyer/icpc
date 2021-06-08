import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class UniformSubtrees2 {
	private static int sInd;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		String line = in.next();
		while (!line.equals("0")) {
			sInd = 0;
			Node root = parse(line);

			traverse(root, "");
			
			line = in.next();
		}
	}
	
	private static void traverse(Node n, String s)
	{
		System.out.println(s + "0");
		
		for (int i = 0; i < n.children.size(); i++)
		{
			traverse(n.children.get(i), s + (i+1) + " ");
		}
	}

	private static Node parse(String line) {
		Node n = new Node();

		sInd++; // parse opening parens
		while (line.charAt(sInd) == '(') {
			n.children.add(parse(line));
		}
		sInd++; // parse closing parens
		Collections.sort(n.children);
		
		if (n.children.size() == 0)
		{
			n.depth = 0;
			n.leaves = 1;
		}
		else
		{
			n.depth = n.children.get(0).depth + 1;
			n.leaves = 0;
			for (Node c : n.children)
			{
				n.leaves += c.leaves;
			}
		}
		
		return n;
	}

	private static class Node implements Comparable<Node> {
		ArrayList<Node> children;
		int depth, leaves;

		public Node() {
			this.children = new ArrayList<Node>();
		}

		@Override
		public int compareTo(Node o) {
			int d = o.leaves - this.leaves;
			
			if (d == 0)
			{
				return o.depth - this.depth;
			}
			
			return d;
		}		
	}
}
