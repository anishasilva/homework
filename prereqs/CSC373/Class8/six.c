#include <stdio.h>
#include <stdlib.h>


void f1(int** arg, int n1, int n2) {
	int i;
	for (i=0; i < n1; i++) {
		int j;
		for (j = 0; j < n2; j++) {
			*(*(arg + i) + j) = rand() % 100;
		}
	}
}

int** f2(int** arg, int n) {
	int** t = malloc(n * sizeof(int*));
	int i, k = 0;
	for(i = n - 1; i >= 0; i--) {
		t[k++] = arg[i];
	}
	return t;
}

void dump(int** array, int rows, int cols) {
	int i, j;
	for (i = 0; i < rows; i++) {
		printf("\n");
		int j;
		for (j = 0; j < cols; j++)
			printf("%i ", array[i][j]);
	}
	printf("\n");
}

int main() {
	int n = 0xffffffff;
	
	do {
		printf("Enter a positive integer: ");
		scanf("%i", &n);
	}while (n < 0);

	int** q = malloc(n*sizeof(int*));

	int i;
	for (i = 0; i < n; i ++) {
		*(q + i) = malloc(n * sizeof(int));
	}
	f1(q, n, n);

	int** r = f2(q, n);
	
	dump(q, n,n);
	printf("\n");
	dump(r, n,n);

	for (i = 0; i < n; i++)
		free(*(q+i));
	free(q);
	free(r);

	return 0;
}