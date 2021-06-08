import java.util.LinkedList;
import java.util.Scanner;

public class HauntedGraveyard2 {

	private static int[] xx = { -1, 1, 0, 0 }, yy = { 0, 0, -1, 1 };
	private static int W, H;
	
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		W = in.nextInt();
		H = in.nextInt();
		
		while (W != 0 || H != 0)
		{
			boolean[][] adj = new boolean[W*H][W*H];
			boolean[] hole = new boolean[W*H];
			int[][] weight = new int[W*H][W*H];
			int[] dist = new int[W*H];
			
			for (int x = 0; x < W; x++)
			{
				for (int y = 0; y < H; y++)
				{
					dist[indexer(x,y)] = Integer.MAX_VALUE;
					
					for (int i = 0; i < 4; i++)
					{
						int nextX = x + xx[i],
							nextY = y + yy[i];
						
						if (nextX >= 0 && nextX < W && nextY >= 0 && nextY < H)
						{
							int fromInd = indexer(x,y),
								toInd = indexer(nextX,nextY);
							
							adj[fromInd][toInd] = true;
							weight[fromInd][toInd] = 1;
						}
					}
				}
			}
			
			int G = in.nextInt();
			for (int g = 0; g < G; g++)
			{
				int x = in.nextInt(),
					y = in.nextInt();
				
				int i = indexer(x,y);
				for (int j = 0; j < W*H; j++)
				{
					adj[i][j] = false;
				}
				for (int j = 0; j < W*H; j++)
				{
					adj[j][i] = false;
				}
			}
			
			int E = in.nextInt();
			for (int e = 0; e < E; e++)
			{
				int x = in.nextInt(),
					y = in.nextInt(),
					toX = in.nextInt(),
					toY = in.nextInt(),
					T = in.nextInt();
						
				int i = indexer(x,y);
				hole[i] = true;
				
				for (int j = 0; j < W; j++)
				{
					adj[i][j] = false;
				}
				
				int j = indexer(toX,toY);
				adj[i][j] = true;
				weight[i][j] = T;
			}
			
			dist[0] = 0;
			LinkedList<Integer> q = new LinkedList<Integer>();
			q.add(0);
			
			boolean negCycles = false;
			
			qLoop: while (!q.isEmpty())
			{	
				int from = q.poll();
				
				for (int to = 0; to < W*H; to++)
				{
					if (!adj[from][to])
						continue;
					
					int du = dist[from],
						dv = dist[to];
					
					if (hole[from] && du != Integer.MAX_VALUE && du > dv)
					{
						negCycles = true;
						break qLoop;
					}
										
					if (du + weight[from][to] < dv)
					{
						dist[to] = du + weight[from][to];
						q.add(to);
					}
				}
			}
		
			negCycleCheck: for (int from = 0; from < W*H; from++)
			{
				for (int to = 0; to < W*H; to++)
				{
					if (!adj[from][to])
						continue;
					
					int du = dist[from],
						dv = dist[to];
						
					if (du + weight[from][to] < dv)
					{
						negCycles = true;
						break negCycleCheck;
					}
				}
			}
			
			int minTime = dist[W*H - 1];

			if (negCycles || minTime == Integer.MIN_VALUE) {
				System.out.println("Never");
			} else if (minTime == Integer.MAX_VALUE) {
				System.out.println("Impossible");
			} else {
				System.out.println(minTime);
			}
			
			W = in.nextInt();
			H = in.nextInt();
		}
	}
	
	private static int indexer(int x, int y)
	{
		return W*y + x;
	}
}
