import java.util.*;
import java.io.*; 
public class LasersAndMirrors {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("lasers.in"));
        PrintWriter pw = new PrintWriter(new File("lasers.out"));
        int N = s.nextInt(), count = 2; 
        Graph g = new Graph(); 
        ArrayList<Node> nodes = new ArrayList<>(); 
        Node src = new Node(s.nextInt(), s.nextInt(), 2);
        Node dest = new Node(s.nextInt(), s.nextInt(), 2);
        g.makeNode(src, 0); 
        g.makeNode(dest, 1); 
        nodes.add(src);
        nodes.add(dest); 
        for (int i = 0; i < N; i ++) {
            int x = s.nextInt(), y = s.nextInt(); 
            Node vert = new Node(x, y, 0);
            Node hor = new Node(x, y, 1); 
            nodes.add(vert);
            nodes.add(hor); 
            g.makeNode(vert, count ++); 
            g.makeNode(hor, count ++); 
            g.addEdge(vert, hor, 1); 
        }
        yConnections(g, nodes); 
        xConnections(g, nodes); 
        pw.println(g.bfs(src, dest)); 
        s.close();
        pw.close(); 
    }
    static void xConnections(Graph g, ArrayList<Node> xNodes) {
        Map<Integer, Node> lastX = new HashMap<>(); 
        for (int i = 0; i < xNodes.size(); i ++) {
            Node n = xNodes.get(i); 
            if (lastX.keySet().contains(n.y) && n.d != 0) {
                g.addEdge(lastX.get(n.y), n, 0); 
            }
            if (n.d != 0) 
                lastX.put(n.y, n); 
        }
    }
    static boolean equals(Node a, Node b) {
        return a.x == b.x && a.y == b.y; 
    }
    static void yConnections(Graph g, ArrayList<Node> yNodes) {
        Map<Integer, Node> lastY = new HashMap<>(); 
        for (int i = 0; i < yNodes.size(); i ++) {
            Node n = yNodes.get(i); 
            if (lastY.keySet().contains(n.x) && n.d != 1) {
                g.addEdge(lastY.get(n.x), n, 0); 
            }
            if (n.d != 1) 
                lastY.put(n.x, n); 
        }
    }
    static class Sort implements Comparator<Node> {
        int by;
        Sort(int by) {
            this.by = by; 
        }
        public int compare(Node a, Node b) {
            if (by == 1)
                return a.x - b.x; 
            else 
                return a.y - b.y; 
        }
    }
    static class Node {
        int x, y, d, ds; 
        boolean v; 
        Node (int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d; 
            this.ds = Integer.MAX_VALUE; 
            this.v = false; 
        }
    }
    static class Graph {
        Map<Node, Integer> map = new HashMap<>(); 
        Map<Integer, Node> map2 = new HashMap<>(); 
        Map<Integer, ArrayList<int[]>> adj = new HashMap<>(); 
        void makeNode(Node n, int val) {
            map.put(n, val); 
            map2.put(val, n); 
            adj.put(val, new ArrayList<int[]>()); 
        }
        void addEdge(Node a, Node b, int w) {
            int aval = map.get(a), bval = map.get(b); 
            adj.get(aval).add(new int[]{bval, w}); 
            adj.get(bval).add(new int[]{aval, w}); 
        }
        int bfs(Node src, Node dest) {
            Deque<Node> dq = new LinkedList<>(); 
            src.ds = 0;
            dq.offer(src);
            while (!dq.isEmpty()) {
                Node curr = dq.poll(); 
                int id = map.get(curr); 
                curr.v = true; 
                for (int[] i : adj.get(id)) {
                    Node n = map2.get(i[0]); 
                    n.ds = Math.min(n.ds, curr.ds + i[1]); 
                    if (i[1] == 1 && !n.v) 
                        dq.offerLast(n);
                    else if (!n.v)
                        dq.offerFirst(n); 
                }
            }
            return dest.ds; 
        }
    }
    static class CompareByDist implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return a.ds - b.ds; 
        }
    }
}