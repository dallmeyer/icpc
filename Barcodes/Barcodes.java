import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Scanner;

public class Barcodes {

	//bitsets representing the different character encodings
	private static BitSet START_STOP = BitSet.valueOf(new long[] { 12 });
	private static ArrayList<BitSet> ENCODINGS = new ArrayList<BitSet>(
			Arrays.asList(new BitSet[] { BitSet.valueOf(new long[] { 16 }),
					BitSet.valueOf(new long[] { 17 }),
					BitSet.valueOf(new long[] { 18 }),
					BitSet.valueOf(new long[] { 3 }),
					BitSet.valueOf(new long[] { 20 }),
					BitSet.valueOf(new long[] { 5 }),
					BitSet.valueOf(new long[] { 6 }),
					BitSet.valueOf(new long[] { 24 }),
					BitSet.valueOf(new long[] { 9 }),
					BitSet.valueOf(new long[] { 1 }),
					BitSet.valueOf(new long[] { 4 }) }));

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int caseNo = 0;
		mainLoop: while (true) {
			caseNo++;
			int m = in.nextInt();
			if (m == 0) {
				break mainLoop;
			}

			double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
			double[] d = new double[m];
			for (int i = 0; i < m; i++) {
				d[i] = in.nextInt();
				if (d[i] < min) {
					min = d[i];
				}
				if (d[i] > max) {
					max = d[i];
				}
			}

			//first condition checks there are an appropriate number of characters
			//second condition checks that there is at least one char in the true message
			if ((m + 1) % 6 != 0 || m < 29) {
				System.out.println("Case " + caseNo + ": bad code");
				continue mainLoop;
			}

			//checks that the min/max don't violate (thin * 2 = thick)
			if (2 * min / .95 < max / 1.05) {
				System.out.println("Case " + caseNo + ": bad code");
				continue mainLoop;
			}

			//in this list we will add thick vals, and 2*thin vals
			ArrayList<Double> scaledVals = new ArrayList<Double>();
			
			//bitsets to store the full encoding in both directions
			BitSet[] b = new BitSet[2];
			b[0] = new BitSet(m);
			b[1] = new BitSet(m);

			//determine whether each bar is thin or thick
			for (int i = 0; i < m; i++) {
				if (d[i] - min < max - d[i]) {		//0	(thin)
					scaledVals.add(2*d[i]);
				} else if (d[i] - min > max - d[i]) {	//1 (thick)
					b[0].flip(i);
					b[1].flip(m - 1 - i);
					scaledVals.add(d[i]);
				} else {
					System.out.println("Case " + caseNo + ": bad code");
					continue mainLoop;
				}
			}

			//check that all thinVals are within +/- 5% of intended width
			Collections.sort(scaledVals);
			double lo = scaledVals.get(0),
					hi = scaledVals.get(scaledVals.size()-1);
			if (hi / 1.05 > lo / .95) {
				System.out.println("Case " + caseNo + ": bad code");
				continue mainLoop;
			}

			// n == number of chars in actual msg
			int n = (m - 23) / 6;

			// o == 0: original order
			// o == 1: reverse order
			// for 'bad code' we dont output anything until after both iterations of this loop
			orderLoop: for (int o = 0; o < 2; o++) {
				// check START
				if (!b[o].get(0, 5).equals(START_STOP)) {
					continue orderLoop;
				}

				// determine message
				String output = "";
				int i = 1;		//same 'i' as in prob statement
				int bInd = 6;	//index within bitset
				int cWeight = 0, kWeight = 0;
				while (i <= n) {
					BitSet enc = b[o].get(bInd, bInd + 5);
					if (ENCODINGS.contains(enc)) {
						int weight = ENCODINGS.indexOf(enc);
						if (weight == 10) {
							output += "-";
						} else {
							output += weight;
						}

						cWeight += (((n - i) % 10) + 1) * weight;
						kWeight += (((n - i + 1) % 9) + 1) * weight;

						bInd += 6;
						i++;
					} else {	//invalid encoding
						continue orderLoop;
					}
				}

				//if we make it this far, decoding was successful, but checksums may fail
				
				// check C
				cWeight %= 11;
				BitSet encC = b[o].get(bInd, bInd + 5);
				if (!encC.equals(ENCODINGS.get(cWeight))) {
					System.out.println("Case " + caseNo + ": bad C");
					continue mainLoop;
				}
				bInd += 6;

				// check K
				kWeight += cWeight;
				kWeight %= 11;
				BitSet encK = b[o].get(bInd, bInd + 5);
				if (!encK.equals(ENCODINGS.get(kWeight))) {
					System.out.println("Case " + caseNo + ": bad K");
					continue mainLoop;
				}
				bInd += 6;

				// check STOP
				BitSet encStop = b[o].get(bInd, bInd + 5);
				if (!encStop.equals(START_STOP)) {
					continue orderLoop;
				}

				System.out.println("Case " + caseNo + ": " + output);
				continue mainLoop;
			}

			System.out.println("Case " + caseNo + ": bad code");
		}
	}

}
