#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#include <unistd.h>


int main (int argc, char* argv[])
{
    int numSecs;
	
    if  ( (argc != 2)  ||  !isdigit(*argv[1]) )
    {
        fprintf(stderr,"Usage: delayedHello <numSecs>\n");
        return(EXIT_FAILURE);
    }
	
    sleep(atoi(argv[1]));
    printf("Hello!\n");
    return(EXIT_SUCCESS);
}
