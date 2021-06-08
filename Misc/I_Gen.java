import java.util.Random;


public class I_Gen {

	public static void main(String[] args) {

		Random gen = new Random();
		for (int i = 0; i < 100; i++)
		{
			System.out.println((gen.nextInt(2000) - 1000) + " " + (gen.nextInt(2000) - 1000));			
		}
	}

}
