import java.util.*; 
import java.io.*;
public class CowLand {
    static int[] euler; 
    static int pos = 0; 
    static ArrayList<Integer> eulerLcaList = new ArrayList<>(); 
    static HashMap<Integer, Integer> fe = new HashMap<>();
    static ArrayList<Integer> nto = new ArrayList<>(); 
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("cowland.in"));
        PrintWriter pw = new PrintWriter(new File("cowland.out")); 
        int N = s.nextInt(), Q = s.nextInt(); 
        Node[] nodes = new Node[N + 1]; 
        for (int i = 1; i <= N; i ++) 
            nodes[i] = new Node(s.nextInt(), i); 
        for (int i = 0; i < N - 1; i ++) {
            int u = s.nextInt(), v = s.nextInt();
            if (v != 1)
                nodes[u].children.add(nodes[v]);
            if (u != 1)
                nodes[v].children.add(nodes[u]);
        }
        euler = new int[2 * N];
        dfs(nodes[1], new boolean[N + 1]);
        int[] eulerLca = new int[eulerLcaList.size()]; 
        for (int i = 0; i < eulerLca.length; i ++) 
            eulerLca[i] = eulerLcaList.get(i); 
        STM rmq = new STM(); 
        rmq.constructST(eulerLca, eulerLca.length);
        STX rxq = new STX(); 
        rxq.constructST(euler, euler.length);
        for (int i = 0; i < Q; i ++) 
            if (s.nextInt() == 1) {
                int n = s.nextInt(), newVal = s.nextInt(); 
                rxq.updateValue(euler, euler.length, nodes[n].start, newVal); 
                rxq.updateValue(euler, euler.length, nodes[n].end, newVal); 
                nodes[n].val = newVal; 
            }
            else {
                int u = s.nextInt(), v = s.nextInt(), a = fe.get(nodes[u].id), b = fe.get(nodes[v].id); 
                int lca = rmq.RMQ(eulerLca.length, Math.min(a, b), Math.max(a, b)); 
                int distU = rxq.RXQ(euler.length, 0, nodes[u].start), distV = rxq.RXQ(euler.length, 0, nodes[v].start);  
                pw.println(distU ^ distV ^ nodes[nto.get(lca)].val); 
            }
        s.close();
        pw.close(); 
    }
    static void dfs(Node n, boolean[] visited) {
        int ni = nto.size(); 
        nto.add(n.id); 
        fe.put(n.id, eulerLcaList.size()); 
        eulerLcaList.add(ni); 
        euler[n.start = pos ++] = n.val; 
        for (Node i : n.children) 
            if (!visited[i.id]) {
                visited[i.id] = true; 
                dfs(i, visited); 
                eulerLcaList.add(ni); 
            }
        euler[n.end = pos ++] = n.val; 
    }
    static class Node { 
        ArrayList<Node> children; 
        int val, id, start, end; 
        public Node(int val, int id) {
            this.val = val; 
            this.id = id; 
            children = new ArrayList<>(); 
        }
    }
    static int mid(int s, int e) {
    	return s + (e - s) / 2;
    }
    static class STM {
        int st[]; 
        int RMQUtil(int ss, int se, int qs, int qe, int index)  { 
            if (qs <= ss && qe >= se) 
                return st[index]; 
            if (se < qs || ss > qe) 
                return Integer.MAX_VALUE; 
            return Math.min(RMQUtil(ss, mid(ss,se), qs, qe, 2*index+1), RMQUtil(mid(ss, se)+1, se, qs, qe, 2*index+2)); 
        } 
        int RMQ(int n, int qs, int qe) { 
            return RMQUtil(0, n - 1, qs, qe, 0); 
        } 
        int constructSTUtil(int arr[], int ss, int se, int si) { 
            if (ss == se) { 
                st[si] = arr[ss]; 
                return arr[ss]; 
            } 
            st[si] = Math.min(constructSTUtil(arr, ss, mid(ss, se), si*2+1), constructSTUtil(arr, mid(ss, se)+1, se, si*2+2)); 
            return st[si]; 
        } 
        void constructST(int arr[], int n)  { 
            st = new int[2 * (int) Math.pow(2, (int) (Math.ceil(Math.log(n) / Math.log(2)))) - 1]; 
            constructSTUtil(arr, 0, n - 1, 0); 
        } 
    }
    static class STX {
        int st[]; 
        int RXQUtil(int ss, int se, int qs, int qe, int index)  { 
            if (qs <= ss && qe >= se) 
                return st[index]; 
            if (se < qs || ss > qe) 
                return 0; 
            return RXQUtil(ss, mid(ss, se), qs, qe, 2*index+1) ^ RXQUtil(mid(ss, se)+1, se, qs, qe, 2*index+2); 
        } 
        int RXQ(int n, int qs, int qe) { 
            return RXQUtil(0, n - 1, qs, qe, 0); 
        } 
        int constructSTUtil(int arr[], int ss, int se, int si) { 
            if (ss == se) { 
                st[si] = arr[ss]; 
                return arr[ss]; 
            } 
            st[si] = constructSTUtil(arr, ss, mid(ss, se), si*2+1) ^ constructSTUtil(arr, mid(ss, se)+1, se, si*2+2); 
            return st[si]; 
        } 
        void constructST(int arr[], int n)  { 
            st = new int[2 * (int) Math.pow(2, (int) (Math.ceil(Math.log(n) / Math.log(2)))) - 1]; 
            constructSTUtil(arr, 0, n - 1, 0); 
        } 
        void updateValueUtil(int ss, int se, int i, int prev_val, int new_val, int si) { 
            if (i < ss || i > se) 
                return; 
            st[si] = (st[si] ^ prev_val) ^ new_val; 
            if (se != ss) { 
                updateValueUtil(ss, mid(ss, se), i, prev_val, new_val, 2*si + 1); 
                updateValueUtil(mid(ss, se) + 1, se, i, prev_val, new_val, 2*si + 2); 
            }    
        }
        void updateValue(int arr[], int n, int i, int new_val) { 
            int temp = arr[i]; 
            arr[i] = new_val; 
            updateValueUtil(0, n-1, i, temp, new_val, 0); 
        } 
    }
}