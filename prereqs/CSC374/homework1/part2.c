/*
 *  part2.c
 *  
 *
 *  Created by Raymond Elward on 9/21/10.
 *  Copyright 2010 DePaul University. All rights reserved.
 *
 */


char* charArray1;
char* charArray2;

extern int SIZE;

char* initializeArray(char* array){
	int i;
	char* ptr = array;
	for (i = 0; i < SIZE; i++)
		*ptr++ =  ' ' + i;
	return array;
}