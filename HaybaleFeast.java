import java.io.*;
import java.util.*;
public class HaybaleFeast {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("hayfeast.in"));
        PrintWriter pw = new PrintWriter(new File("hayfeast.out"));
        int N = s.nextInt(), l = 0;
        long M = s.nextLong(), currSum = 0, min = Integer.MAX_VALUE; 
        long[] flavor = new long[N]; 
        MaxQuery mq = new MaxQuery(N); 
        for (int i = 0; i < N; i ++) {
            flavor[i] = s.nextLong();
            mq.updateMax(0, N - 1, i, s.nextLong(), 0); 
        }
        for (int r = 0; r < N; r ++) {
            currSum += flavor[r]; 
            if (currSum >= M) 
                min = Math.min(min, mq.getMax(0, N - 1, l, r, 0));
            while (currSum >= M) 
                currSum -= flavor[l ++]; 
        } 
        pw.println(min);
        s.close();
        pw.close();
    }
}
class MaxQuery {
    private long[] max; 
    public MaxQuery (int n) {
        max = new long[1<<((int)Math.ceil(Math.log(n)/Math.log(2))+1)]; 
    }
    public void updateMax (int l, int r, int i, long val, int pos) {
        if (i < l || i > r) 
            return; 
        max[pos] = val; 
        if (r != l) {
            updateMax(l, (r+l)/2, i, val, 2*pos+1); 
            updateMax((r+l)/2+1, r, i, val, 2*pos+2); 
            max[pos] = Math.max(max[2*pos+1], max[2*pos+2]); 
        } 
    }
    public long getMax (int l, int r, int ql, int qr, int pos) {
        if (ql <= l && qr >= r) 
            return max[pos];
        if (r < ql || l > qr) 
            return Integer.MIN_VALUE; 
        return Math.max(getMax(l, (r+l)/2, ql, qr, 2*pos+1), getMax((r+l)/2+1, r, ql, qr, 2*pos+2));
    }
}