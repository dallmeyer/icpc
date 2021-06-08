/** This solution is unfinished **/
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;


public class SpeedyEscape {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int N = in.nextInt();
		for (int caseNo = 0; caseNo < N; caseNo++)
		{
			int n = in.nextInt(),
				m = in.nextInt(),
				e = in.nextInt();
			
			Node[] nodes = new Node[n];
			for (int i = 0; i < n; i++)
			{
				nodes[i] = new Node(i);
			}
			for (int i = 0; i < m; i++)
			{
				int a = in.nextInt() - 1,
					b = in.nextInt() - 1;
				double l = in.nextDouble() / 10;	//distances in hundred m, not km
				
				nodes[a].adj.add(nodes[b]);
				nodes[a].dist.add(l);
				nodes[b].adj.add(nodes[a]);
				nodes[b].dist.add(l);
			}
			ArrayList<Integer> exitList = new ArrayList<Integer>();
			for (int i = 0; i < e; i++)
			{
				int exit = in.nextInt() - 1;
				exitList.add(exit);
				nodes[exit].isExit = true;
			}
			
			int b = in.nextInt() - 1,
				p = in.nextInt() - 1;
			
			//determine police times
			Queue<Integer> policeQ = new ArrayDeque<Integer>();
			nodes[p].policeTime = 0;
			policeQ.add(p);
			while (!policeQ.isEmpty())
			{
				Node intersection = nodes[policeQ.poll()];
				for (int i = 0; i < intersection.adj.size(); i++)
				{
					double time = intersection.policeTime + intersection.dist.get(i) / 160.0;

					Node next = intersection.adj.get(i);
					//continue down path if beats best time
					if (next.policeTime > time)
					{
						next.policeTime = time;
						policeQ.add(next.num);
					}
				}
			}
			
			//determine min speeds
			Queue<Integer> brotherQ = new ArrayDeque<Integer>();
			nodes[b].brotherSpeed = 0;
			nodes[b].brotherDist = 0;
			brotherQ.add(b);
			while (!brotherQ.isEmpty())
			{
				Node intersection = nodes[brotherQ.poll()];
				for (int i = 0; i < intersection.adj.size(); i++)
				{
					Node next = intersection.adj.get(i);
					if (next.num == p)	//police blocking this path
					{
						continue;
					}
					
					
					double oldMinSpeed = Math.max(intersection.brotherSpeed, intersection.dist.get(i) / (next.policeTime - intersection.policeTime));
					double changedMinSpeed = (intersection.brotherDist + intersection.dist.get(i)) / next.policeTime;
					double newMinSpeed = Math.min(oldMinSpeed, changedMinSpeed);
					if (next.brotherDist == -1 || newMinSpeed < next.brotherSpeed)
					{
						next.brotherSpeed = newMinSpeed;
						next.brotherDist = intersection.brotherDist + intersection.dist.get(i);
						brotherQ.add(next.num);
					}
				}
			}
			
			double minExitSpeed = -1;
			for (Integer exit : exitList)
			{
				Node exitNode = nodes[exit];
				if (exitNode.brotherSpeed != -1)
				{
					if (minExitSpeed == -1 || exitNode.brotherSpeed < minExitSpeed)
					{
						minExitSpeed = exitNode.brotherSpeed;
					}
				}
			}
			
			if (minExitSpeed == -1)
			{
				System.out.println("IMPOSSIBLE");
			}
			else
			{
				System.out.println(minExitSpeed);
			}
		}
	}

	private static class Node
	{
		ArrayList<Node> adj;
		ArrayList<Double> dist;
		boolean isExit;
		int num;
		double policeTime;
		double brotherSpeed;
		double brotherDist;
		
		public Node(int i)
		{
			num = i;
			adj = new ArrayList<Node>();
			dist = new ArrayList<Double>();
			isExit = false;
			policeTime = Double.MAX_VALUE;
			brotherSpeed = -1;
			brotherDist = Double.MAX_VALUE;
		}
	}
	
}
