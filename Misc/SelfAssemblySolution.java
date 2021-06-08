// Arup Guha
// 7/4/2013
// Solution to 2013 World Finals Problem A: Self-Assembly

// Note: The key here is that you don't really need to store all 40000 blocks and search
//       through them. Rather, there are 52 (A- through Z+) nodes of interest and you're
//       just looking for a cycle in this reduced graph.

import java.util.*;

public class SelfAssemblySolution {

	final public static int SIZE = 52;

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int n = stdin.nextInt();

		int[][] graph = new int[SIZE][SIZE];

		// Build the graph.
		for (int i=0; i<n; i++) {
			String s = stdin.next();
			addEdges(graph, s);
		}

		// All we care is whether or not this graph has a cycle.
		if (hasCycle(graph))
			System.out.println("unbounded");
		else
			System.out.println("bounded");
	}

	public static void addEdges(int[][] graph, String s) {

		ArrayList<Integer> nodes = getNodes(s);

		// Now add in these edges.
		for (int i=0; i<nodes.size(); i++) {
			for (int j=0; j<nodes.size(); j++) {

				if (i == j) continue;

				// We connect this vertex to the other vertex's "opposite" sign.
				int from = nodes.get(i);
				int to = (nodes.get(j) + SIZE/2)%SIZE;
				graph[from][to] = 1;
			}
		}
	}

	// Get the valid nodes represented by the molecule s.
	public static ArrayList<Integer> getNodes(String s) {

		ArrayList<Integer> list = new ArrayList<Integer>();

		// For each side, parse out its value. A- to Z- = 0 to 25, A+ to Z+ = 26 to 51.
		for (int i=0; i<s.length(); i+=2) {
			int val = s.charAt(i) - 'A';
			if (val < 0) continue;
			char sign = s.charAt(i+1);
			if (sign == '+') val += SIZE/2;
			list.add(val);
		}

		return list;
	}

	// Returns true iff graph contains a cycle.
	public static boolean hasCycle(int[][] graph) {

		// Run Floyd's.
		for (int k=0; k<SIZE; k++)
			for (int i=0; i<SIZE; i++)
				for (int j=0; j<SIZE; j++)
					if (graph[i][k] == 1 && graph[k][j] == 1)
						graph[i][j] = 1;

		// A cycle is just a path from i to itself.
		for (int i=0; i<SIZE; i++)
			if (graph[i][i] == 1)
				return true;

		// If we never found one, there's no cycle!
		return false;
	}

}