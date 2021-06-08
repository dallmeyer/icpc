import java.util.Arrays;
import java.util.Scanner;


public class PokerHands {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		Card[] white = new Card[5];
		Card[] black = new Card[5];
		
		while (in.hasNext())
		{
			for (int i = 0; i < 5; i++)
			{
				String s = in.next();
				char c = s.charAt(0);
				int num;
				if (c == 'T')
				{
					num = 10;
				}
				else if (c == 'J')
				{
					num = 11;
				}
				else if (c == 'Q')
				{
					num = 12;
				}
				else if (c == 'K')
				{
					num = 13;
				}
				else if (c == 'A')
				{
					num = 14;
				}
				else
				{
					num = Integer.parseInt(c+"");
				}
				char suit = s.charAt(1);
				black[i] = new Card(num, suit);
			}
			for (int i = 0; i < 5; i++)
			{
				String s = in.next();
				char c = s.charAt(0);
				int num;
				if (c == 'T')
				{
					num = 10;
				}
				else if (c == 'J')
				{
					num = 11;
				}
				else if (c == 'Q')
				{
					num = 12;
				}
				else if (c == 'K')
				{
					num = 13;
				}
				else if (c == 'A')
				{
					num = 14;
				}
				else
				{
					num = Integer.parseInt(c+"");
				}
				char suit = s.charAt(1);
				white[i] = new Card(num, suit);
			}

			Arrays.sort(black);
			Arrays.sort(white);
			int result = compareHands(black, white); 
			if (result > 0)
			{
				System.out.println("Black wins.");
			}
			else if (result == 0)
			{
				System.out.println("Tie.");
			}
			else
			{
				System.out.println("White wins.");
			}
		}
	}
	
	private static int compareHands(Card[] a, Card[] b)
	{
		Score aa = scoreHand(a);
		Score bb = scoreHand(b);
		
		return aa.compareTo(bb);
	}
	
	private static class Score implements Comparable<Score>{
		public int type;
		public int val;
		public Card[] hand;
		
		public Score (int type, int val, Card[] hand)
		{
			this.type = type;
			this.val = val;
			this.hand = hand;
		}
		
		public int compareTo(Score b)
		{
			if (this.type > b.type)
			{
				return 1;
			}
			else if (this.type < b.type)
			{
				return -1;
			}
			else
			{
				if (this.type == 5 || this.type == 0)
				{
					for (int i = 4; i >= 0; i--)
					{
						if (this.hand[i].num > b.hand[i].num)
						{
							return 1;
						}
						else if (this.hand[i].num < b.hand[i].num)
						{
							return -1;
						}
					}
					return 0;
				}
				else if (this.type == 2)
				{
					int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
					for (int  i = 0; i < 5; i++)
					{
						for (int j = i+1; j < 5; j++)
						{
							if (this.hand[i].num == this.hand[j].num)
							{
								x1 = i;
								y1 = j;
							}
							if (b.hand[i].num == b.hand[j].num)
							{
								x2 = i;
								y2 = i;
							}
						}
					}
					
					if (this.hand[x1].num > b.hand[x2].num)
					{
						return 1;
					}
					else if (this.hand[x1].num < b.hand[x2].num)
					{
						return -1;
					}
					else
					{
						int x3 = 0, y3 = 0, x4 = 0, y4 = 0;
						for (int  i = 0; i < 5; i++)
						{
							if (i == x1 || i == y1)
								continue;
							
							for (int j = i+1; j < 5; j++)
							{
								if (j == x2 || j == y2)
									continue;
								
								if (this.hand[i].num == this.hand[j].num)
								{
									x3 = i;
									y3 = j;
								}
								if (b.hand[i].num == b.hand[j].num)
								{
									x4 = i;
									y4 = i;
								}
							}
						} 
						
						if (this.hand[x3].num > b.hand[x3].num)
						{
							return 1;
						}
						else if (this.hand[x3].num < b.hand[x3].num)
						{
							return -1;
						}
						else
						{
							int i = 4, j = 4;
							while (i >= 0 && j >= 0)
							{
								if (i == x1 || i == y1 || i == x3 || i == y3)
								{
									i--;
									continue;
								}
								if (j == x2 || j == y2 || j == x4 || j == y4)
								{
									j--;
									continue;
								}
								
								if (this.hand[i].num > b.hand[j].num)
								{
									return 1;
								}
								else if (this.hand[i].num < b.hand[j].num)
								{
									return -1;
								}
								i--;
								j--;
							}
							return 0;
						}
					}					
				}
				else if (this.type == 1)
				{
					int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
					for (int  i = 0; i < 5; i++)
					{
						for (int j = i+1; j < 5; j++)
						{
							if (this.hand[i].num == this.hand[j].num)
							{
								x1 = i;
								y1 = j;
							}
							if (b.hand[i].num == b.hand[j].num)
							{
								x2 = i;
								y2 = i;
							}
						}
					}
					
					if (this.hand[x1].num > b.hand[x2].num)
					{
						return 1;
					}
					else if (this.hand[x1].num < b.hand[x2].num)
					{
						return -1;
					}
					else
					{
						int i = 4, j = 4;
						while (i >= 0 && j >= 0)
						{
							if (i == x1 || i == y1)
							{
								i--;
								continue;
							}
							if (j == x2 || j == y2)
							{
								j--;
								continue;
							}
							
							if (this.hand[i].num > b.hand[j].num)
							{
								return 1;
							}
							else if (this.hand[i].num < b.hand[j].num)
							{
								return -1;
							}
							i--;
							j--;
						}
						return 0;
					}
				}
				return this.val - b.val;
			}
		}
	}
	
	private static Score scoreHand(Card[] x)
	{
		int type = -1;
		int val = 0;
		if (isStraightFlush(x))
		{
			type = 8;
			val = x[4].num;
		}
		else if (isFourOAK(x))
		{
			type = 7;
			val = x[2].num;
		}
		else if (isFullHouse(x))
		{
			type = 6;
			val = x[2].num;
		}
		else if (isFlush(x))
		{
			type = 5;
		}
		else if (isStraight(x))
		{
			type = 4;
			val = x[4].num;
		}
		else if (isThreeOAK(x))
		{
			type = 3;
			val = x[2].num;
		}
		else if (isTwoPair(x))
		{
			type = 2;
		}
		else if (isPair(x))
		{
			type = 1;
		}
		else
		{
			type = 0;
		}
		
		return new Score(type, val, x);
	}
	
	private static boolean isPair(Card[] x)
	{
		return (x[0].num == x[1].num || 
				x[1].num == x[2].num ||
				x[2].num == x[3].num ||
				x[3].num == x[4].num);
	}
	
	private static boolean isTwoPair(Card[] x) {
		return ((x[0].num == x[1].num && x[2].num == x[3].num) ||
				(x[0].num == x[1].num && x[3].num == x[4].num) ||
				(x[1].num == x[2].num && x[3].num == x[4].num));
	}

	private static boolean isThreeOAK(Card[] x) {
		return ((x[0].num == x[2].num) || 
				(x[1].num == x[3].num) ||
				(x[2].num == x[4].num));
	}

	private static boolean isFullHouse(Card[] x) {
		return ((x[0].num == x[1].num && x[2].num == x[4].num) || 
				(x[0].num == x[2].num && x[3].num == x[4].num));
	}

	private static boolean isFourOAK(Card[] x) {
		return (x[0].num == x[3].num || x[1].num == x[4].num);
	}

	private static boolean isStraightFlush(Card[] x)
	{
		return isStraight(x) && isFlush(x);
	}
	
	private static boolean isStraight(Card[] x)
	{
		for (int i = 0; i < 4; i++)
		{
			int delta = x[i].num - x[i+1].num;
			if (delta != -1)
			{
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean isFlush(Card[] x)
	{
		char suit = x[0].suit;
		for (int i = 1; i < 5; i++)
		{
			if (x[i].suit != suit)
			{
				return false;
			}
		}
		
		return true;
	}
	
	private static class Card implements Comparable<Card> {
		public int num;
		public char suit;
		
		public Card(int num, char suit)
		{
			this.num = num;
			this.suit = suit;
		}
		
		public boolean equals (Card b)
		{
			return (this.num == b.num && this.suit == b.suit);
		}
		
		public int compareTo(Card b)
		{
			if (this.num > b.num)
			{
				return 1;
			}
			else if (this.num < b.num)
			{
				return -1;
			}
			else 
			{
				return 0;
			}
		}
	}
}
