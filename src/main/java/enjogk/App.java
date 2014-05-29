package enjogk;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Set<Integer> set1  = new HashSet<>(Arrays.asList(1, 2, 3));
        for (int a : set1){
        	Set<Integer> set2 = new HashSet<>(set1);
        	set2.remove(a);
        	for (int b : set2){
        		Set<Integer> set3 = new HashSet<>(set2);
        		set3.remove(b);
        		for (int c : set3){
        			System.out.println(String.format("a:%d\tb:%d\tc:%d\n", a, b, c));
        		}
        	}
        }
    }
}
