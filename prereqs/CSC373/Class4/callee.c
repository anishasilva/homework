/* callee.c */
#include <stdio.h>

int callee(int n1, int n2) {
	int temp = n1 * n2;
	return temp - (n1 + n2);
}

void caller() {
	int a = 4;
	int b = 8;
	int c = callee(a, b);
}