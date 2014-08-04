#include <iostream>
#include <vector>
using namespace std;
 
vector<unsigned int> sievePrimes(unsigned int limit) {
    vector<unsigned int> primes;
    if (limit > 1) { 
        primes.push_back(2);
        vector<bool> sieve((limit + 1) / 2, false);
        for (unsigned long long i = 1, n = 3; i < sieve.size(); ++i, n += 2) {
            if (!sieve[i]) {
                primes.push_back(n);
                for (unsigned long long j = n * n / 2; j < sieve.size(); j += n) {
                    sieve[j] = true;
                }
            }
        }
    }
    return primes;
}

int main() {
    vector<unsigned int> p = sievePrimes(16000000);
    cout << p.size() << endl;
    cout << p[1000000-1] << endl;
}