#include <stdio.h>
#include <math.h>

void show_bytes(char* msg, unsigned char* ptr, int how_many) {
	printf("%s:\n", msg);
	int i;
	for (i = 0; i < how_many; i++) printf(" %.2x", ptr[i]);
	printf("\n");
}


int main() {
	double d1 = 1.0 / 10.0;
	printf("d1 == %f\n", d1);

	double d2 = pow(2.0, -20) * d1;
	printf("d2 == %f\n", d2);
	
	double d3 = 9.54 * pow(10.0, -8.0);
	printf("d3 == %f\n", d3);
	printf("\n");

	if (d1 == d2) printf("d1 == d2\n");
	else printf("d1 != d2\n");

	if (d1 == d3) printf("d1 == d3\n");
	else printf("d1 != d3\n");
	
	if (d2 == d3) printf("d2 == d3\n");
	else printf("d2 != d3\n");
	printf("\n\n");
	
	show_bytes("d1", (unsigned char*) &d1, sizeof(d1));
	show_bytes("d2", (unsigned char*) &d2, sizeof(d2));
	show_bytes("d3", (unsigned char*) &d3, sizeof(d3));


	return 0;
}