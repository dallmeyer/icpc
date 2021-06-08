import java.util.ArrayList;
import java.util.Scanner;


public class aMultiplicationGame {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		//stan wins if n falls into an odd interval
		long[] rangeMaxes = new long[20];
		long n = 1;
		boolean timesNine = true;
		for (int i = 0; i < 20; i++)
		{
			n *= (timesNine ? 9 : 2);
			rangeMaxes[i] = n;
			timesNine = !timesNine;
		}		
		
		while (in.hasNext())
		{
			n = in.nextLong();
			
			boolean stanWins = true;
			for (int i = 0; i < 20; i++)
			{
				if (n <= rangeMaxes[i])
				{
					stanWins = (i % 2 == 0);
					break;
				}
			}
			
			if (stanWins)
			{
				System.out.println("Stan wins.");
			}
			else
			{
				System.out.println("Ollie wins.");
			}
		}
	}

}
