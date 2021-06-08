import java.util.*;
import java.math.*;
import java.io.*;

/** @author Godmar 
 *
 * Like Fabian said, just roll them out top and right...
 *
 * passes ICPC in 1.678 seconds.
 *
 * Notes: it would seem reasonable to dedup the strings, but all that
 * does is increase the overall runtime to 2.688 seconds.
 */

public class SA_Back {
    public static void main(String []av) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String line;
        // handle both single and multiple test cases for online archive
        while ((line = r.readLine()) != null) {
            int n = Integer.parseInt(line);
            SA_Back s = new SA_Back(n, r.readLine());
            System.out.println(s.answer);
        }
    }

    String answer;
    @SuppressWarnings("unchecked")
    List<String>  [][] byLeft = new List[2][26]; // left

    @SuppressWarnings("unchecked")
    List<String>  [][] byBottom = new List[2][26]; // bottom

    int signOf(char c) { return c == '+' ? 1 : 0; }

    void add(String p) {
        if (p.charAt(4) != '0')
            byBottom[signOf(p.charAt(5))][p.charAt(4) - 'A'].add(p);

        if (p.charAt(6) != '0')
            byLeft[signOf(p.charAt(7))][p.charAt(6) - 'A'].add(p);
    }

    SA_Back(int n, String line) {
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 26; j++) {
                byLeft[i][j] = new ArrayList<String>();
                byBottom[i][j] = new ArrayList<String>();
            }
                
        StringTokenizer st = new StringTokenizer(line);
        // T R B L
        for (int i = 0; i < n; i++) {
            String p = st.nextToken();
            add(p);     // now add reflections and rotations
            String q = p.substring(0, 2) + p.substring(6, 8) + p.substring(4, 6) + p.substring(2, 4);
            add(q);
            for (int j = 0; j < 3; j++) {
                add(p = p.substring(6) + p.substring(0, 6));
                add(q = q.substring(6) + q.substring(0, 6));
            }
        }

        // could have up to 240,000 pieces to start with.
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 26; j++) {
                for (String p : byLeft[i][j]) {
                    try {
                        explore(p, new boolean[2][26], new boolean[2][26]);
                    } catch (Unbounded __) {
                        answer = "unbounded";
                        return;
                    }
                }
            }
        }

        answer = "bounded";
    }

    void explore(String p, boolean [][]top, boolean [][]right) {
        char topLetter = p.charAt(0);
        if (topLetter != '0') {
            int topSign = signOf(p.charAt(1));
            if (top[topSign][topLetter - 'A'])
                throw new Unbounded();

            top[topSign][topLetter - 'A'] = true;
            for (String q : byBottom[1 - topSign][topLetter - 'A'])
                explore(q, top, right);

            top[topSign][topLetter - 'A'] = false;
        }

        char rightLetter = p.charAt(2);
        if (rightLetter != '0') {
            int rightSign = signOf(p.charAt(3));
            if (right[rightSign][rightLetter - 'A'])
                throw new Unbounded();

            right[rightSign][rightLetter - 'A'] = true;
            for (String q : byLeft[1 - rightSign][rightLetter - 'A'])
                explore(q, top, right);

            right[rightSign][rightLetter - 'A'] = false;
        }
    }

    static class Unbounded extends Error { }

    // debugging left over
    void o(String p) {
        System.out.printf("  %s  %n", p.substring(0, 2));
        System.out.printf("%s  %s%n", p.substring(6, 8), p.substring(2, 4));
        System.out.printf("  %s  %n", p.substring(4, 6));
    }
}