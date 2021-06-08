import java.util.Scanner;

public class B_AlwaysAnInteger {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int tc = 0;
		mainLoop: while (true) {
			tc++;

			String s = in.nextLine();
			if (s.startsWith("."))
				break;

			String[] frac = s.split("/");
			double D = Integer.parseInt(frac[1].trim());

			String numer = frac[0].substring(1, frac[0].length() - 1);

			Polynomial P = new Polynomial(parse(numer));
			double[] evals = new double[P.n + 2];

			for (int i = 0; i < P.n + 2; i++) {
				evals[i] = P.apply(i);
				if (evals[i] % D != 0) {
					System.out.println("Case " + tc + ": Not always an integer");
					continue mainLoop;
				}
			}

			System.out.println("Case " + tc + ": Always an integer");
		}
	}

	private static double[] parse(String poly) {
		//string replacements for simpler parsing
		if (poly.startsWith("n"))
			poly = "+1" + poly;
		poly = poly.replaceAll("\\+n", "+1n");
		poly = poly.replaceAll("-n", "-1n");
		poly = poly.replaceAll("n\\+", "n^1+");
		poly = poly.replaceAll("n-", "n^1-");
		if (poly.endsWith("n"))
			poly += "^1";

		//determine degree of poly
		int maxE = 0;
		if (poly.contains("^")) {
			int eInd = poly.indexOf("^") + 1;
			maxE = 0;
			for (int i = 0; i < 3 && eInd + i < poly.length(); i++) {
				char e = poly.charAt(eInd + i);
				if (e >= '0' && e <= '9') {
					maxE *= 10;
					maxE += Integer.parseInt(e + "");
				} else {
					break;
				}
			}
		}

		double[] coeffs = new double[maxE + 1];

		int i = 0;
		while (i < poly.length()) {
			int nextN = poly.indexOf('n', i);
			if (nextN == -1) // 0-power term
			{
				double c = Integer.parseInt(poly.substring(i));
				coeffs[0] = c;
				i = poly.length();
			} else {
				double c = Integer.parseInt(poly.substring(i, nextN));
				i = nextN + 2;
				int exp = 0;

				int j = 0;
				for (; j < 3 && i + j < poly.length(); j++) {
					char e = poly.charAt(i + j);
					if (e >= '0' && e <= '9') {
						exp *= 10;
						exp += Integer.parseInt(e + "");
					} else {
						break;
					}
				}
				i += j;

				coeffs[exp] = c;
			}
		}

		return coeffs;
	}

	static class Polynomial {
		double[] a; // coefficients a[0], a[1], ...
		int n; // degree

		Polynomial(double[] a) {
			this.a = a.clone();
			this.n = a.length;
		}

		public String toString() {
			String s = "", sep = "";
			for (int k = 0; k < n; k++) {
				if (a[k] != 0) {
					s += String.format("%s%f x^%d", sep, a[k], k);
					sep = " + ";
				}
			}
			return s;
		}

		/**
		 * Horner's method.
		 */
		double apply(double x) {
			double y = a[n - 1];
			for (int k = n - 2; k >= 0; k--)
				y = y * x + a[k];

			return y;
		}
	}
}
