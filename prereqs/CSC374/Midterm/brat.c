/*
 *  brat.c
 *  
 *
 *  Created by Raymond Elward on 10/12/10.
 *  Copyright 2010 DePaul University. All rights reserved.
 *
 */

/*
 *    brat.c
 */
#include <stdlib.h>
#include <stdio.h>
#include <signal.h>
#include <unistd.h>


int number;
int jumps = 0;


/* You may want to write a signal handler for SIGTERM */
void term_handler(int sig){
	
	printf("Brat %d going to bed.\n", number);
	exit(jumps);
}


int main (int argc, const char* argv[])
{
	
	if  (argc < 2)
    {
		fprintf(stderr,"Brat couldn't find it's number, quiting.\n");
		exit(-1);
    }
	
	number = atoi(argv[1]);
	srand(number+1);
	// You may want to tell this brat-process to listen for SIGTERM
	signal(SIGTERM, term_handler);
	/* Infinite loop to:
	 (1) increment jump,
	 (2) output brat number and number times jumped on bed,
	 (3) sleep() for (rand() % 4) + 1
	 */
	while (1) {
		jumps++;
		printf("Brat %d jumped on bed %d times\n", number, jumps);
		sleep( (rand() %4) + 1 );
	}
	
	return(EXIT_SUCCESS);
}