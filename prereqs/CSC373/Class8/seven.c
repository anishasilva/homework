#include <stdio.h>
#include <stdlib.h>

#define D1 (3)
#define D2 (2)
#define D3 (3)
#define UB (100)

void dump_array(int a[][D2][D3]) {
	int i, j, k;
	for(i = 0; i < D1; i++)
	{
		for(j = 0; j < D2; j++)
		{
			for(k = 0; k < D3; k++)
			{
				printf("%i ", a[i][j][k]);
			}
			printf("\n");
		}
		printf("\n");
	}
	printf("\n");
}

int main() {
	srand(time(0));
	
	int d3[D1][D2][D3];
	int n = (D1*D2*D3*sizeof(int)) - sizeof(int);
	int* ptr = (int*) d3;
	printf("\n\n\nptr**%i**\n\n\n", (int) ptr);	
	int* foo = (int*) ((int) d3 + n);
	
	while ((int)foo >= (int)ptr) {printf("foo:%i \n\n", (int) foo); *foo-- = rand() % UB;}
	/*int x = 1;
	while (x <= (sizeof(d3) / sizeof(int))) {*ptr++ = rand() % UB;x++;}*/
	printf("\n\n\nd3**%i**\n\n\n", (int) d3);
	
	printf("\n\n\nptr after**%i**\n\n\n", (int) ptr);	
	printf("\n\n\nfoo**%i**\n\n\n", (int) foo);

	
	/*int n = (D1*D2*D3*sizeof(int)) - sizeof(int);
	printf("\n\n\n**%x**\n\n\n", (int) d3);
	
	printf("\n\n\n**%x**\n\n\n", (int) ptr);

	int* foo = ptr + sizeof(d3);
	
	printf("\n\n\n**%x**\n\n\n", (int) foo);

	while ( foo >= ptr) {
		*foo-- = rand() % UB;
	}*/
	
	printf("size of %s: %i\n", "sizeof(d3)", sizeof(d3));
	printf("size of %s: %i\n", "sizeof(d3[1])", sizeof(d3[1]));
	printf("size of %s: %i\n", "sizeof(d3[1][0])", sizeof(d3[1][0]));
	printf("size of %s: %i\n", "sizeof(d3[1][1][1])", sizeof(d3[1][1][1]));
	
	dump_array(d3);

	return 0;
}