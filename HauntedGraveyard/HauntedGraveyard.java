import java.util.ArrayList;
import java.util.Scanner;

public class HauntedGraveyard {

	private static int[] xx = { -1, 1, 0, 0 }, yy = { 0, 0, -1, 1 };

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int W = in.nextInt(), H = in.nextInt();

		while (W != 0 || H != 0) {
			Cell[][] grid = new Cell[W][H];
			for (int w = 0; w < W; w++) {
				for (int h = 0; h < H; h++) {
					grid[w][h] = new Cell();
				}
			}

			int G = in.nextInt();
			for (int g = 0; g < G; g++) {
				int x = in.nextInt(), y = in.nextInt();

				grid[x][y] = new Cell(true);
			}

			int E = in.nextInt();
			for (int e = 0; e < E; e++) {
				int x = in.nextInt(), y = in.nextInt(), toX = in.nextInt(), toY = in
						.nextInt(), T = in.nextInt();

				grid[x][y] = new Cell();
				grid[x][y].adjX.add(toX);
				grid[x][y].adjY.add(toY);
				grid[x][y].cost.add(T);
			}

			initGraph: for (int w = 0; w < W; w++) {
				for (int h = 0; h < H; h++) {
					if (w == W-1 && h== H-1)
						continue;
					
					Cell c = grid[w][h];
					if (!c.isHole && !c.isGravestone) {
						for (int i = 0; i < 4; i++) {
							int x = w + xx[i], y = h + yy[i];

							if (x >= 0 && x < W && y >= 0 && y < H) {
								if (!grid[x][y].isGravestone) {
									c.adjX.add(x);
									c.adjY.add(y);
									c.cost.add(1);
								}
							}
						}
					}
				}
			}

			boolean negCycle = false;
			grid[0][0].dist = 0;

			boolean changed = true;
			relaxEdges: for (int i = 0; i < W * H; i++) {
				if (!changed)
					break;
				changed = false;
				
				for (int x = 0; x < W; x++) {
					for (int y = 0; y < H; y++) {
						Cell c = grid[x][y];

						for (int e = 0; e < c.adjX.size(); e++) {
							Cell next = grid[c.adjX.get(e)][c.adjY.get(e)];
							int cost = c.cost.get(e);

							if (c.dist + cost < next.dist) {
								next.dist = c.dist + cost;
								next.pre = c;
								changed = true;
							}
						}
					}
				}
			}

			negCycleCheck: for (int x = 0; x < W; x++) {
				for (int y = 0; y < H; y++) {
					Cell c = grid[x][y];

					for (int e = 0; e < c.adjX.size(); e++) {
						Cell next = grid[c.adjX.get(e)][c.adjY.get(e)];
						int cost = c.cost.get(e);

						if (c.dist + cost < next.dist) {
							negCycle = true;
							break negCycleCheck;
						}
					}
				}
			}

			int minTime = grid[W - 1][H - 1].dist;

			if (negCycle || minTime == Integer.MIN_VALUE) {
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

	private static class Cell {
		boolean isGravestone, isHole;

		int dist;

		Cell pre;
		ArrayList<Integer> adjX;
		ArrayList<Integer> adjY;
		ArrayList<Integer> cost;

		public Cell() {
			isGravestone = false;
			isHole = false;
			dist = Integer.MAX_VALUE;
			pre = null;
			adjX = new ArrayList<Integer>();
			adjY = new ArrayList<Integer>();
			cost = new ArrayList<Integer>();
		}

		public Cell(boolean isStone) {
			this.isGravestone = true;
			this.isHole = false;
			dist = Integer.MAX_VALUE;
			pre = null;
			adjX = new ArrayList<Integer>();
			adjY = new ArrayList<Integer>();
			cost = new ArrayList<Integer>();
		}
	}

}
