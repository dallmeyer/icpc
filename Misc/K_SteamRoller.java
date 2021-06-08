import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

public class K_SteamRoller {

	private static int[] deltaR = { -1, 0, 1, 0 }, deltaC = { 0, 1, 0, -1 };
	private static int R, C;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int tc = 0;
		while (true) {
			tc++;

			R = in.nextInt();
			C = in.nextInt();
			int r1 = in.nextInt(), c1 = in.nextInt(), r2 = in.nextInt(), c2 = in
					.nextInt();

			if (R + C + r1 + c1 + r2 + c2 == 0)
				break;

			r1--;
			c1--;
			r2--;
			c2--;

			long[][][] d = new long[R][C][4];

			for (int i = 0; i < R * 2 - 1; i++) {
				if (i % 2 == 0) // left-to-right dists
				{
					for (int j = 0; j < C - 1; j++) {
						int dd = in.nextInt();
						d[i / 2][j][1] = dd;
						d[i / 2][j + 1][3] = dd;
					}
				} else {
					for (int j = 0; j < C; j++) {
						int dd = in.nextInt();
						d[i / 2][j][2] = dd;
						d[i / 2 + 1][j][0] = dd;
					}
				}
			}

			long[][][] sp = new long[R][C][5];
			for (int i = 0; i < R; i++) {
				for (int j = 0; j < C; j++) {
					Arrays.fill(sp[i][j], Long.MAX_VALUE);
				}
			}

			sp[r1][c1][4] = 0;

			Queue<State> q = new ArrayDeque<State>();
			q.add(new State(r1, c1, 4));
			while (!q.isEmpty()) {
				State cur = q.poll();
				for (int i = 0; i < 4; i++) {
					// if road in direction i can be traveled
					if (d[cur.r][cur.c][i] != 0) {
						long dist = d[cur.r][cur.c][i];
						// if going in same direction
						if (cur.s == i) {
							if (sp[cur.r][cur.c][cur.s] + dist < sp[cur.r
									+ deltaR[i]][cur.c + deltaC[i]][i]
									&& notIntoWall(cur.r, cur.c, i)) {
								sp[cur.r + deltaR[i]][cur.c + deltaC[i]][i] = sp[cur.r][cur.c][cur.s]
										+ dist;
								q.add(new State(cur.r + deltaR[i], cur.c
										+ deltaC[i], i));
							}
							dist *= 2;
							if (sp[cur.r][cur.c][cur.s] + dist < sp[cur.r
									+ deltaR[i]][cur.c + deltaC[i]][4]) {
								sp[cur.r + deltaR[i]][cur.c + deltaC[i]][4] = sp[cur.r][cur.c][cur.s]
										+ dist;
								q.add(new State(cur.r + deltaR[i], cur.c
										+ deltaC[i], 4));
							}
						} else if (cur.s == 4) {
							dist *= 2;
							if (sp[cur.r][cur.c][cur.s] + dist < sp[cur.r
									+ deltaR[i]][cur.c + deltaC[i]][i]) {
								sp[cur.r + deltaR[i]][cur.c + deltaC[i]][i] = sp[cur.r][cur.c][cur.s]
										+ dist;
								q.add(new State(cur.r + deltaR[i], cur.c
										+ deltaC[i], i));
							}
							if (sp[cur.r][cur.c][cur.s] + dist < sp[cur.r
									+ deltaR[i]][cur.c + deltaC[i]][4]) {
								sp[cur.r + deltaR[i]][cur.c + deltaC[i]][4] = sp[cur.r][cur.c][cur.s]
										+ dist;
								q.add(new State(cur.r + deltaR[i], cur.c
										+ deltaC[i], 4));
							}
						}
					}
				}
			}

			if (sp[r2][c2][4] < Long.MAX_VALUE) {
				System.out.println("Case " + tc + ": " + sp[r2][c2][4]);
			} else {
				System.out.println("Case " + tc + ": Impossible");
			}
		}
	}

	private static boolean notIntoWall(int r, int c, int i) {
		if (i == 0 || i == 2) // up or down
		{
			return (r + deltaR[i] > 0 && r + deltaR[i] < R - 1);
		} else {
			return (c + deltaC[i] > 0 && c + deltaC[i] < C - 1);
		}
	}

	private static class State {
		int r, c, s;

		public State(int r, int c, int s) {
			this.r = r;
			this.c = c;
			this.s = s;
		}
	}

}
