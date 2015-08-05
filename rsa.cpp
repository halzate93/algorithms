#include <iostream>
#include <string>
#include <stdlib.h>
#include <math.h>

using namespace std;

int primes [22]= {11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
int p_size = 22;

int inverse (int e, int m);
int* egcd (int a, int b);

int main (int argc, char* argv[]) { 
  if (argc < 4) {
    cout << "Please input p, q and m values." << endl;
    return -1;
  }

  int p = atoi(argv[1]);
  int q = atoi(argv[2]);
  int m = atoi(argv[3]);

  //cout << p << " " << q << " " << m << endl;

  int n = p * q;
  int t = n - (p + q - 1);
  //cout << n << " " << t << endl;

  int e = -1;
  for (int i=0; e == -1 && i < p_size; i++) {
    if (t % primes[i] != 0) e = primes[i];
  }
  if (e == -1) {
    cout << "Error finding coprime number for " << t << endl;
    return -2;
  }

  //int d = inverse(e, t);
  int c = (int)pow(m, e) % n;
  cout << "pub: n=" << n << ", e=" << e << endl;
  cout << "c(" << m << ") = " << c << endl;

  return 0; 
}

/*
int* egcd (int a, int b) {
  int x = 0, y = 1, u = 1, v = 0;
  while (a != 0) {
    int q = b/a, r = b%a;
    int m = x-u*q, n = y-v*q;
    b=a, a=r, x=u, y=v, u=m, v=n;
  }
  int gcd = b;
  return new int[3]{gcd, x, y};
}

int inverse (int e, int t) {
  int* res = egcd(e, t);
  if (res[0] != 1) return -1;
  else return res[1] % t;
}
*/
