import java.util.*;
import java.io.*; 
public class VisitFJ {
    static int[][] fields; 
    static int N, T; 
    static HashMap<Node, ArrayList<Node>> map = new HashMap<>(); 
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(new File("visitfj.in"));
        PrintWriter pw = new PrintWriter(new File("visitfj.out"));
        N = scan.nextInt();
        T = scan.nextInt(); 
        fields = new int[N][N];
        Node[][][] nFields = new Node[N][N][3]; 
        for (int i = 0; i < N; i ++)
            for (int j = 0; j < N; j ++) {
                fields[i][j] = scan.nextInt();
                for (int k = 0; k < 3; k ++) {
                    nFields[i][j][k] = new Node(i, j, k, fields[i][j]); 
                    map.put(nFields[i][j][k], new ArrayList<Node>()); 
                }
            }
        for (int i = 0; i < N; i ++) 
            for (int j = 0; j < N; j ++) 
                for (int k = 0; k < 3; k ++) {
                    if (i - 1 >= 0)
                        nFields[i][j][k].addEdge(nFields[i - 1][j][(k + 1) % 3]); 
                    if (j - 1 >= 0)
                        nFields[i][j][k].addEdge(nFields[i][j - 1][(k + 1) % 3]); 
                    if (i + 1 <= N - 1)
                        nFields[i][j][k].addEdge(nFields[i + 1][j][(k + 1) % 3]); 
                    if (j + 1 <= N - 1)
                        nFields[i][j][k].addEdge(nFields[i][j + 1][(k + 1) % 3]); 
                }
        int[][][] d = dijkstra(nFields[0][0][2]);
        pw.println(Math.min(Math.min(d[N - 1][N - 1][0], d[N - 1][N - 1][1]), d[N - 1][N - 1][2]));
        scan.close();
        pw.close(); 
    }
    static int[][][] dijkstra(Node src) {
        int[][][] d = new int[N][N][3]; 
        for (int i = 0; i < N; i ++)
            for (int j = 0; j < N; j ++)
                for (int k = 0; k < 3; k ++)
                    d[i][j][k] = Integer.MAX_VALUE; 
        d[src.i][src.j][src.p] = 0; 
        PriorityQueue<Node> pq = new PriorityQueue<Node>(); 
        pq.offer(src); 
        boolean[][][] visited = new boolean[N][N][3]; 
        while (!pq.isEmpty()) {
            Node n = pq.poll(); 
            visited[n.i][n.j][n.p] = true; 
            for (Node a : map.get(n))
                if (d[n.i][n.j][n.p] + T + (a.p == 2 ? fields[a.i][a.j] : 0) < d[a.i][a.j][a.p]) {
                    d[a.i][a.j][a.p] = d[n.i][n.j][n.p] + T + (a.p == 2 ? fields[a.i][a.j] : 0); 
                    pq.offer(a); 
                }
        }
        return d; 
    }
    static class Node implements Comparable<Node> {
        int i, j, p, w; 
        Node(int i, int j, int p, int w) {
            this.i = i;
            this.j = j;
            this.p = p; 
            this.w = w; 
        }
        void addEdge(Node n) {
            map.get(this).add(n); 
        }
        public int compareTo(Node b) {
            int aval = T + (p == 2 ? w : 0), bval = T + (b.p == 2 ? b.w : 0); 
            return aval - bval; 
        }
    }
}