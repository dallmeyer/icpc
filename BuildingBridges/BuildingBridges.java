/* Matt Dallmeyer - 2003 World Finals (A)*/

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;


public class BuildingBridges {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int cityNum = 1;
		int r = in.nextInt(), c = in.nextInt();
		while (r != 0 && c != 0)
		{
			if (cityNum > 1)
				System.out.println();
			
			int numBridges = 0;
			int bridgeLen = 0;
			
			ArrayList<Building> buildings = new ArrayList<Building>();
			
			String[] city = new String[r];
			for (int i = 0; i < r; i++)
			{
				city[i] = in.next();
				for (int j = 0; j < c; j++)
				{
					if (city[i].charAt(j) == '#')
						buildings.add(new Building(i, j));
				}
			}
			
			int numBuildings = buildings.size();
			
			DisjointSets groups = new DisjointSets(numBuildings);
			PriorityQueue<Edge> edges = new PriorityQueue<Edge>();
			
			for (int i = 0; i < numBuildings; i++)
			{
				for (int j = i + 1; j < numBuildings; j++)
				{
					int dist = buildings.get(i).distance(buildings.get(j));
					if (dist == 0)
					{
						groups.union(i, j);
					}
					else if (dist > 0)
					{
						edges.add(new Edge(i, j, dist));
					}
				}
			}
			
			while (!edges.isEmpty() && groups.N > 1)
			{
				Edge e = edges.poll();
				
				if (groups.find(e.v1) != groups.find(e.v2))
				{
					numBridges++;
					bridgeLen += e.dist;
					
					groups.union(e.v1, e.v2);
				}
			}
			
			int numGroups = groups.N;
			
			System.out.println("City " + cityNum);
			if (numBridges == 0)
			{
				if (numGroups > 1)
				{
					System.out.println("No bridges are possible.");
					System.out.println(numGroups + " disconnected groups");
				}
				else
				{
					System.out.println("No bridges are needed.");
				}
			}
			else
			{
				if (numBridges == 1)					
					System.out.println(numBridges + " bridge of total length " + bridgeLen);
				else
					System.out.println(numBridges + " bridges of total length " + bridgeLen);
				if (numGroups > 1)
				{
					System.out.println(numGroups + " disconnected groups");
				}
			}
			
			cityNum++;
			r = in.nextInt();
			c = in.nextInt();
		}
	}
	
	private static class Edge implements Comparable<Edge>
	{
		int v1, v2, dist;
		
		public Edge(int v1, int v2, int dist)
		{
			this.v1 = v1;
			this.v2 = v2;
			this.dist = dist;
		}

		public String toString()
		{
			return v1 + " to " + v2 + " in " + dist;
		}
		
		@Override
		public int compareTo(Edge o) {
			return dist - o.dist;
		}
		
	}
	
	private static class Building
	{
		int r, c;
		
		public Building (int r, int c)
		{
			this.r = r;
			this.c = c;
		}
		
		public int distance (Building b)
		{
			int rowDist = Math.abs(this.r - b.r);
			int colDist = Math.abs(this.c - b.c);
			
			if (rowDist > 1 && colDist > 1)
			{
				return -1;
			}
			else if (rowDist <= 1 && colDist <= 1)
			{
				return 0;
			}
			else if (rowDist <= 1)
			{
				return colDist - 1;
			}
			else
			{
				return rowDist - 1;
			}
		}
	}
	
	private static class DisjointSets
	{
		int N;
		int[] arr;
		
		public DisjointSets(int N)
		{
			this.N = N;
			arr = new int[N];
			for (int i = 0; i < N; i++)
			{
				arr[i] = -1;
			}
		}
		
		public void union(int a, int b)
		{
			int root1 = find(a);
			int root2 = find(b);
			
			if (root1 == root2)
				return;
			
			if (arr[root2] < arr[root1])
			{
				arr[root1] = root2;
			}
			else
			{
				if (arr[root1] == arr[root2])
				{
					arr[root1]--;
				}
				arr[root2] = root1;
			}
			
			N--;
		}
		
		public int find(int a)
		{
			try {
			if (arr[a] < 0)
				return a;
			
			arr[a] = find(arr[a]);
			}
			catch (Exception e)
			{
				System.exit(0);
			}

			return arr[a];
		}
	}
}
