import java.util.*;
import java.io.*; 
public class Moocast {
    static int N; 
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("moocast.in"));
        PrintWriter pw = new PrintWriter(new File("moocast.out"));
        N = s.nextInt(); 
        PriorityQueue<Edge> edges = new PriorityQueue<Edge>();
        Point[] points = new Point[N]; 
        DisjointSet ds = new DisjointSet(); 
        for (int i = 0; i < N; i ++) {
            points[i] = new Point(s.nextInt(), s.nextInt(), i); 
            ds.makeSet(i, points[i].x, points[i].y); 
        }
        for (int i = 0; i < N; i ++)
            for (int j = 0; j < N; j ++)
                if (i != j)
                    edges.offer(new Edge(points[i], points[j], getDistance(points[i], points[j])));
        long smallest = 0; 
        while (!edges.isEmpty()) {
            Edge e = edges.poll(); 
            smallest = e.w; 
            ds.union(e.a.data, e.b.data);
            if (ds.getSize(e.a.data) == N)
                break; 
        }
        pw.println(smallest); 
        s.close();
        pw.close(); 
    }
    static long getDistance(Point a, Point b) {
        return (long) (Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2)); 
    }
    static class Point {
        Point parent; 
        int x, y, rank; 
        long data; 
        Point(int x, int y, long data) {
            this(x, y, data, null, 0); 
        }
        Point(int x, int y, long data, Point parent, int rank) {
            this.x = x;
            this.y = y;
            this.data = data; 
            this.parent = parent; 
            this.rank = rank; 
            if (this.parent == null)
                this.parent = this; 
        }
        public String toString() {
            return x + ", " + y; 
        }
    }
    static class Edge implements Comparable<Edge> {
        Point a, b; 
        long w;
        Edge(Point a, Point b, long w) {
            this.a = a;
            this.b = b;
            this.w = w; 
        }
        public int compareTo(Edge b) {
            return (w - b.w) > 0 ? 1 : -1; 
        }
    }
    static class DisjointSet {
        private Map<Long, Point> map = new HashMap<Long, Point>();
        private Map<Long, Integer> sizes = new HashMap<Long, Integer>();
        public void makeSet(long data, int x, int y) {
            Point p = new Point(x, y, data); 
            map.put(data, p);
            sizes.put(data, 1); 
        }
        public void union(long data1, long data2) {
            Point n1 = map.get(data1);
            Point n2 = map.get(data2);
            Point p1 = findSet(n1);
            Point p2 = findSet(n2);
            if(p1.data != p2.data) {
                int newSize = sizes.get(p1.data) + sizes.get(p2.data);
                if(p1.rank >= p2.rank) {
                    p1.rank = p1.rank == p2.rank ? p1.rank + 1 : p1.rank;
                    p2.parent = p1;
                    sizes.put(p1.data, newSize);
                }
                else {
                    p1.parent = p2;
                    sizes.put(p2.data, newSize);
                }
            }
        }
        public long findSet(long data) {
            return findSet(map.get(data)).data;
        }
        public Point findSet(Point n) {
            Point p = n.parent;
            if(p == n) 
                return p;
            n.parent = findSet(n.parent);
            return n.parent;
        }
        public int getSize(long data) {
            Point p = findSet(map.get(data));
            return sizes.get(p.data);
        }
    }
}
