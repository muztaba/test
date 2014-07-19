import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by seal on 7/14/14.
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        TaskB solver = new TaskB();
        solver.solve(in);
    }
}

class TaskB {
    public void solve(Scanner in) {
        String str = in.nextLine();
        int k = in.nextInt();
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < 26 ; i++) {
            char c =(char) ('a' + i);
            int x = in.nextInt();
            if(max < x) max = x;
            map.put(c, x);
        }
        int ans = 0;
        for(int i = 0; i < str.length() + k; i++){
            if(i < str.length()) {
                char c = str.charAt(i);
                ans += map.get(c) * (i + 1);
            }else {
                ans += max * (i+1);
            }

        }
        System.out.println(ans);

    }
}

class TaskA{
    public void solver(Scanner in) {
        int p = in.nextInt();
        int n = in.nextInt();
        int ans = 0;
        boolean t = true;
        boolean[] a = new boolean[p+1];
        for(int i = 1; i <= n; i++){
            int x = in.nextInt();
            int hashVal = hashVal(x, p);
            if(a[hashVal] == false) {
                a[hashVal] = true;
                ans++;
            }
            else {
                System.out.println(i);
                t = false;
                break;
            }
        }
        if(t) System.out.println("-1");
    }

    int hashVal(int x, int p) {
        return x % p;
    }

}