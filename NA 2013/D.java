package ptTryouts;

import java.util.LinkedList;
import java.util.Scanner;

public class D {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int T = in.nextInt();
		for (int t = 0; t < T; t++)
		{
			in.nextLine();
			
			boolean b[][] = new boolean[7][7];
			for (int i = 0; i < 7; i++)
			{
				String row = in.nextLine();
				for (int j = 0; j < 7; j++)
				{
					char c = row.charAt(j);
					b[i][j] = (c == 'X');
				}
			}
			
			LinkedList<Board> q = new LinkedList<Board>();
			q.add(new Board(b, 0));
			Board board = null;
			
			while(!q.isEmpty())
			{
				board = q.pop();
				b = board.b;
				
				if (isSolved(b))
				{
					break;
				}
				
				//shoot down/up
				for (int j = 0; j < 7; j++)
				{
					if (b[0][j])
					{
						boolean bb[][] = shootDown(deepClone(b), j);
						if (bb != null)
						{
							q.add(new Board(bb, board.depth+1));
						}
					}
					if (b[6][j])
					{
						boolean bb[][] = shootUp(deepClone(b), j);
						if (bb != null)
						{
							q.add(new Board(bb, board.depth+1));
						}
					}
				}
				
				//shoot left/right
				for (int i = 0; i < 7; i++)
				{
					if (b[i][0])
					{
						boolean bb[][] = shootLeft(deepClone(b), i);
						if (bb != null)
						{
							q.add(new Board(bb, board.depth+1));
						}
					}
					if (b[i][6])
					{
						boolean bb[][] = shootRight(deepClone(b), i);
						if (bb != null)
						{
							q.add(new Board(bb, board.depth+1));
						}
					}
				}
			}
			
			if (isSolved(b))
			{
				System.out.println(board.depth);
			}
			else
			{
				System.out.println(-1);
			}
		}
	}
	
	private static class Board
	{
		public boolean[][] b;
		public int depth;
		
		public Board(boolean[][] b, int d)
		{
			this.b = b;
			this.depth = d;
		}
	}
	
	public static boolean isSolved(boolean[][] b)
	{
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (b[i][j])
				{
					for (int a = 0; a < 3; a++)
					{
						for (int c = 0; c < 3; c++)
						{
							if (!b[i+a][j+c])
								return false;
						}
					}
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean[][] shootRight(boolean[][] b, int i)
	{
		int gap = -1;
		
		for (int j = 5; j >= 0; j--)
		{
			if (!b[i][j] && gap < 0)
			{
				gap = j;
			}
			else if (b[i][j] && gap >= 0)
			{
				for (int jj = 6; jj > gap; jj--)
				{
					b[i][jj] = false;
					b[i][6-jj+j+1] = true;
				}
				return b;
			}
		}

		return null;
	}
	
	public static boolean[][] shootLeft(boolean[][] b, int i)
	{
		int gap = -1;
		
		for (int j = 1; j < 7; j++)
		{
			if (!b[i][j] && gap < 0)
			{
				gap = j;
			}
			else if (b[i][j] && gap >= 0)
			{
				for (int jj = 0; jj < gap; jj++)
				{
					b[i][jj] = false;
					b[i][j-1-jj] = true;
				}
				return b;
			}
		}

		return null;
	}
	
	public static boolean[][] shootUp(boolean[][] b, int j)
	{
		int gap = -1;
		
		for (int i = 5; i >= 0; i--)
		{
			if (!b[i][j] && gap < 0)
			{
				gap = i;
			}
			else if (b[i][j] && gap >= 0)
			{
				for (int ii = 6; ii > gap; ii--)
				{
					b[ii][j] = false;
					b[6-ii+i+1][j] = true;
				}
				return b;
			}
		}

		return null;
	}
	
	public static boolean[][] shootDown(boolean[][] b, int j)
	{
		int gap = -1;
		
		for (int i = 1; i < 7; i++)
		{
			if (!b[i][j] && gap < 0)
			{
				gap = i;
			}
			else if (b[i][j] && gap >= 0)
			{
				for (int ii = 0; ii < gap; ii++)
				{
					b[ii][j] = false;
					b[i-1-ii][j] = true;
				}
				return b;
			}
		}

		return null;
	}
	
	public static boolean[][] deepClone(boolean[][] b)
	{
		boolean bb[][] = new boolean[7][7];
		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				bb[i][j] = b[i][j];
			}
		}
		
		return bb;
	}
}
