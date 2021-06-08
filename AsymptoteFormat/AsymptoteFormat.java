import java.util.Scanner;


public class AsymptoteFormat {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int n = in.nextInt();
		
		System.out.print("draw(");
		
		for (int i = 0; i < n; i++)
		{
			int x = in.nextInt();
			int y = in.nextInt();
			in.nextInt();
			
			System.out.print("(" + x + "," + y + ")--");
		}
		
		System.out.println("cycle);");
	}

}
