import java.util.Scanner;


public class ToiletSeat {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		String s = in.next();
		
		int n = s.length();
		boolean startsUp = s.charAt(0) == 'U';
		
		int a = 0;
		boolean aUp = startsUp;
		for (int i = 1; i < n; i++) //leave up
		{
			boolean seatUp = s.charAt(i) == 'U';
			
			if (seatUp != aUp)
			{
				a++;
			}
			if (!seatUp)
			{
				a++;
			}
			
			aUp = true;
		}
		System.out.println(a);
		
		int b = 0;
		boolean bUp = startsUp;
		for (int i = 1; i < n; i++) //leave down
		{
			boolean seatUp = s.charAt(i) == 'U';
			
			if (seatUp != bUp)
			{
				b++;
			}
			if (seatUp)
			{
				b++;
			}
			
			bUp= false;
		}
		System.out.println(b);
		
		int c = 0;
		boolean cUp = startsUp;
		for (int i = 1; i < n; i++) //leave up
		{
			boolean seatUp = s.charAt(i) == 'U';
			
			if (seatUp != cUp)
			{
				c++;
				cUp = seatUp;
			}
		}
		System.out.println(c);	
	}

}
