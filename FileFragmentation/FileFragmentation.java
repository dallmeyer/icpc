import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FileFragmentation {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int N = Integer.parseInt(in.nextLine());
		in.nextLine();
		for (int n = 0; n < N; n++)
		{
			HashMap<Integer, ArrayList<String>> l = new HashMap<Integer, ArrayList<String>>();
			
			String s = in.nextLine();
			int min = s.length();
			int max = 0;
			while (!s.equals(""))
			{
				int len = s.length();
				if (len < min)
				{
					min = len;
				}
				if (len > max)
				{
					max = len;
				}
				
				if (l.containsKey(len))
				{
					l.get(len).add(s);
				}
				else
				{
					ArrayList<String> x = new ArrayList<String>();
					x.add(s);
					l.put(len, x);
				}
				
				if (!in.hasNext())
					break;
				
				s = in.nextLine();
			}
			
			int len = min + max;	//total length of original string
			double mid = (double) len / 2;
			
			ArrayList<String> sols = new ArrayList<String>();
			boolean firstRun = true;
			
			for (int i = min; i <= mid; i++)
			{
				if (!l.containsKey(i))	continue;
				ArrayList<String> a = l.get(i);							//smaller bucket
				ArrayList<String> b = (i == mid) ? a : l.get(len - i);	//larger (complement) bucket
				
				for (int k = 0; k < a.size(); k++)
				{
					ArrayList<String> posSols = new ArrayList<String>();
					for (int j = 0; j < b.size(); j++)
					{
						String x = a.get(k);
						String y = b.get(j);
						
						String xy = x+y;
						String yx = y+x;
						if (!posSols.contains(xy))	posSols.add(xy);
						if (!posSols.contains(yx))	posSols.add(yx);
					}
					
					if (firstRun)
					{
						sols.addAll(posSols);
						firstRun = false;
					}
					else
					{
						for (int q = 0; q < sols.size(); q++)
						{
							String posSol = sols.get(q);
							if (!posSols.contains(posSol))
							{
								sols.remove(q);
								q--;
							}
						}
					}
				}
			}
			
			String solution = sols.size() > 0 ? sols.get(0) : "";
			System.out.println(solution);
			if (n != N-1) System.out.println();
		}
	}
}