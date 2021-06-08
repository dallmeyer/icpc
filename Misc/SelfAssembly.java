import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SelfAssembly {

	private static int TOP = 0, RIGHT = 1, BOTTOM = 2, LEFT = 3;
	private static List<int[]>[] bottom, left;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		MyScanner in = new MyScanner();

		while (true) {
			int n;
			try {
				n = in.nextInt();
			} catch (Exception e1) {
				return;
			}
			ArrayList<int[]> mols = new ArrayList<int[]>();
			bottom = new List[53];
			left = new List[53];
			for (int i = 0; i < 53; i++) {
				bottom[i] = new ArrayList<int[]>();
				left[i] = new ArrayList<int[]>();
			}

			for (int i = 0; i < n; i++) {
				String s;
				s = in.next();
				int[] m = getMol(s);

				for (int j = 0; j < 4; j++) {
					int[] mf = flip(m);
					mols.add(m);
					mols.add(mf);

					if (m[BOTTOM] != 0) {
						bottom[m[BOTTOM]].add(m);
					}
					if (m[LEFT] != 0) {
						left[m[LEFT]].add(m);
					}
					if (mf[BOTTOM] != 0) {
						bottom[mf[BOTTOM]].add(mf);
					}
					if (mf[LEFT] != 0) {
						left[mf[LEFT]].add(mf);
					}

					m = rotate(m);
				}
			}

			boolean[] leftSeen = new boolean[53], botSeen = new boolean[53];
			boolean bounded = true;
			for (int[] m : mols) {
				if (!isBounded(m, leftSeen, botSeen)) {
					bounded = false;
					break;
				}
			}

			if (bounded) {
				System.out.println("bounded");
			} else {
				System.out.println("unbounded");
			}
		}
	}

	private static int[] flip(int[] m) {
		int[] f = new int[4];
		f[0] = m[2];
		f[1] = m[1];
		f[2] = m[0];
		f[3] = m[3];

		return f;
	}

	private static int[] rotate(int[] m) {
		int[] r = new int[4];
		r[0] = m[1];
		r[1] = m[2];
		r[2] = m[3];
		r[3] = m[0];
		return r;
	}

	private static int inverse(int i) {
		if (i > 26) {
			return i - 26;
		} else
			return i + 26;
	}

	private static boolean isBounded(int[] cur, boolean[] leftSeen,
			boolean[] botSeen) {

		if (cur[LEFT] != 0) {
			leftSeen[cur[LEFT]] = true;
		}
		if (cur[BOTTOM] != 0) {
			botSeen[cur[BOTTOM]] = true;
		}

		if ((cur[RIGHT] != 0 && leftSeen[inverse(cur[RIGHT])])
				|| (cur[TOP] != 0 && botSeen[inverse(cur[TOP])])) {
			return false;
		}

		if (cur[RIGHT] != 0) {
			List<int[]> addToRight = left[inverse(cur[RIGHT])];

			for (int[] m : addToRight) {
				if (!isBounded(m, leftSeen, botSeen)) {
					return false;
				}
			}
		}

		if (cur[TOP] != 0) {
			List<int[]> addToTop = bottom[inverse(cur[TOP])];

			for (int[] m : addToTop) {
				if (!isBounded(m, leftSeen, botSeen)) {
					return false;
				}
			}
		}

		leftSeen[cur[LEFT]] = false;
		botSeen[cur[BOTTOM]] = false;

		return true;
	}

	private static int[] getMol(String s) {
		int[] m = new int[4];
		char[] c = s.toCharArray();
		m[0] = convert(c[0], c[1]);
		m[1] = convert(c[2], c[3]);
		m[2] = convert(c[4], c[5]);
		m[3] = convert(c[6], c[7]);

		return m;
	}

	private static int convert(char c1, char c2) {
		if (c1 == '0') {
			return 0;
		}
		int n = c1 - 'A' + 1;
		if (c2 == '-') {
			n += 26;
		}

		return n;
	}

	private static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		public MyScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreElements()) {
				st = new StringTokenizer(br.readLine());
			}
			return st.nextToken();
		}

		int nextInt() throws NumberFormatException, IOException {
			return Integer.parseInt(next());
		}
	}
}
