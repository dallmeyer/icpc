import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class nine {

	public static void main(String[] args) {
		Scanner in= new Scanner(System.in);
		
		String s = in.next();
		while (!s.equals("00:00"))
		{
			int MM = Integer.parseInt(s.substring(0,2)),
				SS = Integer.parseInt(s.substring(3,5));
			
			int totalS = SS + 60*MM;
			int delta = totalS/10;
			if (totalS%10 == 0)
				delta--;
			
			int minS = totalS - delta,
				maxS = totalS + delta;
			
			int bestNumberOf9s = -1;
			int numSecsOfBest = -1;
			String bestString = "";			
			ArrayList<String> poss = new ArrayList<String>();
			
			for (int time = minS; time <= maxS; time++)
			{
				String s1 = irreg(time);
				if (s1 != null)
				{
					int x = countNines(s1);
					if (x > bestNumberOf9s)
					{
						poss.clear();
						poss.add(s1);
						bestNumberOf9s = x;
						numSecsOfBest = time;
						bestString = s1;
					}
					else if (x == bestNumberOf9s)
					{
						int diff = (Math.abs(totalS - numSecsOfBest) - Math.abs(totalS - time));
						if (diff > 0)
						{
							poss.clear();
							poss.add(s1);
							bestNumberOf9s = x;
							numSecsOfBest = time;
							bestString = s1;
						}
						else if (diff == 0)
						{
							poss.add(s1);
							bestNumberOf9s = x;
							numSecsOfBest = time;
							bestString = s1;
						}
					}
				}
				
				String s2 = reg(time);
				int x = countNines(s2);
				if (x > bestNumberOf9s)
				{
					poss.clear();
					poss.add(s2);
					bestNumberOf9s = x;
					numSecsOfBest = time;
					bestString = s2;
				}
				else if (x == bestNumberOf9s)
				{
					int diff = (Math.abs(totalS - numSecsOfBest) - Math.abs(totalS - time));
					if (diff > 0)
					{
						poss.clear();
						poss.add(s2);
						bestNumberOf9s = x;
						numSecsOfBest = time;
						bestString = s2;
					}
					else if (diff == 0)
					{
						poss.add(s2);
						bestNumberOf9s = x;
						numSecsOfBest = time;
						bestString = s2;
					}
				}
			}
			
			Collections.sort(poss);
			
			System.out.println(poss.get(0));
			
			s = in.next();
		}
	}

	private static int countNines(String s)
	{
		int n = 0;
		for (int i = 0; i < 5; i++)
		{
			if (s.charAt(i) == '9')
				n++;
		}
		
		return n;
	}
	
	private static String irreg(int i)
	{
		int s = i%60;
		
		if (s < 40)
		{
			int m = (i-s)/60 - 1;
			if (m < 0)
				return null;
			s += 60;
			return String.format("%02d:%02d", m, s);
		}
		
		return null;	//will be same as reg(i)
	}
	
	private static String reg(int i)
	{
		int s = i%60;
		
		int m = (i-s)/60;
		return String.format("%02d:%02d", m, s);
	}
}
