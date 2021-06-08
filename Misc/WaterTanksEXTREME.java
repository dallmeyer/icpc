import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Scanner;

/**
 * Solution to ACM-ICPC WF 2007 Problem I - Water Tanks
 * 
 * @author Matt Dallmeyer
 */

public class WaterTanksEXTREME {

	private static BigDecimal Z = BigDecimal.valueOf(0.097);

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int tc = 0;
		while (true) {
			tc++;
			int N = in.nextInt();
			if (N == 0)
				break;

			BigDecimal[] h = new BigDecimal[N], k = new BigDecimal[N];
			BigDecimal hSum = BigDecimal.ZERO;

			for (int i = 0; i < N; i++) {
				h[i] = in.nextBigDecimal();
				hSum = hSum.add(h[i]);
			}
			for (int i = 1; i < N; i++) {
				k[i] = in.nextBigDecimal();
			}

			BigDecimal totVol = h[0];
			BigDecimal P1 = BigDecimal.ONE, V1, P2, V2, PW;
			tankLoop: for (int i = 1; i < N; i++) {
				hSum = hSum.subtract(h[i - 1], MathContext.DECIMAL128);
				V1 = hSum.subtract(k[i], MathContext.DECIMAL128);

				if (i == N - 1) {
					BigDecimal A = Z, B = BigDecimal
							.valueOf(-1)
							.subtract(Z.multiply(h[i], MathContext.DECIMAL128),
									MathContext.DECIMAL128)
							.subtract(Z.multiply(h[0], MathContext.DECIMAL128),
									MathContext.DECIMAL128), C = h[i]
							.add(Z.multiply(
									h[i].multiply(h[0], MathContext.DECIMAL128),
									MathContext.DECIMAL128),
									MathContext.DECIMAL128).add(
									k[i].subtract(h[i], MathContext.DECIMAL128)
											.multiply(P1,
													MathContext.DECIMAL128),
									MathContext.DECIMAL128);

					BigDecimal[] water_i = quadratic(A, B, C);
					totVol = totVol.add(
							(water_i[0].compareTo(water_i[1]) > 0) ? water_i[1]
									: water_i[0], MathContext.DECIMAL128);
					break tankLoop;
				}

				// compute water and air pressure if we could
				// fill tank i and i+1 to height k[i+1].
				V2 = hSum.subtract(k[i + 1].multiply(BigDecimal.valueOf(2),
						MathContext.DECIMAL128), MathContext.DECIMAL128);
				P2 = P1.multiply(V1, MathContext.DECIMAL128).divide(V2,
						MathContext.DECIMAL128);
				PW = BigDecimal.ONE.add(Z.multiply(
						h[0].subtract(k[i + 1], MathContext.DECIMAL128),
						MathContext.DECIMAL128), MathContext.DECIMAL128);

				int diff = PW.compareTo(P2);
				// air pressure higher means we compressed too much
				if (diff < 0) {
					// compute water and air pressure if we could
					// fill tank i to height k[i+1]
					V2 = hSum.subtract(k[i + 1], MathContext.DECIMAL128);
					P2 = P1.multiply(V1, MathContext.DECIMAL128).divide(V2,
							MathContext.DECIMAL128);

					diff = PW.compareTo(P2);
					// air pressure higher means we compressed too much
					if (diff < 0) // no water reaches next tank (case 1)
					{
						BigDecimal A = Z, B = BigDecimal
								.valueOf(-1)
								.subtract(Z.multiply(hSum),
										MathContext.DECIMAL128)
								.subtract(
										Z.multiply(h[0], MathContext.DECIMAL128),
										MathContext.DECIMAL128), C = hSum.add(
								Z.multiply(hSum.multiply(h[0],
										MathContext.DECIMAL128),
										MathContext.DECIMAL128),
								MathContext.DECIMAL128).add(
								k[i].subtract(hSum, MathContext.DECIMAL128)
										.multiply(P1, MathContext.DECIMAL128),
								MathContext.DECIMAL128);

						BigDecimal[] water_i = quadratic(A, B, C);
						totVol = totVol
								.add((water_i[0].compareTo(water_i[1]) > 0) ? water_i[1]
										: water_i[0], MathContext.DECIMAL128);
					} else if (diff == 0) // water just reaches pipe to next
											// tank
					{
						totVol = totVol.add(k[i + 1], MathContext.DECIMAL128);
					} else // PW > P2 water flows into next tank (case 2)
					{
						BigDecimal x = hSum.subtract(P1.multiply(
								hSum.subtract(k[i])).divide(PW,
								MathContext.DECIMAL128));
						totVol = totVol.add(x, MathContext.DECIMAL128);
					}

					break tankLoop;
				}
				// equal pressure means we compressed perfectly
				else if (diff == 0) // water fills to k[i+1] in both pipes
				{
					totVol = totVol.add(k[i + 1].multiply(
							BigDecimal.valueOf(2), MathContext.DECIMAL128),
							MathContext.DECIMAL128);
					break tankLoop;
				}
				// water pressure higher means we can compress more
				else // water flows into next tank (case 3)
				{
					BigDecimal A = Z, B = BigDecimal
							.valueOf(-1)
							.subtract(Z.multiply(h[i], MathContext.DECIMAL128),
									MathContext.DECIMAL128)
							.subtract(Z.multiply(h[0], MathContext.DECIMAL128),
									MathContext.DECIMAL128), C = h[i]
							.add(Z.multiply(
									h[i].multiply(h[0], MathContext.DECIMAL128),
									MathContext.DECIMAL128),
									MathContext.DECIMAL128).add(
									k[i + 1].subtract(h[i],
											MathContext.DECIMAL128).multiply(
											P2, MathContext.DECIMAL128),
									MathContext.DECIMAL128);

					BigDecimal[] water_i = quadratic(A, B, C);
					totVol = totVol.add(
							(water_i[0].compareTo(water_i[1]) > 0) ? water_i[1]
									: water_i[0], MathContext.DECIMAL128);

					P1 = P2;
				}
			}

			System.out.printf("Case %d: %.3f\n\n", tc, totVol);
		}
	}

	private static BigDecimal[] quadratic(BigDecimal A, BigDecimal B,
			BigDecimal C) {
		BigDecimal[] ans = new BigDecimal[2];
		BigDecimal sqrt = BigDecimal
				.valueOf(Math.sqrt(B
						.multiply(B, MathContext.DECIMAL128)
						.subtract(
								BigDecimal.valueOf(4).multiply(
										A.multiply(C, MathContext.DECIMAL128),
										MathContext.DECIMAL128),
								MathContext.DECIMAL128).doubleValue()));
		ans[0] = (sqrt.subtract(B, MathContext.DECIMAL128)).divide(BigDecimal
				.valueOf(2).multiply(A, MathContext.DECIMAL128),
				MathContext.DECIMAL128);
		ans[1] = (sqrt.multiply(BigDecimal.valueOf(-1), MathContext.DECIMAL128)
				.subtract(B, MathContext.DECIMAL128)).divide(BigDecimal
				.valueOf(2).multiply(A, MathContext.DECIMAL128),
				MathContext.DECIMAL128);
		return ans;
	}

}
