import java.math.BigInteger;
import java.util.Scanner;


public class PiecesOfLand {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int s = in.nextInt();
		for (int i = 0; i < s; i++)
		{
			BigInteger n = in.nextBigInteger();
			BigInteger nSq = n.multiply(n);
			
			/* 
			 * we get a new piece of land every time two lines intersect,
			 * and also we always get an additional piece of land from every line
			 * x(n) = (n choose 4) + (n choose 2) + 1
			 *      = (n^4-6n^3+23n^2-18n+24)/24
			 */
			BigInteger x = (nSq.multiply(nSq).subtract(
							BigInteger.valueOf(6).multiply(nSq).multiply(n)).add(
							BigInteger.valueOf(23).multiply(nSq)).subtract(
							BigInteger.valueOf(18).multiply(n)).add(
							BigInteger.valueOf(24))).divide(BigInteger.valueOf(24));
			
			System.out.println(x);
		}
	}

}
