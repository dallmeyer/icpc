package ptTryouts;

import java.util.Scanner;

public class oddities {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int N = in.nextInt();
		for (int i = 0; i < N; i++)
		{
			int n = in.nextInt();
			if (n%2==0)
			{
				System.out.println(n + " is even");
			}
			else
			{
				System.out.println(n + " is odd");
			}
		}
	}

}
