import java.util.Scanner;


public class GreatWallGame {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int caseNo = 1;
		int n = in.nextInt();
		while (n > 0)
		{
			Tuple[] stones = new Tuple[n];
			int[][][] dist = new int[n][n][n];
			
			for (int i = 0; i < n; i++)
			{
				Tuple t = new Tuple(in.nextInt()-1, in.nextInt()-1);
				stones[i] = t;
				
				for (int r = 0; r < n; r++)
				{
					for (int c = 0; c < n; c++)
					{
						dist[i][r][c] = Math.abs(t.row - r) + Math.abs(t.col - c);
					}
				}
			}
			
			int minMoves = Integer.MAX_VALUE;
			for (int row = 0; row < n; row++)
			{
				minMoves = Math.min(minMoves, checkHoriz(stones, row, n, dist));
			}
			for (int col = 0; col < n; col++)
			{
				minMoves = Math.min(minMoves, checkHoriz(stones, col, n, dist));
			}
			minMoves = Math.min(minMoves, checkDiag1(stones, n, dist));
			minMoves = Math.min(minMoves, checkDiag2(stones, n, dist));
			
			System.out.println("Board " + caseNo++ + ": " + minMoves + " moves required.\n");
			
			n = in.nextInt();
		}
	}
	
	private static int checkHoriz(Tuple[] stones, int row, int n, int[][][] dist)
	{
		int totalDist = 0;
		boolean[] stonesUsed = new boolean[n];
		boolean[] spotsUsed = new boolean[n];
		
		for (int i = 0; i < n; i++)
		{
			int minDist = Integer.MAX_VALUE;
			int minSpotIndex = -1;
			int minStoneIndex = -1;
			
			for (int col = 0; col < n; col++)
			{
				if (spotsUsed[col])
					continue;				
				
				for (int stone = 0; stone < n; stone++)
				{
					if (stonesUsed[stone])
						continue;
					
					if (dist[stone][row][col] < minDist)
					{
						minSpotIndex = col;
						minStoneIndex = stone;
						minDist = dist[stone][row][col];
					}
				}
			}
			
			stonesUsed[minStoneIndex] = true;
			spotsUsed[minSpotIndex] = true;
			totalDist += minDist;
		}
		
		return totalDist;
	}
	
	private static int checkVert(Tuple[] stones, int col, int n, int[][][] dist)
	{
		int totalDist = 0;
		boolean[] stonesUsed = new boolean[n];
		boolean[] spotsUsed = new boolean[n];
		
		for (int i = 0; i < n; i++)
		{
			int minDist = Integer.MAX_VALUE;
			int minSpotIndex = -1;
			int minStoneIndex = -1;
			
			for (int row = 0; row < n; row++)
			{
				if (spotsUsed[row])
					continue;				
				
				for (int stone = 0; stone < n; stone++)
				{
					if (stonesUsed[stone])
						continue;
					
					if (dist[stone][row][col] < minDist)
					{
						minSpotIndex = row;
						minStoneIndex = stone;
						minDist = dist[stone][row][col];
					}
				}
			}
			
			stonesUsed[minStoneIndex] = true;
			spotsUsed[minSpotIndex] = true;
			totalDist += minDist;
		}
		
		return totalDist;
	}
	
	private static int checkDiag1(Tuple[] stones, int n, int[][][] dist)
	{
		int totalDist = 0;
		boolean[] stonesUsed = new boolean[n];
		boolean[] spotsUsed = new boolean[n];
		
		for (int i = 0; i < n; i++)
		{
			int minDist = Integer.MAX_VALUE;
			int minSpotIndex = -1;
			int minStoneIndex = -1;
			
			for (int rc = 0; rc < n; rc++)
			{
				if (spotsUsed[rc])
					continue;				
				
				for (int stone = 0; stone < n; stone++)
				{
					if (stonesUsed[stone])
						continue;
					
					if (dist[stone][rc][rc] < minDist)
					{
						minSpotIndex = rc;
						minStoneIndex = stone;
						minDist = dist[stone][rc][rc];
					}
				}
			}
			
			stonesUsed[minStoneIndex] = true;
			spotsUsed[minSpotIndex] = true;
			totalDist += minDist;
		}
		
		return totalDist;
	}
	
	private static int checkDiag2(Tuple[] stones, int n, int[][][] dist)
	{
		int totalDist = 0;
		boolean[] stonesUsed = new boolean[n];
		boolean[] spotsUsed = new boolean[n];
		
		for (int i = 0; i < n; i++)
		{
			int minDist = Integer.MAX_VALUE;
			int minSpotIndex = -1;
			int minStoneIndex = -1;
			
			for (int row = 0; row < n; row++)
			{
				int col = n - row - 1;
				
				if (spotsUsed[row])
					continue;				
				
				for (int stone = 0; stone < n; stone++)
				{
					if (stonesUsed[stone])
						continue;
					
					if (dist[stone][row][col] < minDist)
					{
						minSpotIndex = row;
						minStoneIndex = stone;
						minDist = dist[stone][row][col];
					}
				}
			}
			
			stonesUsed[minStoneIndex] = true;
			spotsUsed[minSpotIndex] = true;
			totalDist += minDist;
		}
		
		return totalDist;
	}
	
	private static class Tuple
	{
		int row, col;
		
		public Tuple(int row, int col)
		{
			this.row = row;
			this.col = col;
		}
	}

}
