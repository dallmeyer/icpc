import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Chemistry {

	public static void main(String[] args) {
		Chemistry me = new Chemistry();
		me.runAll();
	}

	char[] s;
	Scanner in = new Scanner(System.in);
	int i;

	public void runAll() {
		while (in.hasNext()) {
			String newString = in.nextLine();
			if (newString == "") {
				return;
			}
			s = newString.toCharArray();
			runOne();

		}
	}

	public void runOne() {

		i = 0;
		HashMap<String, Integer> map = subParse();
		ArrayList<NameNum> atoms = new ArrayList<NameNum>();

		for (String name : map.keySet()) {
			NameNum newNN = new NameNum();
			newNN.name = name;
			newNN.num = map.get(name);
			atoms.add(newNN);
		}

		Collections.sort(atoms);

		StringBuilder sb = new StringBuilder();
		for (NameNum nn : atoms) {
			if (nn.num > 1) {
				sb.append(nn.num);
			}
			sb.append(nn.name);
			sb.append("+");
		}
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb);
	}

	public HashMap<String, Integer> subParse() {

		HashMap<String, Integer> myMap = new HashMap<String, Integer>();

		while (true) {

			if (i >= s.length) {
				return myMap;
			}

			if (s[i] == ')') {
				i++;
				return myMap;
			}

			if (s[i] >= 'A' && s[i] <= 'Z') {
				NameNum res = parseAtom();
				AddOrIncrement(myMap, res.num, res.name, 1);
				continue;
			}

			if (s[i] == '(') {
				i++;
				HashMap<String, Integer> subMap = subParse();

				int mult = 1;
				if (i < s.length && s[i] >= '0' && s[i] <= '9') {
					mult = parseNum();
				}

				AddOrInrecmentAll(subMap, myMap, mult);
				continue;
			}

		}

	}

	public NameNum parseAtom() {

		String name = parseName();
		int num = 1;
		if (i < s.length && s[i] >= '0' && s[i] <= '9') {
			num = parseNum();
		}

		NameNum res = new NameNum();
		res.name = name;
		res.num = num;
		return res;
	}

	public Integer parseNum() {
		char[] buf = new char[20];
		int j = 0;
		while (i < s.length && s[i] >= '0' && s[i] <= '9') {
			buf[j] = s[i];
			i++;
			j++;
		}
		int num = Integer.parseInt(new String(Arrays.copyOf(buf, j)));
		return num;
	}

	public String parseName() {
		char[] buf = new char[100];
		int j = 0;
		buf[j] = s[i];
		i++;
		j++;
		while (i < s.length && s[i] >= 'a' && s[i] <= 'z') {
			buf[j] = s[i];
			i++;
			j++;
		}
		String name = new String(Arrays.copyOf(buf, j));
		return name;
	}

	public void AddOrIncrement(HashMap<String, Integer> to, int num,
			String name, int mult) {

		if (to.containsKey(name)) {
			to.put(name, to.get(name) + num * mult);
		} else {
			to.put(name, num * mult);
		}

	}

	public void AddOrInrecmentAll(HashMap<String, Integer> from,
			HashMap<String, Integer> to, int mult) {
		for (String name : from.keySet()) {
			AddOrIncrement(to, from.get(name), name, mult);
		}
	}

	private class NameNum implements Comparable<NameNum> {
		int num;
		String name;

		@Override
		public int compareTo(NameNum o) {
			return this.name.compareTo(o.name);
		}

	}

}
