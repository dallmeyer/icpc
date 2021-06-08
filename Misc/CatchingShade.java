import java.awt.geom.Line2D;
import java.util.Scanner;

public class CatchingShade {

	public static void main(String[] args) {

		CatchingShade me = new CatchingShade();
		me.runAll();
	}

	Scanner in = new Scanner(System.in);

	public void runAll() {
		while (true) {
			int N = in.nextInt();
			if (N == 0) {
				return;
			}
			runOne(N);
		}
	}

	double[] treeR;
	double[] treeX;
	double[] treeY;

	public void runOne(int N) {

		treeR = new double[N];
		treeX = new double[N];
		treeY = new double[N];

		for (int i = 0; i < N; i++) {
			treeX[i] = in.nextInt();
			treeY[i] = in.nextInt();
			treeR[i] = in.nextInt();
		}

		double maxLen = -1;

		int maxMin = 24 * 60;
		for (int iMin = 0; iMin < maxMin; iMin++) {

			double theta = ((double) iMin) / maxMin * 2 * Math.PI;

			double thisLen = 0;
			for (int iTree = 0; iTree < N; iTree++) {
				thisLen += intersectLength(theta, treeX[iTree], treeY[iTree],
						treeR[iTree]);
			}

			//System.err.println("Angle " + iMin + " length " + thisLen);

			maxLen = Math.max(maxLen, thisLen);
		}

		System.out.printf("%.3f\n", maxLen);
	}

	public double intersectLength(double theta, double x0, double y0, double r) {

		double m = Math.tan(theta);

		double a = m * m + 1;
		double b = -2.0 * (x0 + m*y0);
		double c = x0 * x0 + y0 * y0 - r * r;

		double disc = b * b - 4.0 * a * c;
		if (disc <= 0) {
			return 0;
		}

		double sqDisc = Math.sqrt(disc);
		double xSol1 = (-b + sqDisc) / (2.0 * a);
		double xSol2 = (-b - sqDisc) / (2.0 * a);

		// System.err.println("---> xSol1 = " + xSol1 + " xSol2 = " + xSol2);

		// Make sure x intersection quadrants are correct
		if (((theta < Math.PI / 2.0) || (theta > 3.0 * Math.PI / 2.0))
				&& xSol1 < 0) {
			return 0;
		}
		if (((theta > Math.PI / 2.0) && (theta < 3.0 * Math.PI / 2.0))
				&& xSol1 > 0) {
			return 0;
		}

		double ySol1 = m * xSol1;
		double ySol2 = m * xSol2;

		// Make sure the y intersection quadrants are correct
		if (theta < Math.PI && ySol1 < 0) {
			return 0;
		}
		if (theta > Math.PI && ySol1 > 0) {
			return 0;
		}

		// This line intersects the tree in the proper quadrant, compute length
		return Math.sqrt((xSol1 - xSol2) * (xSol1 - xSol2) + (ySol1 - ySol2)
				* (ySol1 - ySol2));
	}

}
