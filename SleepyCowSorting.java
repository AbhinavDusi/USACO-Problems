import java.io.*;
import java.util.*;
public class SleepyCowSorting {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("sleepy.in"));
        PrintWriter pw = new PrintWriter(new File("sleepy.out"));
        ArrayList<Integer> sorted = new ArrayList<Integer>(); 
        ArrayList<Integer> list = new ArrayList<Integer>(); 
        int N = s.nextInt(), ans = N - 1; 
        for (int i = 0; i < N; i ++) 
            list.add(s.nextInt()); 
        sorted.add(list.get(N - 1)); 
        for (int i = N - 2; i >= 0; i --) 
            if (list.get(i) < list.get(i + 1)) {
                ans --; 
                sorted.add(0, list.get(i)); 
            }
            else 
                break; 
        for (int i = N - 1; i >= N - sorted.size(); i --) 
            list.remove(i); 
        pw.println(ans);
        for (int i = 0; i < list.size(); i ++) {
            int pos = Collections.binarySearch(sorted, list.get(i)); 
            pos = pos >= 0 ? pos : - pos - 1; 
            sorted.add(pos, list.get(i)); 
            pw.print(list.size() - i - 1 + pos);
            if (i != list.size() - 1)
                pw.print(" "); 
        }
        s.close();
        pw.close();
        System.exit(0); 
    }
}