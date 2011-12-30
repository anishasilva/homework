#include <stdlib.h>
#include <stdio.h>

void  reverse (const char* const cPtr)
{
    const char* run;
	
    //  Run to end of string:
	
    for  (run = cPtr;  *run != '\0';  run++);
	
	
    //  Come on back and print chars as go:
	
    for  (run--;  run >= cPtr;  run--)
		putchar(*run);
	
}



int main (int argc, char* argv[])
{
    if  (argc != 2)
    {
        fprintf(stderr,"Usage: reverse <string>\n");
        return(EXIT_FAILURE);
    }
	
    reverse(argv[1]);
    putchar('\n');
    return(EXIT_SUCCESS);
}