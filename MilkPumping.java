import java.util.*;
import java.io.*; 

public class MilkPumping {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("pump.in")); 
        PrintWriter pw = new PrintWriter(new File("pump.out"));

        String[] tokens = s.nextLine().split(" ");
        int N = Integer.parseInt(tokens[0]);
        int M = Integer.parseInt(tokens[1]); 

        for (int i = 0; i < M; i++) {
            tokens = s.nextLine().split(" ");

        }

        s.close();
        pw.close();
    }
}