import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Dungeon {

	static int L, R, C;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		while (true) {
			L = in.nextInt();
			R = in.nextInt();
			C = in.nextInt();

			if (L == 0 && R == 0 && C == 0) {
				break;
			}

			char[][][] map = new char[L][R][C];
			int start = 0;

			for (int k = 0; k < L; k++) {
				in.nextLine();
				for (int i = 0; i < R; i++) {
					char[] line = in.nextLine().toCharArray();
					for (int j = 0; j < C; j++) {

						if (line[j] == 'S') {
							start = getx(i, j, k);
						}

						map[k][i][j] = line[j];
					}

				}
			}

			solve(map, start);

		}
	}

	public static int level(int x) {
		return (x / (R * C));
	}

	public static int row(int x) {
		x = x % (R * C);

		return x / C;

	}

	public static int col(int x) {
		x = x % (R * C);

		return x % C;
	}

	public static int getx(int r, int c, int l) {
		return (R * C) * l + (r * C) + c;
	}

	public static void solve(char[][][] map, int start) {

		int[] dist = new int[R * C * L];
		boolean[] added = new boolean[R * C * L];

		Queue<Integer> q = new ArrayDeque<Integer>();

		q.add(start);
		added[start] = true;

		while (!q.isEmpty()) {
			int cur = q.poll();
			int r = row(cur);
			int c = col(cur);
			int l = level(cur);
//			System.out.printf("%d - %d %d %d%n",cur, r, c, l);

			if (map[l][r][c] == 'E') {
				System.out.println("Escaped in " + dist[cur] + " minute(s).");
				return;
			}

			// South
			if (valid(r + 1, c, l, map) && !added[getx(r + 1, c, l)]) {
				int x = getx(r + 1, c, l);
				q.add(x);
				dist[x] = dist[cur] + 1;
				added[x] = true;
			}
			// North
			if (valid(r - 1, c, l, map) && !added[getx(r - 1, c, l)]) {
				int x = getx(r - 1, c, l);
				q.add(x);
				dist[x] = dist[cur] + 1;
				added[x] = true;
			}
			// East
			if (valid(r, c + 1, l, map) && !added[getx(r, c + 1, l)]) {
				int x = getx(r, c + 1, l);
				q.add(x);
				dist[x] = dist[cur] + 1;
				added[x] = true;
			}
			// West
			if (valid(r, c - 1, l, map) && !added[getx(r, c - 1, l)]) {
				int x = getx(r, c - 1, l);
				q.add(x);
				dist[x] = dist[cur] + 1;
				added[x] = true;
			}
			// Up
			if (valid(r, c, l + 1, map) && !added[getx(r, c, l + 1)]) {
				int x = getx(r, c, l + 1);
				q.add(x);
				dist[x] = dist[cur] + 1;
				added[x] = true;
			}
			// Down
			if (valid(r, c, l - 1, map) && !added[getx(r, c, l - 1)]) {
				int x = getx(r, c, l - 1);
				q.add(x);
				dist[x] = dist[cur] + 1;
				added[x] = true;
			}

		}
		
		System.out.println("Trapped!");

	}

	public static boolean valid(int r, int c, int l, char[][][] map) {
		if (r < 0 || r >= R)
			return false;
		if (c < 0 || c >= C)
			return false;
		if (l < 0 || l >= L)
			return false;
		if (map[l][r][c] == '#')
			return false;
		
		return true;
	}

	public static void printMap(char[][] map) {
		System.out.println("----------");
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println();

	}

}
