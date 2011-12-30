/*
 *  mama.c
 *  
 *
 *  Created by Raymond Elward on 10/12/10.
 *  Copyright 2010 DePaul University. All rights reserved.
 *
 */

/*
 *    mama.c
 */


#include <stdlib.h>
#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>


#define NUM_BRATS (4)
#define TEXT_LEN  (10)
/* You may want to write a signal handler for SIGCHLD */
void chld_handler(int sig) {
	int child_status;
	pid_t pid;
	while ( (pid = wait(&child_status) ) > 0 )
		printf("Child %d ended after jumping on the bed %d times.\n", pid, WEXITSTATUS(child_status));
}

int main ()
{
	int index;
	int pid[NUM_BRATS];
	char text[TEXT_LEN];
	
	// You may want to listen to SIGCHLD
	//old placement of child listener.
	//signal(SIGCHLD, chld_handler);
	
	for  (index = 0;  index < NUM_BRATS;  index++)
    {
		pid[index] = fork();
		/*
		 (1) Try to make a baby and put its process id in pid[index]
		 (2) If no baby was made then do:
		 
		 fprintf(stderr,"fork() error--someone's making too many children!\n");
		 exit(-1);
		 (3) If this process is the baby then does:
		 snprintf(text,TEXT_LEN,"%d",index);
		 execl("./brat","brat",text,NULL);
		 fprintf(stderr,"execl() error--couldn't find brat!\n");
		 exit(-1);
		 */
		
		if (pid[index] == 0) {
			snprintf(text, TEXT_LEN, "%d", index);
			execl("./brat", "brat", text, NULL);
			fprintf(stderr, "excel() error--couldn't find brat!\n");
			exit(-1);
		}
		if (pid[index] < 0) {
			fprintf(stderr,"fork() error--someone's making too many children!\n");
			exit(-1);
		}
    }
	
	printf("Press enter to tell the brats \"Stop jumping on the bed!\"\n");
	fgets(text,TEXT_LEN,stdin);
	printf("Mama has had enough and shouts \"GO TO BED!\"\n\n");
	
	//  Send SIGTERM to all brats
	for (index = 0; index < NUM_BRATS; index++) {
		kill(pid[index], SIGTERM);
	}


	//child signal moved down here to make sure all the children get caught.
	signal(SIGCHLD, chld_handler);
	
	sleep(2);  // Makes mama wait for brats to finish
	printf("Mama's finished!\n");
	return(EXIT_SUCCESS);
}