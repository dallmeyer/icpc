package ptTryouts;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class I {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int T = in.nextInt();
		for (int t = 0; t < T ; t++)
		{
			int r = in.nextInt();
			int n = in.nextInt();
			Angle d = new Angle(in.nextInt(), in.nextInt(), in.nextInt());
			
			ArrayList<Angle> cuts = new ArrayList<Angle>();
			Angle start = new Angle(0,0,0);
			cuts.add(start);
			
			for (int i = 0; i < n; i++)
			{
				start = start.add(d);
				cuts.add(start);
			}
			
			Collections.sort(cuts);
			Angle max = null;
			for (int i = 0; i < n; i++)
			{
				Angle gap = null;
				if (i == n-1)
				{
					gap = cuts.get(0).subtract(cuts.get(i));
				}
				else
				{
					gap = cuts.get(i+1).subtract(cuts.get(i));
				}
				
				if (gap.compareTo(max) > 0)
					max = gap;
			}
			
			double area = (Math.PI*r*r) / 360;
			area *= max.d+((max.m+(max.s/60))/60);
			System.out.printf("%1.7f\n", area);
		}
	}
	
	private static class Angle implements Comparable<Angle>
	{
		int d, m, s;
		
		public Angle(int d, int m, int s)
		{
			this.d = d;
			this.m = m;
			this.s = s;
		}
		
		public Angle add(Angle b)
		{
			int ss = s + b.s;
			int mm = m + b.m;
			int dd = d + b.d;
			
			if (ss >= 60)
				mm++;
			
			if (mm >= 60)
				dd++;
			
			return new Angle(dd%360, mm%60, ss%60);
		}
		
		public Angle subtract(Angle b)
		{	
			int ss = s - b.s;
			int mm = m - b.m;
			int dd = d - b.d;
			
			if (ss < 0)
			{
				ss += 60;
				mm--;
			}
			
			if (mm < 0)
			{
				mm += 60;
				dd--;
			}
			
			return new Angle(dd,mm,ss);
		}

		@Override
		public int compareTo(Angle o) {
			if (o == null)
				return 1;
			
			if (this.d > o.d)
			{
				return 1;
			}
			else if (this.d < o.d)
			{
				return -1;
			}
			else
			{
				if (this.m > o.m)
				{
					return 1;
				}
				else if (this.m < o.m)
				{
					return -1;
				}
				else
				{
					return this.s - o.s;
				}
			}
		}
	}

}
