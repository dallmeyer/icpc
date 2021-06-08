import java.util.*;
public class autobox {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 42);
        map.put(2, 42);
        System.out.println(map.get(1) == map.get(2));        

        map.put(1, 1042);
        map.put(2, 1042);
        System.out.println(map.get(1) == map.get(2));        
        System.out.println(map.get(1).equals(map.get(2)));   
        System.out.println(map.get(1) == (int) map.get(2));  
    }
}