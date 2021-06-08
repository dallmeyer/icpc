import java.awt.geom.Line2D;
import java.util.Scanner;

public class Torches {

	public static void main(String[] arsg) {
		Scanner in = new Scanner(System.in);

		int N = in.nextInt();
		int M = in.nextInt();

		Beacon[] b = new Beacon[N];
		Peak[] p = new Peak[M];
		DisjointSet set = new DisjointSet(N);

		for (int i = 0; i < N; i++) {
			b[i] = new Beacon(in.nextInt(), in.nextInt());
		}
		for (int i = 0; i < M; i++) {
			p[i] = new Peak(in.nextInt(), in.nextInt(), in.nextInt());
		}

		if (N == 1 || M == 0) {
			System.out.println(0);
			return;
		}

		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				if (set.connected(i, j)) {
					continue;
				}

				boolean connected = true;
				for (int k = 0; k < M; k++) {
					if (doesIntersectTwo(b[i], b[j], p[k])) {
						connected = false;
						break;
					}

				}

				if (connected) {
					set.union(i, j);
				}
			}
		}

		System.out.println(set.N - 1);

	}

	
	public static boolean doesIntersectTwo(Beacon one, Beacon two, Peak p) {

		double dist = Line2D.ptSegDistSq(one.x, one.y, two.x, two.y, p.x, p.y);

		return p.r * p.r > dist;

	}

	public static boolean doesIntersect(Beacon one, Beacon two, Peak p) {
		if (two.x == one.x) {
			double xInt = one.x;
			double yInt = p.y;

			double dist = (p.x - xInt) * (p.x - xInt) + (p.y - yInt)
					* (p.y - yInt);

			return p.r * p.r > dist;
		}
		if (two.y == one.y) {
			double xInt = p.x;
			double yInt = one.y;

			double dist = (p.x - xInt) * (p.x - xInt) + (p.y - yInt)
					* (p.y - yInt);

			return p.r * p.r > dist;
		}

		double m1 = (two.y - one.y) / (two.x - one.x);
		double b1 = one.y - m1 * one.x;

		double m2 = -1.0 / m1;
		double b2 = p.y - m2 * p.x;
		double xInt = (b2 - b1) / (m1 - m2);
		double yInt = m2 * xInt + b2;

		double dist = (p.x - xInt) * (p.x - xInt) + (p.y - yInt) * (p.y - yInt);

		return p.r * p.r > dist;
	}

	public static class Beacon {
		double x, y;

		public Beacon(double x, double y) {
			this.x = x;
			this.y = y;
		}

	}

	public static class Peak {
		double x, y, r;

		public Peak(double x, double y, double r) {
			this.x = x;
			this.y = y;
			this.r = r;
		}

	}

	public static class DisjointSet {
		int[] id;
		int[] sz;
		int N;

		public DisjointSet(int N) {
			this.N = N;
			id = new int[N];
			sz = new int[N];
			for (int i = 0; i < N; i++) {
				id[i] = i;
				sz[i] = 1;
			}
		}

		public int find(int p) {
			int root = p;
			while (root != id[root])
				root = id[root];
			while (p != root) {
				int newp = id[p];
				id[p] = root;
				p = newp;
			}
			return root;
		}

		public void union(int p, int q) {
			int i = find(p);
			int j = find(q);
			if (i == j)
				return;
			if (sz[i] < sz[j]) {
				id[i] = j;
				sz[j] += sz[i];
			} else {
				id[j] = i;
				sz[i] += sz[j];
			}
			N--;
		}

		public boolean connected(int p, int q) {
			return find(p) == find(q);
		}
	}

}
