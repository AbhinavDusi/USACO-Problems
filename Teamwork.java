import java.io.*;
import java.util.*;
public class Teamwork {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("/Users/abhinavdusi/Desktop/in.in"));
        PrintWriter pw = new PrintWriter(new File("/Users/abhinavdusi/Desktop/out.out"));
        int N = s.nextInt(), K = s.nextInt();
        int[] skills = new int[N]; 
        for (int i = 0; i < N; i ++) 
            skills[i] = s.nextInt(); 
        int[] max = new int[N]; 
        for (int i = 0; i < N; i ++) {
            int curr = skills[i]; 
            for (int j = 0; j < K; j ++) 
                if (i - j >= 0) {
                    curr = Math.max(skills[i - j], curr); 
                    max[i] = Math.max(max[i], (curr * (j + 1)) + (i - j - 1 > 0 ? max[i - j - 1] : 0)); 
                }
        }
        pw.println(Arrays.toString(max)); 
        s.close();
        pw.close();
    }
}