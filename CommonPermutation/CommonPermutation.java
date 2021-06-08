import java.util.Arrays;
import java.util.Scanner;


public class CommonPermutation {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		while(in.hasNext())
		{
			String a = in.nextLine();
			String b = in.nextLine();
			
			char aa[] = a.toCharArray();
			Arrays.sort(aa);
			char bb[] = b.toCharArray();
			Arrays.sort(bb);
			
			int j = 0;
			for (int i = 0; i < a.length(); i++)
			{
				for (; j < b.length(); j++)
				{
					char x = aa[i], y = bb[j];
					if (x == y)
					{
						System.out.print(x);
						j++;
						break;
					}
					if (x < y)
					{
						break;
					}
				}
			}
			System.out.println();
		}
	}
}