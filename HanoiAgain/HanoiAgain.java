import java.util.Scanner;


public class HanoiAgain {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int N = in.nextInt();
		for (int i = 0; i < N; i++)
		{
			int p = in.nextInt();
			
			System.out.println(quad(p));
			
		}
	}
	
	private static int quad(int p)
	{
		  return (int) (Math.floor((p+1)*(p+1)/2)-1);
	}
}
	