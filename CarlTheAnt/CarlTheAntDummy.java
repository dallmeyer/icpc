import java.util.Scanner;

public class CarlTheAntDummy {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int numCases = in.nextInt();
		for (int tc = 1; tc <= numCases; tc++)
		{
			int n = in.nextInt(), m = in.nextInt(), d = in.nextInt();
			int[][] pts = new int[201][201];

			int 	x1 = 0, y1 = 0,
					x2, 	y2;

			for (int i = 0; i < n; i++)
			{
				x2 = in.nextInt();
				y2 = in.nextInt();
				
				pts[x2+100][y2+100] = -1;
			}
			
			System.out.println("dammit");
		}
	}

}
