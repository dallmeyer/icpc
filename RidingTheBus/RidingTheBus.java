/* Matt Dallmeyer - 2003 World Finals (C) */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;


public class RidingTheBus {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int caseNo = 1;
		int k = in.nextInt();
		while (k > 0)
		{
			if (caseNo > 1)
				System.out.printf("\n");
			
			double x1 = in.nextDouble(), y1 = in.nextDouble(),
				   x2 = in.nextDouble(), y2 = in.nextDouble();
			
			int scale = pow(3, k) - 1;
			
			int r1 = new BigDecimal(y1*scale).setScale(0, RoundingMode.HALF_DOWN).intValue();
			int c1 = new BigDecimal(x1*scale).setScale(0, RoundingMode.HALF_DOWN).intValue();
			int r2 = new BigDecimal(y2*scale).setScale(0, RoundingMode.HALF_DOWN).intValue();
			int c2 = new BigDecimal(x2*scale).setScale(0, RoundingMode.HALF_DOWN).intValue();
			
			int n1 = getNumSkipped(r1, c1, k, true, true);
			int n2 = getNumSkipped(r2, c2, k, true, true);
			
			double d = Math.abs(n1 - n2) + dist(scale*x1, scale*y1, c1, r1) +  dist(scale*x2, scale*y2, c2, r2);
			d /= ((double) scale);
			
			System.out.printf("Case " + caseNo + ".  Distance is %.4f\n", d);
			
			caseNo++;
			k = in.nextInt();
		}
	}
	
	private static double dist(double x1, double y1, double x2, double y2)
	{
		double t1 = x1 - x2,
				t2 = y1 - y2;
		t1 *= t1;
		t2 *= t2;
		
		return Math.sqrt(t1 + t2);
	}
	
	private static int getNumSkipped(int r, int c, int k, boolean up, boolean s)
	{
		if (k == 0)
			return 0;
	
		int p3 = pow(3, k-1);
		
		int n = 0;
		int bigR = r / p3;
		int bigC = c / p3;
		
		int region = getRegion(bigR, bigC, up, s);
		
		n += pow(9,k-1) * (region-1);
		
		if (bigR % 2 == 1)
			s = !s;
		if (bigC % 2 == 1)
		{
			s = !s;
			up = !up;
		}
		
		n += getNumSkipped(r % p3, c % p3, k-1, up, s);
		
		return n;
	}
	
	private static int pow(int base, int exp)
	{
		int ans = 1;
		for (int i = 0; i < exp; i++)
		{
			ans *= base;
		}
		
		return ans;
	}
	
	private static int getRegion(int r, int c, boolean up, boolean s)
	{
		int[][] regions;
		
		if (up)
		{
			if (s)
			{
				regions = new int[][]{	{1, 2, 3},
										{6, 5, 4},
										{7, 8, 9}};
			}
			else
			{
				regions = new int[][]{	{3, 2, 1},
										{4, 5, 6},
										{9, 8, 7}};
			}
		}
		else
		{
			if (s)
			{
				regions = new int[][]{	{9, 8, 7}, 
										{4, 5, 6}, 
										{3, 2, 1}};
			}
			else
			{
				regions = new int[][]{	{7, 8, 9}, 
										{6, 5, 4}, 
										{1, 2, 3}};
			}
		}
		
		return regions[r][c];
	}

}
