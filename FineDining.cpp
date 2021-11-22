#include <bits/stdc++.h>
using namespace std;
int N, M, K; 
class Graph {
    list< pair<int, int> > *adj; 
    public: 
    Graph() {
        adj = new list< pair<int, int> >[N + 2]; 
    }
    void add_edge(int u, int v, int w) {
        adj[u].push_back(make_pair(v, w)); 
    }
    vector<int> shortest_path(int src) {
        set< pair<int, int> > setds; 
        vector<int> dist(N + 2, INT_MAX); 
        setds.insert(make_pair(0, src)); 
        dist[src] = 0; 
        while (!setds.empty()) {
            pair<int, int> tmp = *setds.begin(); 
            setds.erase(setds.begin()); 
            int u = tmp.second;
            list< pair<int, int> >:: iterator i; 
            for (i = adj[u].begin(); i != adj[u].end(); i ++) {
                int v = (*i).first, w = (*i).second; 
                if (dist[u] + w <= dist[v]) {
                    dist[v] = dist[u] + w;
                    setds.insert(make_pair(dist[v], v)); 
                }
            }
        }
        return dist; 
    }   
};
int main() {
    ifstream in("dining.in"); 
    ofstream out("dining.out"); 
    in >> N >> M >> K; 
    Graph g; 
    for (int i = 0; i < M; i ++) {
        int a, b, t;
        in >> a >> b >> t; 
        g.add_edge(a, b, t); 
        g.add_edge(b, a, t); 
    }
    vector<int> dist = g.shortest_path(N); 
    for (int i = 0; i < K; i ++) {
        int a, y; 
        in >> a >> y; 
        g.add_edge(N + 1, a, dist[a] - y); 
    }
    vector<int> changed_dist = g.shortest_path(N + 1); 
    for (int i = 1; i < N; i ++) 
        out << (changed_dist[i] <= dist[i]) << endl; 
}