import java.util.Arrays;


public class CandidateSelectionEasy {

	public int[] sort(String[] score, int x)
	{
		int n = score.length;
		int m = score[0].length();
		
		Candidate[] peeps = new Candidate[n];
		for (int i = 0; i < n; i++)
		{
			String s = score[i];
			peeps[i] = new Candidate(i, s.charAt(x));
		}
		
		Arrays.sort(peeps);
		int[] out = new int[n];
		for (int i = 0; i < n; i++)
		{
			out[i] = peeps[i].num;
		}
		
		return out;
	}
	
	private static class Candidate implements Comparable<Candidate>
	{
		int num;
		char skill;
		
		public Candidate(int n, char c)
		{
			num = n;
			skill = c;
		}

		@Override
		public int compareTo(Candidate o) {
			int d = this.skill - o.skill;
			if (d != 0)
			{
				return d;
			}
			return this.num - o.num;
		}		
	}

}
