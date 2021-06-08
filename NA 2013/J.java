package ptTryouts;

import java.util.ArrayList;
import java.util.Scanner;

public class J {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int T = in.nextInt();
		ArrayList<Integer> cards = new ArrayList<Integer>();
		for (int i = 0; i < T; i++)
			cards.add(in.nextInt());
		
		for (int i = T-1; i > 0; i--)
		{
			if (i < cards.size())
			{
			int sum = cards.get(i) + cards.get(i-1);
			if (sum % 2 == 0)
			{
				cards.remove(i);
				cards.remove(i-1);
			}
			}
		}
		
		System.out.print(cards.size());
	}
}
