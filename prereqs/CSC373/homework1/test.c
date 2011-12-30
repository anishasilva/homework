/*
 *  test.c
 *  
 *
 *  Created by Raymond Elward on 6/18/10.
 *  Copyright 2010 DePaul University. All rights reserved.
 *
 */

#include <stdio.h>


typedef unsigned char *byte_pointer;

void show_bytes(byte_pointer start, int len){
	int i;
	printf("0x");
	for (i = 0; i < len; i++)
		printf("%.2x", start[i]);
	printf("\n");
}


int main(){
	int val = 123456;
	byte_pointer valp = (byte_pointer) &val;
	printf("****************************************************************\n\n");
	show_bytes(valp, sizeof(val)); /* A. */
	printf("****************************************************************\n\n");
	show_bytes(valp, sizeof(valp)); /* B. */
	printf("****************************************************************\n\n");
	show_bytes(valp, 6); /* C. */
	printf("****************************************************************\n\n");
	return 0;
}
