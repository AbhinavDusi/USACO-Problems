import java.io.*;
import java.util.*; 
public class GrassPlanting {
	public static void main(String[] args) throws IOException {
		Scanner s = new Scanner(new File("planting.in"));
        PrintWriter pw = new PrintWriter(new File("planting.out"));
        int N = s.nextInt(), max = 0; 
        int[] num = new int[N + 1]; 
        for (int i = 0; i < N - 1; i ++) 
        	max = Math.max(Math.max(max, ++ num[s.nextInt()]), ++ num[s.nextInt()]); 
        pw.println(max + 1);
        s.close();
        pw.close();
        System.exit(0);
	}
}