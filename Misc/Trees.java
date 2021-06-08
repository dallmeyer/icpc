import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Trees {
	
	static int casenum = 1;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		while (true) {
			int N = in.nextInt();
			int M = in.nextInt();

			if (N == 0 && M == 0) {
				break;
			}
			ArrayList<Edge> e = new ArrayList<Edge>();
			for (int i = 0; i < M; i++) {
				e.add(new Edge(in.nextInt() - 1, in.nextInt() - 1));
			}
			
			solve(N, e);
			casenum++;
		}
	}
	
	public static void solve(int N, ArrayList<Edge> e)
	{
		DisjointSet set = new DisjointSet(N);
		
		HashSet<Integer> good = new HashSet<Integer>();
		HashSet<Integer> bad = new HashSet<Integer>();
		
		// Find disjoint graphs
		for (Edge edge : e)
		{
			if (set.find(edge.to) == set.find(edge.from))
			{
				bad.add(edge.to);
				bad.add(edge.from);
			}
			else
			{
				set.union(edge.to, edge.from);
			}
		}
		
		// Init our good with all the disjoint graphs
		for (int i = 0; i < N; i++)
		{
			good.add(set.find(i));
		}
		
		// Remove graphs with cycles
		for (int i = 0; i < N; i++)
		{
			if (bad.contains(i) && good.contains(set.find(i)))
			{
				good.remove(set.find(i));
			}
		}
		
		printAnswer(good.size());
			
	}
	
	public static void printAnswer(int size)
	{
		
		String ans = "";
		
		if (size == 0)
		{
			ans = "No trees.";
		}
		else if (size == 1)	
		{
			ans = "There is one tree.";
		}
		else {
			ans = "A forest of " + size + " trees.";
		}
		
		
		System.out.printf("Case %d: %s%n", casenum, ans);
		
	}

	static class Edge {
		int to, from;

		public Edge(int to, int from) {
			this.to = to;
			this.from = from;
		}
	}

	static class DisjointSet {
		int s[];
		int N;

		public DisjointSet(int N) {
			this.N = N;
			s = new int[N];
			for (int i = 0; i < N; i++) {
				s[i] = -1;
			}
		}

		public void union(int a, int b) {

			int root1 = find(a);
			int root2 = find(b);

			if (root1 == root2)
				return;

			if (s[root2] < s[root1]) {
				s[root1] = root2; // root2 is taller; make root2 new root
			} else {
				if (s[root1] == s[root2]) {
					s[root1]--; // Both trees same height; new one is taller
				}
				s[root2] = root1; // root1 equal or taller; make root1 new root
			}
			N--;
		}

		public int find(int x) {
			if (s[x] < 0) {
				return x; // x is the root of the tree; return it
			} else {
				s[x] = find(s[x]);
				return s[x]; // Return the root
			}
		}
	}
}
