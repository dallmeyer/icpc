import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Scanner;

public class Sort {

	static int N;
	static String[] rules;
	static HashMap<String, int[]> cache = new HashMap<String, int[]>();

	public static void main(String[] arg) {
		Scanner in = new Scanner(System.in);

		while (true) {
			N = in.nextInt();
			if (N == 0)
				return;
			in.nextLine();
			cache.clear();

			rules = new String[N];

			for (int i = 0; i < N; i++)
				rules[i] = in.nextLine();

			int[] sol = dp(0, new BitSet(N));

			for (int i = 0; i < N; i++) {
				System.out.printf("%d ", sol[i]);
			}
			System.out.println();
			System.out.println(calcCost(0, sol));
		}
	}

	public static int[] dp(int cur, BitSet used) {
		String key = used.toString() + cur;

		// if (cache.containsKey(key))
		// return cache.get(key).clone();

		if (cur == N) {
			int[] blank =  new int[N];
			Arrays.fill(blank, -1);
			return blank;
		}
			

		int[] sol = null;
		int min = Integer.MAX_VALUE;

		for (int i = 0; i < N; i++) {
			
			if (cur == 0)
				System.out.println(" HEREEEEEEEEEEEEEEEEEEEEE" + i);
			if (used.get(i))
				continue;

//			BitSet clone = (BitSet) used.clone();
//			clone.set(i);
			used.set(i);
			int[] cand = dp(cur + 1, used);
			used.set(i, false);
			cand[cur] = i;
			int cost = calcCost(cur, cand);
			System.out.println(cost);
			
			printArr(cand);
			System.out.println("  -->  "+cost);

			if (cost < min) {
				min = cost;
				sol = cand.clone();
			}
		}
		
		

//		cache.put(key, sol);
		return sol;
	}

	public static void printArr(int[] arr) {
		for (Integer i : arr) {
			System.out.print(i + " ");
		}
	}

	public static int calcCost(int cur, int[] cand) {
		int ans = 0;
		for (int i = cur; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				if (less(i, j))
					ans++;
			}
		}
		return ans;
	}

	public static boolean less(int i, int j) {
		return rules[i].charAt(j) == '1';
	}
}
