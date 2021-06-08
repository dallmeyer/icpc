import java.util.ArrayList;
import java.util.Scanner;

public class I_SensorNetwork {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		double d = in.nextDouble();

		P[] pts = new P[n];
		for (int i = 0; i < n; i++) {
			pts[i] = new P(in.nextInt(), in.nextInt());
		}

		ArrayList<Integer> maxList = new ArrayList<Integer>();
		maxList.add(1);
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (pts[i].dist(pts[j]) > d) {
					continue;
				}
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(i + 1);
				list.add(j + 1);
				if (list.size() > maxList.size()) {
					maxList = list;
				}

				for (int k = j + 1; k < n; k++) {
					if (pts[i].dist(pts[k]) > d || pts[j].dist(pts[k]) > d) {
						continue;
					}

					ArrayList<Integer> three_list = new ArrayList<Integer>(list);
					three_list.add(k + 1);
					for (int m = k + 1; m < n; m++) {
						if (pts[i].dist(pts[m]) <= d
								&& pts[j].dist(pts[m]) <= d
								&& pts[k].dist(pts[m]) <= d) {
							three_list.add(m + 1);
						}
					}
					if (three_list.size() > maxList.size()) {
						maxList = three_list;
					}
				}
			}
		}

		System.out.println(maxList.size());
		for (int i : maxList)
		{
			System.out.print(i + " ");
		}
	}

	private static class P {
		double x, y;

		public P(double x, double y) {
			this.x = x;
			this.y = y;
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
	}

}
