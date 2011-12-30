/**************************************************************************
***									***
***		optimizeAndInvestigate1.c				***
***									***
***	***	***	***	***	***	***	***	***	***
***									***
***	Version 1.0		Joseph Phillips		2010 Oct 12	***
***									***
**************************************************************************/

#include <stdlib.h>
#include <stdio.h>


/*
 *      Constants, global vars, and global function declarations:
 */

#define TEXT_LEN 10

extern int  limit;

int sum (int sumLimit);



/*
 *      Functions that do main work of program:
 */

/* PURPOSE:  To compute and return a silly, useless sum based upon global
 *      variable 'limit'.  Other than global var 'limit' no parameters.
 */
int superSum ()
{
	
	int i;
	int j;
	int i2;
	int j2;
	int total = 0;
	
	for  (i = 1;  i <= limit;  i = i << 1){
		i2 = i * i;
		for  (j = 1;  j <= i;  j++){
			j2 = j * j;
			total += sum(i2 - j2) + sum(i2 + j2) + j;
			
		}
	}
	
   /*
	*for  (i = 1;  i <= limit;  i = i * 2)
	*	for  (j = 1;  j <= i;  j++)
	*		total += sum(i*i-j*j) + sum(i*i+j*j) + j;
	*
	*/
	return(total);
}



/* PURPOSE:  To ask the user which number they want the superSum() of, and
 *      to compute and output the superSum() of that number.  No parameters.
 *      Returns 'EXIT_SUCCESS' to OS.
 */
int main ()
{
	char text[TEXT_LEN];
	
	do
    {
		printf("Please enter a positive integer less than or equal to 128: ");
		fgets(text,TEXT_LEN,stdin);
		limit = atoi(text);
    }
	while ( (limit < 1) || (limit > 128) );
	
	printf("Supersum(%d) == %d\n",limit,superSum(limit));
	return(EXIT_SUCCESS);
}