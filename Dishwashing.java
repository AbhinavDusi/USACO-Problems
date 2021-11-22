import java.util.*; 
import java.io.*;
public class Dishwashing {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("dishes.in"));
        PrintWriter pw = new PrintWriter(new File("dishes.out")); 
        int N = s.nextInt(), pre = N; 
        int[] a = new int[N];
        for (int i = 0; i < N; i ++) 
            a[i] = s.nextInt(); 
        boolean[] b = new boolean[N + 1]; 
        TreeSet<Integer> ts = new TreeSet<>(); 
        ArrayList<Integer> list = new ArrayList<>(); 
        list.add(Integer.MIN_VALUE); 
        for (int i = 0; i < N; i ++) {
            int curr = a[i]; 
            if (curr > list.get(list.size() - 1)) {
                if (ts.higher(curr) == null) {
                    ts.add(curr); 
                    b[curr] = true; 
                }
                else {
                    int n = ts.higher(curr); 
                    if (b[n]) {
                        ts.add(curr);
                        b[curr] = true; 
                        b[n] = false; 
                    }
                    else {
                        ts.add(curr); 
                        while (ts.first() != curr) {
                            b[ts.first()] = false;
                            list.add(ts.pollFirst()); 
                        }
                        b[curr] = true; 
                    }
                }
            }
            else {
                pre = i;
                break; 
            }
        }
        pw.println(pre); 
        s.close();
        pw.close(); 
    }
}