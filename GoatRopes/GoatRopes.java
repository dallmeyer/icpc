import java.util.Arrays;
import java.util.Scanner;


public class GoatRopes {

	private static double epsilon = 0.0000001;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int n = in.nextInt();
		while (n != 0)
		{
			double[] 	x = new double[n],
						y = new double[n];
			double[][] dist = new double[n][n];
			
			for (int i = 0; i < n; i++)
			{
				x[i] = in.nextInt();
				y[i] = in.nextInt();
				
				for (int j = 0; j < i; j++)
				{
					dist[i][j] = distance(x[i], y[i], x[j], y[j]);
					dist[j][i] = dist[i][j];
				}
			}
			
			//n variables, k constraints, s slack vars
			int k = (n*(n-1))/2;
 			int s = k;
			
			double m[][] = new double[1+k][1+n+s];
			Arrays.fill(m[0], 0);
			for (int j = 1; j <= n; j++)	//fill n vars in cost func
			{
				m[0][j] = -1;
			}
//			for (int j = 1; j <= s; j++)	//fill s slack vars in cost func
//			{
//				m[0][n+j] = 0;
//			}
			
			int constraintNum = 1;
			for (int i = 0; i < n; i++)
			{
				for (int j = i+1; j < n; j++)
				{
					Arrays.fill(m[constraintNum], 0);
					m[constraintNum][0] = dist[i][j];
					m[constraintNum][1+i] = 1;
					m[constraintNum][1+j] = 1;
					m[constraintNum][1+n+(constraintNum-1)] = 1;
					
					constraintNum++;
				}
			}
			
			// Now, Simplex!!
			for(;;)
			{
				// First, find the pivot column - that's the one with
				// the most negative coefficient in the objective function
			    double smallest = -epsilon;
			    int pivotcol = -1;
			    for( int j=1; j<m[0].length; j++ )
			    {
			    	if( m[0][j]<smallest )
			    	{
			    		smallest = m[0][j];
			    		pivotcol = j;
			    	}
			    }
			    
			    // If there are no negative coefficients, we're done!
			    if( pivotcol<0 ) break;
			    
			    // Now find the pivot row. That's the one where the value divided by the 
			    // the coefficient in the pivot column is the smallest, but only if that
			    // coefficient is positive.
			    smallest = Double.MAX_VALUE;
			    int pivotrow = -1;
			    for( int i=1; i<m.length; i++ )
			    {
			    	if( m[i][pivotcol] > epsilon )
			    	{
			    	    double value = m[i][0]/m[i][pivotcol];
			    	    if( value<smallest )
			    	    {
			    	    	smallest = value;
			    	    	pivotrow = i;
			    	    }
			    	}
			    }
			    
			    // Now, divide the entire pivot row by the pivot value
			    double pivotval = m[pivotrow][pivotcol];
			    for( int j=0; j<m[pivotrow].length; j++ )
			    {
			        m[pivotrow][j] /= pivotval;	
			    }
			    
			    // Finally, pivot all of the other rows using the pivot row.
			    for( int i=0; i<m.length; i++ ) if( i!=pivotrow )
			    {       	        
			    	pivotval = m[i][pivotcol];
			    	for( int j=0; j<m[i].length; j++ )
				    {
			        	m[i][j] -= m[pivotrow][j] * pivotval;
			        }
			    }
			}
			
			System.out.printf("%.2f\n", m[0][0]);
			
			n = in.nextInt();
		}
	}
	
	private static double distance(double x1, double y1, double x2, double y2)
	{
		double 	xd = x1-x2,
				yd = y1-y2;
		return Math.sqrt(xd*xd+yd*yd);
	}

}
