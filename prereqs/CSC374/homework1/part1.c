#include <stdlib.h>
#include <stdio.h>


/* Declarations go here: */

extern char* charArray1;
extern char* charArray2;
char* initializeArray (char* array);
/* This function is given a C array (that is, a pointer to the 1st
 element in an array)
 
 It sets:
 array[0] = ' ' + 0,
 array[1] = ' ' + 1,
 array[2] = ' ' + 2,
 . . .
 array[SIZE-1] = ' ' + (SIZE-1)
 etc. where ' ' is the space char.   It then returns the array.
 */


/* Defined global variables go here: */

int SIZE = 'z' - ' ' + 1;



int main ()
{
	int i;
	
	charArray1 = initializeArray((char*)calloc(SIZE+1,sizeof(char)));
	charArray2 = (char*)calloc(SIZE+1,sizeof(char));
	
	
	for  (i = 0;  i < SIZE;  i++)
		charArray2[SIZE-1-i] = charArray1[i];
	
	charArray2[SIZE] = '\0';
	printf("The ASCII table in reverse is:\n%s\n",charArray2);
	free(charArray2);
	free(charArray1);
	return(0);
}