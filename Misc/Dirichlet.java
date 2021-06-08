import java.util.Scanner;

public class Dirichlet {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		int casenum = 1;
		while (true) {
			long a = in.nextLong();
			if (a == 0)
				break;
			long b = in.nextLong();
			long L = in.nextLong();
			long U = in.nextLong();

			long ans = solve(a, b, L, U);
			System.out.printf("Case %d: %d%n", casenum, ans);
			casenum++;
		}
	}

	public static long solve(long a, long b, long L, long U) {
		long ans = 0;
		for (long n = L; n <= U; n++) {
			boolean prime = true;
			long t = a * n + b;
			for (long i = 2; i * i <= t; i++) {
				if (t % i == 0) {
					prime = false;
					break;
				}
			}
			if (prime)
				ans++;
		}
		return ans;
	}

}
