import java.util.*;
import java.io.*; 
import java.awt.*; 
public class CowChecklist {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("checklist.in"));
        PrintWriter pw = new PrintWriter(new File("checklist.out"));
        int H = s.nextInt(), G = s.nextInt(); 
        long[][][] dp = new long[H + 1][G + 1][2]; 
        Point[] h = new Point[H + 1], g = new Point[G + 1]; 
        for (int i = 1; i <= H; i ++)
            h[i] = new Point(s.nextInt(), s.nextInt()); 
        for (int i = 1; i <= G; i ++)
            g[i] = new Point(s.nextInt(), s.nextInt()); 
        h[0] = h[1]; 
        g[0] = new Point(10000, 10000); 
        for (int i = 0; i <= H; i ++)
            for (int j = 0; j <= G; j ++)
                for (int k = 0; k < 2; k ++)
                    dp[i][j][k] = Integer.MAX_VALUE; 
        dp[0][0][0] = 0; 
        for (int i = 0; i <= H; i ++)
            for (int j = 0; j <= G; j ++) {
                if (i < H) 
                    dp[i + 1][j][0] = Math.min(dp[i][j][0] + getDistance(h[i], h[i + 1]),
                            dp[i][j][1] + getDistance(g[j], h[i + 1])); 
                if (j < G && i != 0) 
                    dp[i][j + 1][1] = Math.min(dp[i][j][0] + getDistance(g[j + 1], h[i]),
                            dp[i][j][1] + getDistance(g[j + 1], g[j]));
            }
        pw.println(dp[H][G][0]); 
        s.close();
        pw.close(); 
    }
    static int getDistance(Point a, Point b) {
        return (int) (Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2)); 
    }
}
