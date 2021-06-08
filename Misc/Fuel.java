import java.util.Scanner;

public class Fuel {

	public static void main(String[] args) {

		Fuel me = new Fuel();
		me.runALL();

	}

	Scanner in = new Scanner(System.in);

	public void runALL() {
		int i = 0;
		while (true) {
			i++;
			int N = in.nextInt();
			if (N == 0) {
				return;
			}
			runOne(i, N);
		}
	}

	public void runOne(int iCase, int nCities) {

		int[] fuelDel = new int[nCities];

		in.nextLine();
		String fuelAvailLine = in.nextLine();
		String fuelCostString = in.nextLine();

		String[] fuelBreak = fuelAvailLine.split(" ");
		String[] costBreak = fuelCostString.split(" ");

		for (int i = 0; i < nCities; i++) {
			fuelDel[i] = Integer.parseInt(fuelBreak[i])
					- Integer.parseInt(costBreak[i]);
		}

		int[] runningVal = new int[nCities];

		for (int i = 1; i < nCities; i++) {
			runningVal[i] = runningVal[i - 1] + fuelDel[i-1];
		}

		// Global min
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < nCities; i++) {
			min = Math.min(min, runningVal[i]);
		}

		// Find all min
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nCities; i++) {
			if (runningVal[i] == min) {
				sb.append((i+1) + " ");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		System.out.println("Case " + iCase + ": " + sb);
	}

}
