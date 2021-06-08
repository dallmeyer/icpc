import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Shoemaker {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int N = in.nextInt();
		for (int t = 0; t < N; t++)
		{
			if (t > 0)
				System.out.println();
			
			int n = in.nextInt();
			ArrayList<Job> jobs = new ArrayList<Job>();
			
			for (int i = 0; i < n; i++)
			{
				double time = in.nextInt();
				double fine = in.nextInt();
				Job j = new Job(i, time, fine);
				jobs.add(j);
			}
			
			Collections.sort(jobs);
			Collections.reverse(jobs);
			for (int i = 0; i < n; i++)
			{
				System.out.print(jobs.get(i).i);
				if (i != n-1)
					System.out.print(" ");
			}
			
			System.out.println();
		}
	}
	
	private static class Job implements Comparable<Job>
	{
		int i;
		double time, fine, relCost;
		
		public Job(int i, double time, double fine)
		{
			this.i = i+1;
			this.time = time;
			this.fine = fine;
			this.relCost = fine/time;
		}

		@Override
		public int compareTo(Job o) {
			double diff = this.relCost - o.relCost;
			if (diff < 0)
				return -1;
			if (diff == 0)
			{
				return o.i - this.i;
			}

			return 1;
		}
		
		
	}
}
