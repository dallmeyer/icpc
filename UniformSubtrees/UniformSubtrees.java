import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class UniformSubtrees {
	private static int sInd;
	
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));

		String line = in.next();
		while (!line.equals("0")) {
			sInd = 0;
			Node root = parse(line);

			ArrayList<Integer> uniformTree = new ArrayList<Integer>();
			uniformTree.add(0);

			while (true) {
				if (passes(root, uniformTree, 0)) {
					log.write(printTree(uniformTree) + "\n");

					uniformTree
							.add(uniformTree.remove(uniformTree.size() - 1) + 1);
					uniformTree.add(0);
				} else if (uniformTree.size() == 2) {
					break;
				} else {
					uniformTree.remove(uniformTree.size() - 2);

					uniformTree.set(uniformTree.size() - 2,
							uniformTree.get(uniformTree.size() - 2) + 1);
				}
			}

			log.flush();
			
			line = in.next();
		}
	}

	private static String printTree(ArrayList<Integer> uniformTree) {
		String s = "";
		for (Integer i : uniformTree) {
			s += i + " ";
		}
		s = s.substring(0, s.length()-1);
		return s;
	}

	private static boolean passes(Node root, ArrayList<Integer> uniformTree,
			int i) {
		if (uniformTree.size() == 0)
		{
			return false;
		}
		
		if (i >= uniformTree.size())
			return false;

		int n = uniformTree.get(i);
		if (n == 0) {
			return true;
		}

		if (root.children.size() >= n) {
			int count = 0;
			for (Node c : root.children) {
				if (passes(c, uniformTree, i + 1)) {
					count++;
				}
			}

			if (count >= n) {
				return true;
			}
		}

		return false;
	}


	private static Node parse(String line) {
		Node n = new Node();

		sInd++; // parse opening parens
		while (line.charAt(sInd) == '(') {
			n.children.add(parse(line));
		}
		sInd++; // parse closing parens

		return n;
	}

	private static class Node {
		ArrayList<Node> children;

		public Node() {
			this.children = new ArrayList<Node>();
		}
	}
}
