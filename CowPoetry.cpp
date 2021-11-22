#include <bits/stdc++.h>
#define MOD 1000000007
using namespace std;
int N, M, K; 
long power(long base, long exp) {
    long result = 1;
    for (;;) {
        if (exp & 1)
            result = ((result % MOD) * (base % MOD)) % MOD;
        exp >>= 1;
        if (!exp)
            break;
        base = ((base % MOD) * (base % MOD)) % MOD;
    }
    return result % MOD;
}
int main() {
    ifstream in("poetry.in"); 
    ofstream out("poetry.out"); 
    in >> N >> M >> K; 
    vector< pair<int, int> > words(N); 
    unordered_map<char, int> char_map; 
    for (int i = 0; i < N; i ++) {
    	int s, r;
    	in >> s >> r; 
    	words[i] = make_pair(s, r);
    }
    for (int i = 0; i < M; i ++) {
    	char c;
    	in >> c; 
    	if (char_map.find(c) != char_map.end()) 
    		char_map.find(c) -> second += 1; 
    	else 
    		char_map.insert(make_pair(c, 1));
    }
    long comb_dp[K + 1], rhyme_dp[N + 1]; 
    fill_n(comb_dp, K + 1, 0);
    fill_n(rhyme_dp, N + 1, 0); 
    comb_dp[0] = 1; 
    for (int i = 0; i <= K; i ++) 
    	for (int j = 0; j < N; j ++) 
    		if (words[j].first + i <= K) {
    			if (words[j].first + i == K)
    				rhyme_dp[words[j].second] = ((comb_dp[i] % MOD) + (rhyme_dp[words[j].second] % MOD)) % MOD;
    			comb_dp[words[j].first + i] = ((comb_dp[i] % MOD) + (comb_dp[words[j].first + i] % MOD)) % MOD; 
    		}
    long combinations = 1; 
    unordered_map<char, int>::iterator itr; 
    for (itr = char_map.begin(); itr != char_map.end(); itr ++) {
    	long sum = 0; 
    	for (int i = 0; i < N + 1; i ++) 
    		if (rhyme_dp[i] != 0) 
    			sum = ((power(rhyme_dp[i], itr -> second) % MOD) + (sum % MOD)) % MOD; 
    	combinations = ((sum % MOD) * (combinations % MOD)) % MOD; 
    }
    out << (combinations % MOD); 
}