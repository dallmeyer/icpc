import java.util.Scanner;

public class Partitions {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int w = in.nextInt();
		int h = in.nextInt();
		in.nextLine();
		int caseNum = 1;
		
		while (w != 0 && h!= 0)
		{
			char[][] a = new char[h+1][2*w+1];
			char[][] b = new char[h+1][2*w+1];
			
			for (int i = 0; i <= h; i++)
			{
				String s = in.nextLine();
				
				for (int j = 0; j <= 2*w; j++)
				{
					a[i][j] = s.charAt(j);
					b[i][j] = s.charAt(2*w+2+j);
				}
			}
			
			char[][] inf = new char[h+1][2*w+1];
			char[][] sup = new char[h+1][2*w+1];
			
			for (int i = 0; i <= h; i++)
			{
				for (int j = 0; j <= 2*w; j++)
				{
					if (a[i][j] == '_' || b[i][j] == '_')
						inf[i][j] = '_';
					else if (a[i][j] == '|' || b[i][j] == '|')
						inf[i][j] = '|';
					else
						inf[i][j] = ' ';
					
					if ((i == 0 || i == h) && (j%2 == 1))
					{
						sup[i][j] = '_';
					}				
					else if ((i > 0) && (j == 0 || j == 2*w))
					{
						sup[i][j] = '|';
					}
					else
					{
						sup[i][j] = ' ';
					}
				}
			}
			
			//if both contain a full partition (one which cuts all the way across)
			if (containsPart(a, b, h, w))
			{
				findPart(a, b, sup, 0, h, 0, 2*w);
			}
			else if (nonEmpty(a, b, h, w))
			{
				useIntersection(a, b, sup, h, w);
			}
			
			System.out.println("Case " + caseNum + ": ");
			for (int i = 0; i <= h; i++)
			{
				for (int j = 0; j <= 2*w; j++)
				{
					System.out.print(inf[i][j]);
				}
				
				System.out.print(' ');

				for (int j = 0; j <= 2*w; j++)
				{
					System.out.print(sup[i][j]);
				}
				System.out.println();
			}
			System.out.println();
			
			caseNum++;
			w = in.nextInt();
			h = in.nextInt();
			in.nextLine();
		}
	}
	
	private static void useIntersection(char[][] a, char[][] b, char[][] sup, int h, int w)
	{
		for (int r = 1; r <= h; r++)
		{
			for (int c = 1; c <= 2*w; c++)
			{
				if (a[r][c] == b[r][c])
					sup[r][c] = a[r][c];
			}
		}		
	}
	
	private static boolean nonEmpty(char[][] a, char[][] b, int h, int w)
	{
		boolean aEmpty = true, bEmpty = true;
		
		for (int r = 1; r < h; r++)
		{
			for (int c = 1; c <= 2*w; c++)
			{
				if (a[r][c] != ' ')
					aEmpty = false;
				if (b[r][c] != ' ')
					bEmpty = false;
				
				if (!aEmpty && !bEmpty)
					return true;
			}
		}
		for (int c = 2; c <= 2*w; c+=2)
		{
			if (a[h][c] != ' ')
				aEmpty = false;
			if (b[h][c] != ' ')
				bEmpty = false;
			
			if (!aEmpty && !bEmpty)
				return true;
		}
		
		return false;
	}
	
	private static boolean containsPart(char[][] a, char[][] b, int h, int w)
	{		
		boolean partA = true, partB = true;
		
		if (h > 1)
		{
			for (int r = 1; r < h; r++)
			{
				partA = true;
				for (int c = 1; c < 2*w; c+=2)
				{
					if (a[r][c] == ' ')
					{
						partA = false;
						break;
					}
				}
				
				if (partA)
				{
					break;
				}
			}
		}
		
		if (!partA && 2*w > 2)
		{
			for (int c = 2; c < 2*w; c+=2)
			{
				partA = true;
				for (int r = 1; r <= h; r++)
				{
					if (a[r][c] == ' ')
					{
						partA = false;
						break;
					}
				}
				
				if (partA)
				{
					break;
				}
			}
		}
		
		if (h > 1)
		{
			for (int r = 1; r < h; r++)
			{
				partB = true;
				for (int c = 1; c < 2*w; c+=2)
				{
					if (b[r][c] == ' ')
					{
						partB = false;
						break;
					}
				}
				
				if (partB)
				{
					break;
				}
			}
		}
		
		if (!partB && 2*w > 2)
		{
			for (int c = 2; c < 2*w; c+=2)
			{
				partB = true;
				for (int r = 1; r <= h; r++)
				{
					if (b[r][c] == ' ')
					{
						partB = false;
						break;
					}
				}
				
				if (partB)
				{
					break;
				}
			}
		}
		
		return partA || partB;
	}
	
	private static void findPart(char[][] a, char[][] b, char[][] sup, int r1, int r2, int c1, int c2)
	{
//		System.out.println(r1 + " " + r2 + " " + c1 + " " + c2);
		
		if (r2 - r1 > 1)
		{
			for (int r = r1+1; r < r2; r++)
			{
				boolean part = true;
				for (int c = c1+1; c < c2; c+=2)
				{
					if (a[r][c] == ' ' || b[r][c] == ' ')
					{
						part = false;
						break;
					}
				}
				
				if (part)
				{
					for (int c = c1+1; c < c2; c+=2)
					{
						sup[r][c] = '_';
					}
					findPart(a, b, sup, r1, r, c1, c2);
					findPart(a, b, sup, r, r2, c1, c2);
					
					return;
				}
			}
		}
		
		if (c2 - c1 > 2)
		{
			for (int c = c1+2; c < c2; c+=2)
			{
				boolean part = true;
				for (int r = r1+1; r <= r2; r++)
				{
					if (a[r][c] == ' ' || b[r][c] == ' ')
					{
						part = false;
						break;
					}
				}
				
				if (part)
				{
					for (int r = r1+1; r <= r2; r++)
					{
						sup[r][c] = '|';
					}
					findPart(a, b, sup, r1, r2, c1, c);
					findPart(a, b, sup, r1, r2, c, c2);
					
					return;
				}
			}
		}
	}
}
