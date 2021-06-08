import java.util.Random;


public class SA_Generator {

	public static void main(String[] args) {
		Random gen = new Random();
		for (int i = 0; i < 300; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				int n = gen.nextInt(53);
				if (n == 0)
				{
					System.out.print("00");
				}
				else if (n > 26)
				{
					char c = 'A';
					c += (n - 27);
					System.out.print(c + "-");
				}
				else
				{
					char c = 'A';
					c += (n - 1);
					System.out.print(c + "+");
				}
			}
			System.out.print(" ");
		}
	}

}
