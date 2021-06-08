import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class emergency {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int numDocs = in.nextInt();
		int docStart = in.nextInt();
		int caseNum = 0;
		
		while(numDocs != 0 && docStart != 0)
		{
			if (caseNum != 0)
				System.out.println();
			caseNum++;
			
			ArrayList<Patient> notArrived = new ArrayList<Patient>();
			ArrayList<Patient> waiting = new ArrayList<Patient>();
			ArrayList<Patient> withDoctor = new ArrayList<Patient>();
			
			int patientArrival = in.nextInt();
			while (patientArrival != -1)
			{
				Patient p = new Patient(patientArrival);
				int pri = in.nextInt(), dur = in.nextInt();
				while (pri != 0 && dur != 0)
				{
					p.treatments.add(new Treatment(pri, dur));
					pri = in.nextInt();
					dur = in.nextInt();
				}
				
				notArrived.add(p);
				
				patientArrival = in.nextInt();
			}
			
			ArrayList<Patient> finished = new ArrayList<Patient>();
			
			int time = docStart;
			while (notArrived.size() > 0 || waiting.size() > 0 || withDoctor.size() > 0)
			{		
				//let patients arrive
				for (int i = 0; i < notArrived.size(); i++)
				{
					Patient p = notArrived.get(i);
					if (p.arrive <= time)
					{
						waiting.add(notArrived.remove(i--));
					}
				}
				
				//let patients return from doctors office
				for (int i = 0; i < withDoctor.size(); i++)
				{
					Patient p = withDoctor.get(i);
					if (p.treatments.get(0).end == time)
					{
						p.treatments.remove(0);
						if (p.treatments.size() == 0)
						{
							p.finish(time);
							finished.add(withDoctor.remove(i--));
						}
						else
						{
							waiting.add(withDoctor.remove(i--));
						}
					}
				}
				
				Collections.sort(waiting);
				
				//send patients to doctor
				while (withDoctor.size() < numDocs && waiting.size() > 0)
				{
					Patient p = waiting.remove(0);
					p.treatments.get(0).start(time);
					withDoctor.add(p);
				}
				
				time++;
			}
			
			Collections.sort(finished);
			
			System.out.println("Case " + caseNum + ":");			
			for (int i = 0; i < finished.size(); i++)
			{
				Patient p = finished.get(i);
				System.out.printf("Patient %d released at clock = %d", p.arrive, p.release);
				if (i < finished.size()-1)
				{
					System.out.println();
				}
			}			
			
			numDocs = in.nextInt();
			docStart = in.nextInt();
		}
	}
	
	private static class Patient implements Comparable<Patient>
	{
		public int arrive;
		public ArrayList<Treatment> treatments;
		public int release = -1;
		
		public Patient(int a)
		{
			this.arrive = a;
			treatments = new ArrayList<Treatment>();
		}

		public int compareTo(Patient o) {
			int r = this.release - o.release;
			if (r == 0)
			{		
				if (this.treatments.size() > 0)
				{
					int p1 = this.treatments.get(0).priority;
					int p2 = o.treatments.get(0).priority;
					
					if (p1 - p2 != 0)
						return p2 - p1;
					else
						return arrive - o.arrive;
				}
				
				return arrive - o.arrive;
			}
			
			return r;
		}
		
		public void finish(int e)
		{
			release = e;
		}
	}
	
	private static class Treatment
	{
		public int priority;
		public int duration;
		public int end;
		
		public Treatment(int p, int d)
		{
			this.priority = p;
			this.duration = d;
		}
		
		public void start(int s)
		{
			this.end = s + duration;
		}
	}

}
