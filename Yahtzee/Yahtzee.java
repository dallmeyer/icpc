

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Yahtzee {

	private static ArrayList<Integer[]> scores = new ArrayList<Integer[]>();
	
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		while (in.hasNext())
		{
			scores.clear();
			Integer blankScore[] = new Integer[13];
			for (int i = 0; i < 13; i++)
			{
				blankScore[i] = -1;
			}
			
			scores.add(blankScore);
			
			for (int j = 0; j < 13; j++)
			{
				Integer hand[] = new Integer[5];
				for (int i = 0; i < 5; i++)
				{
					hand[i] = in.nextInt();
				}
				
				int count = scores.size();
				for (int i = 0; i < count; i++)
				{
					Integer score[] = scores.get(0);
					Integer newScore[];
					
					if (score[0] == -1)
					{
						newScore = score.clone();
						int sum = 0;
						for (int k = 0; k < 5; k++)
						{
							sum += (hand[k] == 1) ? 1 : 0;
						}
						
						newScore[0] = sum;
						if (!deepContains(newScore)) scores.add(newScore);
					}
					if (score[1] == -1)
					{
						newScore = score.clone();
						int sum = 0;
						for (int k = 0; k < 5; k++)
						{
							sum += (hand[k] == 2) ? 2 : 0;
						}
						
						newScore[1] = sum;
						if (!deepContains(newScore)) scores.add(newScore);
					}
					if (score[2] == -1)
					{
						newScore = score.clone();
						int sum = 0;
						for (int k = 0; k < 5; k++)
						{
							sum += (hand[k] == 3) ? 3 : 0;
						}
						
						newScore[2] = sum;
						if (!deepContains(newScore)) scores.add(newScore);						
					}
					if (score[3] == -1)
					{
						newScore = score.clone();
						int sum = 0;
						for (int k = 0; k < 5; k++)
						{
							sum += (hand[k] == 4) ? 4 : 0;
						}
						
						newScore[3] = sum;
						if (!deepContains(newScore)) scores.add(newScore);						
					}
					if (score[4] == -1)
					{
						newScore = score.clone();
						int sum = 0;
						for (int k = 0; k < 5; k++)
						{
							sum += (hand[k] == 5) ? 5 : 0;
						}
						
						newScore[4] = sum;
						if (!deepContains(newScore)) scores.add(newScore);						
					}
					if (score[5] == -1)
					{
						newScore = score.clone();
						int sum = 0;
						for (int k = 0; k < 5; k++)
						{
							sum += (hand[k] == 6) ? 6 : 0;
						}
						
						newScore[5] = sum;
						if (!deepContains(newScore)) scores.add(newScore);						
					}
					if (score[6] == -1)
					{
						newScore = score.clone();
						int sum = 0;
						for (int k = 0; k < 5; k++)
						{
							sum += hand[k];
						}
						
						newScore[6] = sum;
						if (!deepContains(newScore)) scores.add(newScore);						
					}
					if (score[7] == -1)	//3 of a kind
					{
						newScore = score.clone();
						int sum = 0;

						boolean toak = false;
						for (int k = 0; k < 5; k++)
						{
							int n = 1;
							int a = hand[k];
							for (int l = 0; l < 5; l++)
							{
								if (l == k) continue;
								
								if (hand[l] == a)
									n++;
							}
							
							if (n >= 3)
							{
								toak = true;
								break;
							}
						}
						
						if (toak)
						{
							for (int k = 0; k < 5; k++)
							{
								sum += hand[k];
							}
						}
							
						newScore[7] = sum;
						if (!deepContains(newScore)) scores.add(newScore);						
					}
					if (score[8] == -1)	//4 of a kind
					{
						newScore = score.clone();
						int sum = 0;

						boolean foak = false;
						for (int k = 0; k < 5; k++)
						{
							int n = 1;
							int a = hand[k];
							for (int l = 0; l < 5; l++)
							{
								if (l == k) continue;
								
								if (hand[l] == a)
									n++;
							}
							
							if (n >= 4)
							{
								foak = true;
								break;
							}
						}
						
						if (foak)
						{
							for (int k = 0; k < 5; k++)
							{
								sum += hand[k];
							}
						}
							
						newScore[8] = sum;
						if (!deepContains(newScore)) scores.add(newScore);						
					}
					if (score[9] == -1)	//5 of a kind
					{
						newScore = score.clone();
						int sum = 0;

						boolean foak = false;
						for (int k = 0; k < 5; k++)
						{
							int n = 1;
							int a = hand[k];
							for (int l = 0; l < 5; l++)
							{
								if (l == k) continue;
								
								if (hand[l] == a)
									n++;
							}
							
							if (n == 5)
							{
								foak = true;
								break;
							}
						}
						
						if (foak)
						{
							sum = 50;
						}
							
						newScore[9] = sum;
						if (!deepContains(newScore)) scores.add(newScore);						
					}
					if (score[10] == -1)	//short straight
					{
						newScore = score.clone();
						int sum = 0;

						boolean str8 = false;
						List<Integer> hand2 = Arrays.asList(hand);
						
						if ((hand2.contains(1) && hand2.contains(2) && hand2.contains(3) && hand2.contains(4)) ||
							(hand2.contains(2) && hand2.contains(3) && hand2.contains(4) && hand2.contains(5)) ||
							(hand2.contains(3) && hand2.contains(4) && hand2.contains(5) && hand2.contains(6)))
						{
							str8 = true;
						}
							
						if (str8)
						{
							sum = 25;
						}
							
						newScore[10] = sum;
						if (!deepContains(newScore)) scores.add(newScore);						
					}
					if (score[11] == -1)	//long straight
					{
						newScore = score.clone();
						int sum = 0;

						boolean str8 = false;
						List<Integer> hand2 = Arrays.asList(hand);
						
						if ((hand2.contains(1) && hand2.contains(2) && hand2.contains(3) && hand2.contains(4) && hand2.contains(5)) ||
							(hand2.contains(2) && hand2.contains(3) && hand2.contains(4) && hand2.contains(5) && hand2.contains(6)))
						{
							str8 = true;
						}
							
						if (str8)
						{
							sum = 35;
						}
							
						newScore[11] = sum;
						if (!deepContains(newScore)) scores.add(newScore);						
					}
					if (score[12] == -1)
					{
						newScore = score.clone();
						int sum = 0;
						
						boolean fh = false;
						
						int cts[] = new int[6];
						for (int p = 0; p < 6; p++)
						{
							cts[p] = 0;
						}
						for (int p = 0; p < 5; p++)
						{
							cts[hand[p] - 1]++;
						}
						
						checkLoop: for (int p = 0; p < 6; p++)
						{
							if (cts[p] == 3)
							{
								for (int q = 0; q < 6; q++)
								{
									if (q==p)	continue;
									
									if (cts[q] == 2)
									{
										fh = true;
										break checkLoop;
									}
								}
							}
						}
						
						if (fh)
						{
							sum = 40;
						}
						
						newScore[12] = sum;
						if (!deepContains(newScore)) scores.add(newScore);
					}
					scores.remove(0);
				}
				
				count = scores.size();
				ArrayList<Integer[]> topScores = new ArrayList<Integer[]>();
				
				ArrayList<Integer> sizes = new ArrayList<Integer>();
				for (int i = 0; i < count; i++)
				{
					Integer score[] = scores.get(i);
					sizes.add(totalPoints(score));
				}
				ArrayList<Integer> sizesClone = (ArrayList<Integer>) sizes.clone();
				Collections.sort(sizesClone);
				int mincount = Math.min(200, count);
				for (int i = 0; i < mincount; i++)
				{
					int index = sizes.indexOf(sizesClone.get(sizesClone.size() - 1));
					topScores.add(scores.get(index));
					sizes.remove(index);
					scores.remove(index);
					sizesClone.remove(sizesClone.size() - 1);
				}
				
				scores = topScores;
			}
			
			int max = 0;
			int maxi = 1;
			 
			for (int i = 0; i < scores.size(); i++)
			{
				int sum = totalPoints(scores.get(i));
				
				if (sum > max)
				{
					max = sum;
					maxi = i;
				}
			}
			
			Integer bestScore[] = scores.get(maxi);
			int sum = 0;
			int firstSix = 0;
			for (int i = 0; i < 13; i++)
			{
				if (i < 6)
					firstSix += bestScore[i];
				
				sum += bestScore[i];
				
				System.out.print(bestScore[i] + " ");
			}
			
			if (firstSix >= 63)
			{
				System.out.print("35 ");
				sum += 35;
			}
			else
			{
				System.out.print("0 ");
			}
			
			System.out.println(sum);
		}
	}
	
	private static int totalPoints(Integer[] score)
	{
		int sum = 0;
		int firstSix = 0;
		for (int i = 0; i < 13; i++)
		{
			if (i < 6)
				firstSix += score[i];
			
			sum += score[i];
		}
		
		if (firstSix > 63)
			sum += 35;
		
		return sum;
	}
	
	private static boolean deepContains(Integer[] score)
	{
		for (int i = 0; i < scores.size(); i++)
		{
			Integer oldScore[] = scores.get(i);
			boolean match = true;
			for (int j = 0 ; j < 13; j++)
			{
				if (oldScore[j] != score[j])
				{
					match = false;
					break;
				}
			}
			
			if (match)
				return true;
		}
		
		return false;
	}
}
