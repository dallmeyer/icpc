import java.util.Scanner;


public class SternBrocot {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		double a = in.nextDouble();
		double b = in.nextDouble();
		while (a != 1 && b != 1)
		{
			Frac pl = new Frac(0,1,null,null);
			Frac pr = new Frac(1,0,null,null);
			Frac x = new Frac(a,b,null,null);
			Frac i = new Frac(1,1,pl,pr);
			
			while (true)
			{
				int d = i.compareTo(x);
				if (d == 0)
				{
					break;
				}
				if (d > 0)
				{
					i = i.left();
					System.out.print("L");
				}
				else
				{
					i = i.right();
					System.out.print("R");
				}
			}
			System.out.println();
			
			a = in.nextDouble();
			b = in.nextDouble();
		}
	}

	private static class Frac implements Comparable<Frac>
	{
		double a,b;
		Frac pl, pr;
		
		public Frac(double a, double b, Frac pl, Frac pr)
		{
			this.a = a;
			this.b = b;
			this.pl = pl;
			this.pr = pr;
		}
		
		public double val()
		{
			return a/b;
		}
		
		public Frac left()
		{
			return new Frac(this.a + pl.a, this.b + pl.b, pl, this);
		}
		
		public Frac right()
		{
			return new Frac(this.a + pr.a, this.b + pr.b, this, pr);
		}

		@Override
		public int compareTo(Frac o) {
			double diff = this.val() - o.val();
			if (diff < 0)
			{
				return -1;
			}
			else if (diff == 0)
			{
				return 0;
			}
			else
			{
				return 1;
			}
		}
		
		
	}
}
