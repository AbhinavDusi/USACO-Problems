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
    ifstream in("spainting.in"); 
    ofstream out("spainting.out"); 
    in >> N >> M >> K; 
    long dp[N + 1]; 
    long sum = 0; 
    for (int i = 1; i < K; i ++) {
    	dp[i] = power(M, i); 
    	sum = (sum + dp[i] + MOD) % MOD; 
    }
    for (int i = K; i < N + 1; i ++) {
    	dp[i] = 0; 
        dp[i] = (dp[i] + (M - 1) * sum + MOD) % MOD;
        sum = (sum - dp[i - K + 1] + dp[i] + MOD) % MOD;
    }
    out << (power(M, N) - dp[N] + MOD) % MOD  << endl; 
}