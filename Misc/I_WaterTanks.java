import java.util.Scanner;

/**
 * Solution to ACM-ICPC WF 2007 Problem I - Water Tanks
 * 
 * @author Matt Dallmeyer
 */

public class I_WaterTanks {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int tc = 0;
		while (true) {
			tc++;
			int N = in.nextInt();
			if (N == 0)
				break;

			double[] h = new double[N], k = new double[N];
			double hSum = 0;

			for (int i = 0; i < N; i++) {
				h[i] = in.nextDouble();
				hSum += h[i];
			}
			for (int i = 1; i < N; i++) {
				k[i] = in.nextDouble();
			}

			double totVol = h[0];
			double P1 = 1, V1, P2, V2, PW;
			tankLoop: for (int i = 1; i < N; i++) {
				hSum -= h[i - 1];
				V1 = hSum - k[i];

				if (i == N - 1) {
					double A = 0.097, B = -1 - 0.097 * h[i] - 0.097 * h[0], C = h[i]
							+ 0.097 * h[i] * h[0] + k[i] * P1 - h[i] * P1;

					double[] water_i = quadratic(A, B, C);
					totVol += Math.min(water_i[0], water_i[1]);
					break tankLoop;
				}

				// compute water and air pressure if we could
				// fill tank i and i+1 to height k[i+1].
				V2 = hSum - 2 * k[i + 1];
				P2 = P1 * V1 / V2;
				PW = 1 + 0.097 * (h[0] - k[i + 1]);

				// air pressure higher means we compressed too much
				if (PW < P2) {
					// compute water and air pressure if we could
					// fill tank i to height k[i+1]
					V2 = hSum - k[i + 1];
					P2 = P1 * V1 / V2;

					// air pressure higher means we compressed too much
					if (PW < P2) // no water reaches next tank (case 1)
					{
						double A = 0.097, B = -1 - 0.097 * hSum - 0.097 * h[0], C = hSum
								+ 0.097 * hSum * h[0] + k[i] * P1 - hSum * P1;

						double[] water_i = quadratic(A, B, C);
						totVol += Math.min(water_i[0], water_i[1]);
					} else if (PW == P2) // water just reaches pipe to next tank
					{
						totVol += k[i + 1];
					} else // PW > P2 water flows into next tank (case 2)
					{
						double x = hSum - P1 * (hSum - k[i]) / PW;
						totVol += x;
					}

					break tankLoop;
				}
				// equal pressure means we compressed perfectly
				else if (PW == P2) // water fills to k[i+1] in both pipes
				{
					totVol += k[i + 1] * 2;
					break tankLoop;
				}
				// water pressure higher means we can compress more
				else // water flows into next tank (case 3)
				{
					double A = 0.097, B = -1 - 0.097 * h[i] - 0.097 * h[0], C = h[i]
							+ 0.097 * h[i] * h[0] + k[i + 1] * P2 - h[i] * P2;

					double[] water_i = quadratic(A, B, C);
					totVol += Math.min(water_i[0], water_i[1]);

					P1 = P2;
				}
			}

			System.out.printf("Case %d: %.3f\n\n", tc, totVol);
		}
	}

	private static double[] quadratic(double A, double B, double C) {
		double[] ans = new double[2];
		double sqrt = Math.sqrt(B * B - 4 * A * C);
		ans[0] = (-B + sqrt) / (2 * A);
		ans[1] = (-B - sqrt) / (2 * A);
		return ans;
	}

}
