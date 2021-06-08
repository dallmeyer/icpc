import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class SensorRandom {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		double d = in.nextDouble();

		P[] pts = new P[n];
		for (int i = 0; i < n; i++) {
			pts[i] = new P(in.nextInt(), in.nextInt());
		}

		BitSet[] inRange = new BitSet[n];
		for (int i = 0; i < n; i++) {
			inRange[i] = new BitSet(n);
			inRange[i].set(i);
		}

		int high = 1;
		ArrayList<Integer>[] sizes = new ArrayList[n + 1];
		BitSet low = new BitSet(n);
		low.set(0);

		for (int i = 0; i <= n; i++) {
			sizes[i] = new ArrayList<Integer>();

			for (int j = i + 1; j < n; j++) {
				double dist = pts[i].dist(pts[j]);
				if (dist <= d) {
					inRange[i].set(j);
					inRange[j].set(i);
					if (low.cardinality() < 2) {
						low = new BitSet(n);
						low.set(i);
						low.set(j);
					}
				}
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 1; j <= inRange[i].cardinality(); j++) {
				sizes[j].add(i);
			}
		}

		for (int i = n; i > 1; i--) {
			if (sizes[i].size() >= i) {
				high = i;
				break;
			}
		}

		Random gen = new Random();
		int runs = 0;
		int maxRuns = 10000000 / (n * n);
		HashSet<BitSet>[] seen = new HashSet[n + 1];
		while (low.cardinality() < high && runs++ < maxRuns) {
//			int size = low.cardinality() < high - 1 ? gen.nextInt(high
//					- low.cardinality() - 1)
//					+ low.cardinality() + 1 : low.cardinality() + 1;
			int size = low.cardinality() + 1;
			ArrayList<Integer> ofSize = sizes[size];
			if (seen[size] == null) {
				seen[size] = new HashSet<BitSet>();
			}

			BitSet testAns = new BitSet(n);
			while (testAns.cardinality() < size) {
				int ind = gen.nextInt(ofSize.size());
				testAns.set(ofSize.get(ind));
			}

			if (seen[size].contains(testAns)) {
				continue;
			}
			seen[size].add(testAns);

			BitSet andTest = (BitSet) testAns.clone();

			for (int i = 0; i < n; i++) {
				if (testAns.get(i)) {
					andTest.and(inRange[i]);
				}
				if (!andTest.equals(testAns)) {
					break;
				}
			}

			if (andTest.equals(testAns)) {
				low = andTest;
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (!inRange[i].get(j) || inRange[i].cardinality() < low.cardinality() || inRange[j].cardinality() < low.cardinality()) {
					continue;
				}

				BitSet[] testRange = checkRanges(i, j, pts, d, n);
				for (int k = 0; k < 4; k++)
				{
					if (testRange[k].cardinality() > low.cardinality()) {
						low = testRange[k];
					}
				}
			}
		}
		
		System.out.println(low.cardinality());
		for (int i = 0; i < n; i++) {
			if (low.get(i)) {
				System.out.print((i + 1) + " ");
			}
		}
	}

	private static class P {
		double x, y;

		public P(double x, double y) {
			this.x = x;
			this.y = y;
		}

		P add(P that) {
			return new P(x + that.x, y + that.y);
		}

		P sub(P that) {
			return new P(x - that.x, y - that.y);
		}

		double length() {
			return Math.sqrt(x * x + y * y);
		}

		double dist(P to) {
			return sub(to).length();
		}

		double dot(P to) {
			return x * to.x + y * to.y;
		}

		P scale(double l) {
			return normalize().mult(l);
		}

		P normalize() {
			double n = length();
			return n > 0 ? new P(x / n, y / n) : new P(0, 0);
		}

		P mult(double l) {
			return new P(x * l, y * l);
		}

		double angleTo(P to) {
			return Math.acos(this.dot(to) / this.length() / to.length());
		}
	}


	private static BitSet[] checkRanges(int i, int j, P[] pts, double d, int n) {
		P p1 = new P(0, 0);
		P p2 = pts[j].sub(pts[i]);

		double X = p2.x - p1.x, Y = p2.y - p1.y, R = X * X + Y * Y;

		double a = 4 * R, b = -4 * R * X, c = R * R - 4 * Y * Y * d * d;

		double p3x = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a), p3y = (R - 2
				* X * p3x)
				/ (2 * Y), p4x = (-b - Math.sqrt(b * b - 4 * a * c)) / (2 * a), p4y = (R - 2
				* X * p4x)
				/ (2 * Y);

		P p3 = new P(p3x, p3y), p4 = new P(p4x, p4y);

		double X1 = p3.x - p1.x, Y1 = p3.y - p1.y, R1 = X1 * X1 + Y1 * Y1;

		double a1 = 4 * R1, b1 = -4 * R1 * X1, c1 = R1 * R1 - 4 * Y1 * Y1 * d
				* d;

		double p5x = (-b1 + Math.sqrt(b1 * b1 - 4 * a1 * c1)) / (2 * a1), p5y = (R1 - 2
				* X1 * p5x)
				/ (2 * Y1), p6x = (-b1 - Math.sqrt(b1 * b1 - 4 * a1 * c1))
				/ (2 * a1), p6y = (R1 - 2 * X1 * p6x) / (2 * Y1);

		P p5 = new P(p5x + pts[i].x, p5y + pts[i].y), p6 = new P(
				p6x + pts[i].x, p6y + pts[i].y);

		double X2 = p4.x - p1.x, Y2 = p4.y - p1.y, R2 = X2 * X2 + Y2 * Y2;

		double a2 = 4 * R2, b2 = -4 * R2 * X2, c2 = R2 * R2 - 4 * Y2 * Y2 * d
				* d;

		double p7x = (-b2 + Math.sqrt(b2 * b2 - 4 * a2 * c2)) / (2 * a2), p7y = (R2 - 2
				* X2 * p7x)
				/ (2 * Y2), p8x = (-b2 - Math.sqrt(b2 * b2 - 4 * a2 * c2))
				/ (2 * a2), p8y = (R2 - 2 * X2 * p8x) / (2 * Y2);

		P p7 = new P(p7x + pts[i].x, p7y + pts[i].y), p8 = new P(
				p8x + pts[i].x, p8y + pts[i].y);
		
		p1 = pts[i];
		p3 = p3.add(pts[i]);
		p4 = p4.add(pts[i]);

		BitSet[] ans = new BitSet[4];
		ans[0] = new BitSet(n);
		ans[1] = new BitSet(n);
		ans[2] = new BitSet(n);
		ans[3] = new BitSet(n);
		for (int k = 0; k < n; k++) {
			double d1 = pts[k].dist(p1), d3 = pts[k].dist(p3), d4 = pts[k]
					.dist(p4), d5 = pts[k].dist(p5), d6 = pts[k].dist(p6), d7 = pts[k]
					.dist(p7), d8 = pts[k].dist(p8);

			if (d1 <= d) {
				if (d3 <= d) {
					if (d5 <= d) {
						ans[0].set(k);
					}
					if (d6 <= d) {
						ans[1].set(k);
					}
				}
				if (d4 <= d) {
					if (d7 <= d) {
						ans[2].set(k);
					}
					if (d8 <= d) {
						ans[3].set(k);
					}
				}
			}
		}

		return ans;
	}
	
}
