import java.util.ArrayList;
import java.util.Scanner;

public class CarlTheAnt2 {

	private static int xOff = 100, yOff = 100;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int numCases = in.nextInt();
		for (int tc = 1; tc <= numCases; tc++)
		{
			int n = in.nextInt(), m = in.nextInt(), d = in.nextInt();
			Point[][] pts = new Point[201][201];
			pts[100][100] = new Point(100,100);

			int 	x1 = 0 + xOff, y1 = 0 + yOff,
					x2, 	y2;

			for (int i = 0; i < n; i++)
			{
				x2 = in.nextInt() + xOff;
				y2 = in.nextInt() + yOff;

				int 	xDiff = x2 - x1,
						yDiff = y2 - y1;
				
				if (xDiff == 0)
				{
					int step = (yDiff > 0) ? 1 : -1;
					
					for (int j = 0; j*step != yDiff; j++)
					{
						int		oldY = y1+j*step,
								newY = y1+(j+1)*step;
						
						Point 	p1 = pts[x1][oldY],
								p2 = pts[x1][newY];
						
						if (p2 == null)
						{
							p2 = new Point(x1, newY);
							pts[x1][newY] = p2;
						}
						if (p1 == null)
						{
							p1 = new Point(x1, oldY);
							pts[x1][oldY] = p1;
						}

						if (p1.p1 == null)
						{
							p1.p1 = p2;
						}
						else
						{
							p1.p2 = p2;
						}

						if (p2.p3 == null)
						{
							p2.p3 = p1;
						}
						else
						{
							p2.p4 = p1;
						}
					}
				}
				else
				{
					int step = (xDiff > 0) ? 1 : -1;
					
					for (int j = 0; j*step != xDiff; j++)
					{
						int		oldX = x1+j*step,
								newX = x1+(j+1)*step;
						
						Point 	p1 = pts[oldX][y1],
								p2 = pts[newX][y1];
						
						if (p2 == null)
						{
							p2 = new Point(newX, y1);
							pts[newX][y1] = p2;
						}
						if (p1 == null)
						{
							p1 = new Point(oldX, y1);
							pts[oldX][y1] = p1;
						}

						if (p1.p1 == null)
						{
							p1.p1 = p2;
						}
						else
						{
							p1.p2 = p2;
						}

						if (p2.p3 == null)
						{
							p2.p3 = p1;
						}
						else
						{
							p2.p4 = p1;
						}
					}
				}

				x1 = x2;
				y1 = y2;
			}

			ArrayList<Integer> finishOrder = new ArrayList<Integer>();
			int t = 0;
			int antNum = 0;
			ArrayList<Ant> ants = new ArrayList<Ant>();
			int carlTime = -1, lastTime = -1;

			while (finishOrder.size() < m)
			{
				boolean somethingBlocked = false;
				boolean[] isBlocked = new boolean[m];
				for (int i = 0; i < ants.size(); i++)
				{
					Ant a1 = ants.get(i);
					Point p1 = pts[a1.x][a1.y];
					for (int j = i+1; j < ants.size(); j++)
					{						
						Ant a2 = ants.get(j);
						Point p2 = pts[a2.x][a2.y];
						Point 	next1 = p1.nextPoint(),
								next2 = p2.nextPoint();

						//check if a1 and a2 are going to same pt
						if (next1 != null && next2 != null && next1.equals(next2))
						{
							somethingBlocked = true;
							if (a1.wait > a2.wait)
							{
								isBlocked[a2.num] = true;
							}
							else if (a2.wait > a1.wait)
							{
								isBlocked[a1.num] = true;
							}
							else if (a1.num < a2.num)
							{
								isBlocked[a2.num] = true; 
							}
							else if (a2.num < a1.num)
							{
								isBlocked[a1.num] = true; 
							}
						}
					}
				}

				while (somethingBlocked)
				{
					somethingBlocked = false;

					for (int i = 0; i < ants.size(); i++)
					{
						Ant a1 = ants.get(i);
						if (isBlocked[a1.num])
						{
							Point p = pts[a1.x][a1.y];
							Point 	p3 = p.p3,
									p4 = p.p4;

							if (p3 != null)
							{
								if (p.equals(p3.nextPoint()))
								{
									for (Ant a : p3.as)
									{
										if (!isBlocked[a.num])
										{
											somethingBlocked = true;
										}

										isBlocked[a.num] = true;
									}
								}
							}
							if (p4 != null)
							{
								if (p.equals(p4.nextPoint()))
								{
									for (Ant a : p4.as)
									{
										if (!isBlocked[a.num])
										{
											somethingBlocked = true;
										}
	
										isBlocked[a.num] = true;
									}
								}
							}
						}
					}
				}

				Point carlPoint = null;
				for (int i = 0; i < ants.size(); i++)
				{
					Ant a = ants.get(i);
					Point p = pts[a.x][a.y];

					if (isBlocked[a.num])
					{
						a.wait++;
					}
					else
					{
						a.wait = 0;
						p.as.remove(a);

						Point newP = p.nextPoint();
						if (newP == null)
						{
							ants.remove(a);
							i--;

							finishOrder.add(a.num);

							if (a.num == 0)
							{
								carlTime = t;
							}
							if (finishOrder.size() == m)
							{
								lastTime = t;
							}
						}
						else
						{
							newP.as.add(a);
							a.x = newP.x;
							a.y = newP.y;

							if (a.num == 0)
							{
								carlPoint = newP;
							}
						}
					}
				}

				if (carlPoint != null)
				{
					carlPoint.carlVisits++;
				}

				if (antNum < m && t % d == 0)
				{
					Ant a = new Ant(antNum++);
					ants.add(a);
					Point p = pts[100][100];
					p.as.add(a);
				}

//				System.out.print("@" + t + ": ");
//				for (Ant a : ants)
//				{
//					System.out.print(a.num + " - " + (a.x-xOff) + "," + (a.y-yOff) + " ");
//				}
//				System.out.println();

				t++;
			}

			System.out.println("Case " + tc + ":");
			System.out.println("Carl finished the path at time " + carlTime);
			System.out.println("The ants finished in the following order:");
			String s = "";
			for (Integer i : finishOrder)
			{
				s += i + " ";
			}
			s = s.substring(0, s.length()-1);
			System.out.println(s);
			System.out.println("The last ant finished the path at time " + lastTime);
			if (tc != numCases)
			{
				System.out.println();
			}
		}
	}

	private static class Ant
	{
		int num, wait;
		int x, y;

		public Ant(int n)
		{
			x = 0 + xOff;
			y = 0 + yOff;
			num = n;
			wait = 0;
		}
	}

	private static class Point
	{
		int x, y;
		Point p1, p2;
		Point p3, p4;
		int carlVisits;
		ArrayList<Ant> as;

		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
			carlVisits = 0;
			as = new ArrayList<Ant>();
		}

		public Point nextPoint()
		{
			if (carlVisits > 1)
			{
				return p2;
			}
			return p1;
		}

		public boolean equals(Object o)
		{
			if (o == null)
				return false;
			
			if (o instanceof Point)
			{
				Point p = ((Point) o);
				return x == p.x && y == p.y;
			}
			return false;
		}
	}
}
