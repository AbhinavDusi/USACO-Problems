#include <bits/stdc++.h>
using namespace std;
int N, M, T; 
class Graph {
    list< pair<int, int> > *adj; 
    public: 
    Graph() {
        adj = new list< pair<int, int> >[N + 1]; 
    }
    void add_edge(int u, int v, int w) {
        adj[u].push_back(make_pair(v, w)); 
        adj[v].push_back(make_pair(u, w)); 
    }
    long shortest_path(vector<int> num_cows) {
        set< pair<int, int> > setds; 
        vector<long> dist(N + 1, LONG_MAX), sums(N + 1); 
        vector<int> par(N + 1); 
        setds.insert(make_pair(0, 1)); 
        dist[1] = 0; 
        par[1] = 0; 
        while (!setds.empty()) {
            pair<int, int> tmp = *setds.begin(); 
            setds.erase(setds.begin()); 
            int u = tmp.second;
            list< pair<int, int> >:: iterator i; 
            for (i = adj[u].begin(); i != adj[u].end(); i ++) {
                int v = (*i).first, w = (*i).second; 
                if ((dist[u] + w == dist[v] && u < par[v]) || dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    par[v] = u; 
                    setds.insert(make_pair(dist[v], v)); 
                }
            }
        }
        long ret = 0; 
        for (int i = 1; i < N + 1; i ++) {
            int j = i; 
            while (j != 0) {
                sums[j] += max(num_cows[i] * (dist[j] - T), 0L);
                ret = max(ret, sums[j]); 
                j = par[j]; 
            } 
        }
        return ret; 
    }   
};
int main() {
    ifstream in("shortcut.in"); 
    ofstream out("shortcut.out"); 
    in >> N >> M >> T; 
    Graph g; 
    vector<int> num_cows(N + 1); 
    for (int i = 1; i < N + 1; i ++) 
        in >> num_cows[i]; 
    for (int i = 0; i < M; i ++) {
        int u, v, w; 
        in >> u >> v >> w; 
        g.add_edge(u, v, w); 
    }
    out << g.shortest_path(num_cows); 
}