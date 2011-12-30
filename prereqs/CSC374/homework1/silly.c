/*
 *  silly.c
 *  
 *
 *  Created by Raymond Elward on 9/21/10.
 *  Copyright 2010 DePaul University. All rights reserved.
 *
 */
/*#include <stdlib.h>
#include <stdio.h>
*/
int fncCall(int input){
	return input;
} 

int* sillyCompute (int* dest, int* source, int n)
{
    int i, j;
	
    for  (i = n; i > 0; i = i / 2)
    {
		
        for  (j = 0;  j < n;  j++)
            dest[ i*n + j ] = source[n*n - j/(i*2) - i] + fncCall(i);
		
    }
	
}

/*
void sillyCompute (int* dest, int* source, int n)
{
    int i,j;
	int n2 = n*n;
    for  (i = n;  i > 0;  i = i >> 1)
    {
		int in = i*n;
		int fnc = fncCall(i);
		int* ptrDest = dest;
		int* ptrSource = source;
		int n2minusi = n2 - i;
        for  (j = 0;  j < n;  j++){
			ptrSource = n2minusi - j/(i<<1);
			ptrDest = in + j;
            *ptrDest = *ptrSource + fnc;
		}
		
    }
	
}
*/
int main() {
}

/*
int main() {
	int* dest = (int*)calloc(11, sizeof(int));
	int* source = (int*)calloc(11, sizeof(int));
	int i = 0;
	for (i = 0; i < 10; i++){
		dest[i] = i;
		source[i] = i + 20;
	}
	int* returned = sillyCompute(dest, source, 10);
	
	for (i = 0; i < 10; i++){
		printf("%d\n", returned[i]);
	}
	free(dest);
	free(source);
}*/