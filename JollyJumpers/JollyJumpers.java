
import java.util.Scanner;

public class JollyJumpers {

	public static void main(String[] args) {
		Scanner in  = new Scanner(System.in);
		
		whileLoop: while (in.hasNext())
		{
			int n = in.nextInt();
			if (n == 1)
			{
				in.nextInt();
				System.out.println("Jolly");
				continue;
			}
				
			boolean b[] = new boolean[n];
			for (int i = 0 ; i < n ; i++)
			{
				b[i] = false;
			}
			
			int x = in.nextInt();
			int y;
			for (int i = 1; i < n; i++)
			{
				y = in.nextInt();
				int d = Math.abs(x-y);
				
				if (d > 0 && d < n)
					b[d] = true;
				
				x = y;
			}
			
			for (int i = 1; i < n; i++)
			{
				if (!b[i])
				{
					System.out.println("Not jolly");
					continue whileLoop;
				}
			}
			System.out.println("Jolly");
		}
	}

}
