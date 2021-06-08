import java.util.ArrayDeque;
import java.util.Queue;


public class PairGameEasy {

	public static void main(String[] args)
	{
		System.out.println(able(1,2,3,5));
	}
	
	public static String able(int a, int b, int c, int d)
	{
		Tuple winning = new Tuple(c,d);
		Queue<Tuple> q = new ArrayDeque<Tuple>();
		Tuple t = new Tuple(a,b);
		if (t.equals(winning))
		{
			return "Able to generate";
		}
		
		q.add(t);
		while (!q.isEmpty())
		{
			t = q.poll();
			Tuple t1 = new Tuple(t.x+t.y,t.y);
			if (t1.equals(winning))
			{
				return "Able to generate";
			}
			else if (t1.x <= winning.x && t1.y <= winning.y)
			{
				q.add(t1);
			}
			Tuple t2 = new Tuple(t.x,t.x+t.y);
			if (t2.equals(winning))
			{
				return "Able to generate";
			}
			else if (t2.x <= winning.x && t2.y <= winning.y)
			{
				q.add(t2);
			}
		}
		
		return "Not able to generate";
	}
	
	private static class Tuple
	{
		int x, y;
		
		public Tuple(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public boolean equals(Object o)
		{
			if (o instanceof Tuple)
			{
				Tuple t = (Tuple) o;
				return this.x == t.x && this.y == t.y;
			}
			return false;
		}
	}
	
}
