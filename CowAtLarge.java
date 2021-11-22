import java.io.*;
import java.util.*;
public class CowAtLarge {
	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("atlarge.in"));
        PrintWriter pw = new PrintWriter(new File("atlarge.out"));
        int[] line = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = line[0], k = line[1]; 
        Graph g = new Graph(n); 
        for (int i = 0; i < n - 1; i ++) {
        	line = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        	g.addEdge(line[0], line[1]);
        }
        g.dfsKTE(k); 
        g.bfsNearestLeaf(); 
        g.dfsTotal(k);
        pw.println(g.numFarmers); 
        br.close();
        pw.close();
        System.exit(0);
	}
}
class Graph {
	ArrayList<Integer>[] edges; 
	int[] kToEdge, nodeToEdge; 
	int numFarmers; 
	Graph(int n) {
		numFarmers = 0; 
		kToEdge = new int[n + 1];
		nodeToEdge = new int[n + 1]; 
		edges = new ArrayList[n + 1]; 
		Arrays.fill(nodeToEdge, Integer.MAX_VALUE);
		for (int i = 0; i < n + 1; i ++)
			edges[i] = new ArrayList<Integer>(); 
	}
	void addEdge(int u, int v) {
		edges[u].add(v);
		edges[v].add(u); 
	}
	void dfsKTE(int k) {
		Stack<Integer> stack = new Stack<Integer>(); 
		boolean[] visited = new boolean[edges.length];
		stack.push(k); 
		while (!stack.isEmpty()) {
			int n = stack.pop(); 
			visited[n] = true; 
			for (int i : edges[n]) 
				if (!visited[i]) {
					kToEdge[i] = kToEdge[n] + 1; 
					stack.push(i); 
				}
		}
	}
	void bfsNearestLeaf() {
		Queue<Integer> queue = new LinkedList<Integer>(); 
		boolean[] visited = new boolean[edges.length]; 
		for (int i = 1; i < edges.length; i ++)
			if (edges[i].size() == 1) {
				nodeToEdge[i] = 0;
				queue.add(i); 
			}
		while (!queue.isEmpty()) {
			int n = queue.poll(); 
			visited[n] = true; 
			for (int i : edges[n]) 
				if (!visited[i]) {
					nodeToEdge[i] = nodeToEdge[n] + 1; 
					queue.add(i); 
				}
		}
	}
	void dfsTotal(int k) {
		Stack<Integer> stack = new Stack<Integer>(); 
		boolean[] visited = new boolean[edges.length]; 
		stack.push(k); 
		while (!stack.isEmpty()) {
			int n = stack.pop(); 
			visited[n] = true;
			for (int i : edges[n]) 
				if (!visited[i] && nodeToEdge[i] <= kToEdge[i]) 
					numFarmers ++;
				else if (!visited[i]) 
					stack.push(i); 
		}
	}
}