import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;


public class Sculpture {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int cases = in.nextInt();
		for (int c = 0; c < cases; c++)
		{
			HashSet<Integer> uniqueX = new HashSet<Integer>();
			HashSet<Integer> uniqueY = new HashSet<Integer>();
			HashSet<Integer> uniqueZ = new HashSet<Integer>();
			
			int n = in.nextInt();
			int[] x = new int[2*n];
			int[] y = new int[2*n];
			int[] z = new int[2*n];
			
			for (int i = 0; i < 2*n; i+=2)
			{
				x[i] = in.nextInt();
				y[i] = in.nextInt();
				z[i] = in.nextInt();
				
				x[i+1] = x[i] + in.nextInt();
				y[i+1] = y[i] + in.nextInt();
				z[i+1] = z[i] + in.nextInt();
				
				uniqueX.add(x[i]);
				uniqueX.add(x[i+1]);
				uniqueY.add(y[i]);
				uniqueY.add(y[i+1]);
				uniqueZ.add(z[i]);
				uniqueZ.add(z[i+1]);
			}

			uniqueX.add(0);
			uniqueX.add(1000);
			uniqueY.add(0);
			uniqueY.add(1000);
			uniqueZ.add(0);
			uniqueZ.add(1000);
			
			ArrayList<Integer> xx = new ArrayList<Integer>(uniqueX);
			ArrayList<Integer> yy = new ArrayList<Integer>(uniqueY);
			ArrayList<Integer> zz = new ArrayList<Integer>(uniqueZ);
			
			Collections.sort(xx);
			Collections.sort(yy);
			Collections.sort(zz);
			
			int xn = xx.size()-1,
				yn = yy.size()-1,
				zn = zz.size()-1;
			
			Box[][][] boxes = new Box[xn][yn][zn];
			for (int xi = 0; xi < xn; xi++)
			{
				for (int yi = 0; yi < yn; yi++)
				{
					for (int zi = 0; zi < zn; zi++)
					{
						boxes[xi][yi][zi] = new Box(xi, yi, zi,
													xx.get(xi),	  yy.get(yi),	zz.get(zi),
													xx.get(xi+1), yy.get(yi+1),	zz.get(zi+1));
					}
				}
			}
			
			for (int i = 0; i < 2*n; i+=2)
			{
				int xl = xx.indexOf(x[i]),
					xh = xx.indexOf(x[i+1]),
					yl = yy.indexOf(y[i]),
					yh = yy.indexOf(y[i+1]),
					zl = zz.indexOf(z[i]),
					zh = zz.indexOf(z[i+1]);
						
				for (int xi = xl ; xi < xh; xi++)
				{
					for (int yi = yl ; yi < yh; yi++)
					{
						for (int zi = zl ; zi < zh; zi++)
						{
							boxes[xi][yi][zi].s = BoxStatus.FILLED;
						}
					}
				}
			}
			
			LinkedList<Box> q = new LinkedList<Box>();
			for (int yi = 0; yi < yn; yi++)
			{
				for (int zi = 0; zi < zn; zi++)
				{
					Box b = boxes[0][yi][zi];
					if (b.s == BoxStatus.OTHER)
					{
						b.s = BoxStatus.OUTSIDE;
						q.add(b);
					}
				}
			}
			
			for (int xi = 0; xi < xn; xi++)
			{
				for (int zi = 0; zi < zn; zi++)
				{
					Box b = boxes[xi][0][zi];
					if (b.s == BoxStatus.OTHER)
					{
						b.s = BoxStatus.OUTSIDE;
						q.add(b);
					}
				}
			}
			
			for (int yi = 0; yi < yn; yi++)
			{
				for (int xi = 0; xi < xn; xi++)
				{
					Box b = boxes[xi][yi][0];
					if (b.s == BoxStatus.OTHER)
					{
						b.s = BoxStatus.OUTSIDE;
						q.add(b);
					}
				}
			}
			
			while (!q.isEmpty())
			{
				Box b = q.poll();
				
				if (b.xi > 0)
				{
					Box left = boxes[b.xi-1][b.yi][b.zi];
					if (left.s == BoxStatus.OTHER)
					{
						left.s = BoxStatus.OUTSIDE;
						q.add(left);
					}
				}
				if (b.xi < xn-1)
				{
					Box right = boxes[b.xi+1][b.yi][b.zi];
					if (right.s == BoxStatus.OTHER)
					{
						right.s = BoxStatus.OUTSIDE;
						q.add(right);
					}
				}
				
				if (b.yi > 0)
				{
					Box front = boxes[b.xi][b.yi-1][b.zi];
					if (front.s == BoxStatus.OTHER)
					{
						front.s = BoxStatus.OUTSIDE;
						q.add(front);
					}
				}
				if (b.yi < yn-1)
				{
					Box back = boxes[b.xi][b.yi+1][b.zi];
					if (back.s == BoxStatus.OTHER)
					{
						back.s = BoxStatus.OUTSIDE;
						q.add(back);
					}
				}
				
				if (b.zi > 0)
				{
					Box bottom = boxes[b.xi][b.yi][b.zi-1];
					if (bottom.s == BoxStatus.OTHER)
					{
						bottom.s = BoxStatus.OUTSIDE;
						q.add(bottom);
					}
				}
				if (b.zi < zn-1)
				{
					Box top = boxes[b.xi][b.yi][b.zi+1];
					if (top.s == BoxStatus.OTHER)
					{
						top.s = BoxStatus.OUTSIDE;
						q.add(top);
					}
				}
			}
			
			long volume = 1000*1000*1000;
			long area = 0;
			
			for (int xi = 0; xi < xn; xi++)
			{
				for (int yi = 0; yi < yn; yi++)
				{
					for (int zi = 0; zi < zn; zi++)
					{
						Box b = boxes[xi][yi][zi];
						
						if (b.s == BoxStatus.OUTSIDE)
						{
							volume -= b.volume();
							
							if (b.xi > 0)
							{
								Box left = boxes[b.xi-1][b.yi][b.zi];
								if (left.s == BoxStatus.FILLED)
								{
									area += (b.y2-b.y1)*(b.z2-b.z1);
								}
							}
							if (b.xi < xn-1)
							{
								Box right = boxes[b.xi+1][b.yi][b.zi];
								if (right.s == BoxStatus.FILLED)
								{
									area += (b.y2-b.y1)*(b.z2-b.z1);
								}
							}
							
							if (b.yi > 0)
							{
								Box front = boxes[b.xi][b.yi-1][b.zi];
								if (front.s == BoxStatus.FILLED)
								{
									area += (b.x2-b.x1)*(b.z2-b.z1);
								}
							}
							if (b.yi < yn-1)
							{
								Box back = boxes[b.xi][b.yi+1][b.zi];
								if (back.s == BoxStatus.FILLED)
								{
									area += (b.x2-b.x1)*(b.z2-b.z1);
								}
							}
							
							if (b.zi > 0)
							{
								Box bottom = boxes[b.xi][b.yi][b.zi-1];
								if (bottom.s == BoxStatus.FILLED)
								{
									area += (b.x2-b.x1)*(b.y2-b.y1);
								}
							}
							if (b.zi < zn-1)
							{
								Box top = boxes[b.xi][b.yi][b.zi+1];
								if (top.s == BoxStatus.FILLED)
								{
									area += (b.x2-b.x1)*(b.y2-b.y1);
								}
							}
						}												
					}
				}
			}
			
			if (c > 0)
			{
				System.out.println();
			}
			System.out.print(area + " " +volume);
			
		}
	}
	
	public enum BoxStatus {
	    OUTSIDE, FILLED, OTHER
	}
	
	private static class Box
	{
		int xi, yi, zi,
			x1, y1, z1,
			x2, y2, z2;
		
		BoxStatus s = BoxStatus.OTHER;
		
		public String toString()
		{
			return s.toString();
		}
		
		public Box(int xi, int yi, int zi, int x1, int y1, int z1, int x2, int y2, int z2)
		{
			this.xi = xi;
			this.yi = yi;
			this.zi = zi;
		
			this.x1 = x1;
			this.y1 = y1;
			this.z1 = z1;
			this.x2 = x2;
			this.y2 = y2;
			this.z2 = z2;
		}
		
		public long volume()
		{
			return (x2-x1)*(y2-y1)*(z2-z1);
		}
	}

}
