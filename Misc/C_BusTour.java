import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class C_BusTour {

	private static int N, M, H, first, second;
	private static int[][] dist;
	private static int MIN;
	private static int INFIN = Integer.MAX_VALUE / 4;
	private static TSP forward, backward;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int tc = 0;
		while (in.hasNext()) {
			tc++;
			MIN = INFIN;

			N = in.nextInt();
			M = in.nextInt();

			H = N - 2;
			first = (int) Math.floor(((int) H) / 2);
			second = H - first;

			dist = new int[N][N];

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					dist[i][j] = INFIN;
				}
				dist[i][i] = 0;
			}

			for (int i = 0; i < M; i++) {
				int u = in.nextInt(), v = in.nextInt(), t = in.nextInt();

				dist[u][v] = t;
				dist[v][u] = t;
			}

			for (int k = 0; k < N; k++) {
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						if (dist[i][k] + dist[k][j] < dist[i][j]) {
							dist[i][j] = dist[i][k] + dist[k][j];
						}
					}
				}
			}

			int[] forwardIC = new int[N];
			Arrays.fill(forwardIC, INFIN);
			forwardIC[0] = 0;
			forward = new TSP(N, dist, forwardIC);

			int[] backwardIC = new int[N];
			Arrays.fill(backwardIC, INFIN);
			backwardIC[N - 1] = 0;
			backward = new TSP(N, dist, backwardIC);

			solve(new ArrayList<Integer>(), new ArrayList<Integer>(), 1);

			System.out.println("Case " + tc + ": " + MIN);
		}
	}

	private static void solve(ArrayList<Integer> used,
			ArrayList<Integer> notUsed, int next) {
		if (used.size() < first) {
			used.add(next);
			solve(used, notUsed, next + 1);
			used.remove(Integer.valueOf(next));
		}
		if (notUsed.size() < second) {
			notUsed.add(next);
			solve(used, notUsed, next + 1);
			notUsed.remove(Integer.valueOf(next));
		}
		if (used.size() == first && notUsed.size() == second) {
			int b1 = 0, b2 = 0, b3 = 0, b4 = 0;

			b1 |= 1 << 0;
			b3 |= 1 << (N - 1);
			for (int i = 0; i < first; i++) {
				b1 |= 1 << used.get(i);
				b3 |= 1 << used.get(i);
			}

			b2 |= 1 << (N - 1);
			b4 |= 1 << 0;
			for (int i = 0; i < second; i++) {
				b2 |= 1 << notUsed.get(i);
				b4 |= 1 << notUsed.get(i);
			}

			int totDist = 0;
			if (first == 0)
			{
				totDist += dist[0][1];
				totDist += backward.dp[b2][1];
				totDist += dist[N-1][1];
				totDist += forward.dp[b4][1];
				
				if (totDist < MIN) {
					MIN = totDist;
				}
				return;
			}

			int mid1 = INFIN, mid2 = INFIN;
			for (int i = 0; i < first; i++) {
				int trueI = used.get(i);
				for (int j = 0; j < second; j++) {
					int trueJ = notUsed.get(j);
					int temp1 = forward.dp[b1][trueI];

					temp1 += dist[trueI][trueJ];
					temp1 += backward.dp[b2][trueJ];

					if (temp1 < mid1) {
						mid1 = temp1;
					}

					int temp2 = backward.dp[b3][trueI];

					temp2 += dist[trueI][trueJ];
					temp2 += forward.dp[b4][trueJ];

					if (temp2 < mid2) {
						mid2 = temp2;
					}
				}
			}

			totDist = mid1 + mid2;
			if (totDist < MIN) {
				MIN = totDist;
			}
		}
	}

	private static class TSP {
		int n;
		int[][] dist;
		int[] initDist;
		int[][] dp;

		public TSP(int n, int[][] dist, int[] initDist) {
			this.n = n;
			this.dist = dist;
			this.initDist = initDist;
			solve();
		}

		public void solve() {
			final int[][] D = new int[n][n];
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					D[j][i] = dist[j][i];

			dp = new int[1 << n][n];
			for (int[] row : dp)
				Arrays.fill(row, INFIN);

			for (int i = 0; i < n; i++)
				dp[1 << i][i] = initDist[i];

			for (int mask = 0; mask < 1 << n; mask++)
				for (int i = 0; i < n; i++)
					if ((mask & 1 << i) > 0)
						for (int j = 0; j < n; j++)
							dp[mask][i] = Math.min(dp[mask][i], dp[mask
									^ (1 << i)][j]
									+ D[j][i]);
		}
	}

}
