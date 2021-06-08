import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;


public class CryptKicker {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int n = Integer.parseInt(in.nextLine());
		ArrayList<String> dict = new ArrayList<String>();
		
		for (int i = 0; i < n; i++)
		{
			dict.add(in.nextLine());
		}
		Collections.sort(dict);
		
		while (in.hasNext())
		{
			String cipherStr = in.nextLine();
			ArrayList<String> cipher = new ArrayList<String>();
			Scanner strIn = new Scanner(cipherStr);
			while (strIn.hasNext())
			{
				cipher.add(strIn.next());
			}
			
			int m = cipher.size();
			HashMap<Character, Character> crypt = new HashMap<Character, Character>();
			
			if (!runMe(crypt, 0, cipher, dict))
			{
				for (int i = 0; i < 26; i++)
				{
					crypt.put((char) ('a' + i), '*');
				}
				
				decrypt(crypt, cipher);
			}
			
		}
	}
	
	private static boolean runMe(HashMap<Character, Character> crypt, int i, ArrayList<String> cipher, ArrayList<String> dict)
	{
		if (i == cipher.size())
		{
			decrypt(crypt, cipher);
			return true;
		}
		
		String s = cipher.get(i);
		for (int j = 0; j < dict.size(); j++)
		{
			String d = dict.get(j);
			if (d.length() != s.length())
				continue;
			
			HashMap<Character, Character> cryptClone = (HashMap<Character, Character>) crypt.clone();
			if (match(d, s, cryptClone))
			{
				if (runMe(cryptClone, i+1, cipher, dict))
					return true;
			}
		}
		
		return false;
	}
	
	private static void decrypt(HashMap<Character, Character> crypt, ArrayList<String> cipher)
	{
		for (int i = 0; i < cipher.size(); i++)
		{
			String s = cipher.get(i);
			for (int j = 0; j < s.length(); j++)
			{
				System.out.print(crypt.get(s.charAt(j)));
			}
			
			if (i != cipher.size()-1)
				System.out.print(" ");
		}
		System.out.println();
	}
	
	private static boolean match(String d, String s, HashMap<Character, Character> crypt)
	{
		for (int i = 0; i < d.length(); i++)
		{
			char c = s.charAt(i);
			char dc = d.charAt(i);
			
			if (crypt.containsKey(c))
			{
				if (crypt.get(c) != dc)
				{
					return false;
				}
			}
			else if (crypt.containsValue(dc))
			{
				return false;
			}
			else
			{
				crypt.put(c, dc);
			}
		}
		
		return true;
	}

}
