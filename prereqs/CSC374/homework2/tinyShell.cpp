/*
 *  Compile with:
 *  linux> g++ tinyShell.cpp -o tinyShell
 *
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>
#include <sys/wait.h>
#include <ctype.h>
#include <setjmp.h>


const int	MAX_STRING	= 256;

const char	BACKGROUND_CHAR	= '&';

#define		ENDING_TEXT	"end"



/*  PURPOSE:  To get a line of text and place it in TEXT (without the \n char
 *	that fgets() reads.)  No return value.
 */
void	getText	(char* text)	throw(const char*)

{
    char*	nl_ptr;
	
    if  (text == NULL)
		throw "NULL ptr to getText()";
	
    if  (fgets(text,MAX_STRING,stdin) == NULL)
		throw "Input error (EOF?)";
	
    nl_ptr = strchr(text,'\n');
	
    if  (nl_ptr != NULL)
		*nl_ptr = '\0';
	
}



/*  PURPOSE:  To return "true" if the string FIRST is equivalent to string
 *	SECOND, or "false" otherwise.
 */
bool	equals	(const char* first, const char* second)	throw(const char*)

{
	
    if  ( (first == NULL)  ||  (second == NULL) )
		throw "NULL ptr to equals";
	
    return( strcmp(first,second) == 0 );
}



/*  PURPOSE: To see if file referenced by PATH_NAME_PTR exists.  Returns TRUE
 *	if it does or FALSE if not.
 */
bool	doesFileExist	(const char* path_name_ptr)	throw(const char*)

{
    static char	error[MAX_STRING];
	
	
    //  I.  Parameter validity check:
	
    if  (path_name_ptr == NULL)
		throw "NULL ptr to doesFileExist()";
	
	
    //	II.  Try to open file for reading & see if successful:
	
    int fd = open(path_name_ptr,O_RDONLY,0);
	
	
    //	II.A.  Some sort of error in attempting to open file occured.  Make
    //		sure it is because the file wasn't found:
	
    if  (fd == -1)
	{
		
		//  II.A.1.  Trap unexpected errors, assume they indicate a file that
		//	     effectively does not exist:
		
		if  (errno != ENOENT)
	    {
			sprintf(error,"Error trying to open %s to see if exists",
					path_name_ptr);
			throw error;
	    }
		
		
		//  II.A.2.  The error occured because the file does not exist:
		
		return(false);
	}
	
	
    //	III.  The file was successfully opened therefore it does exist.
    //		Close it & report that.
	
    if  (close(fd) != 0)
	{
		sprintf(error,"Error trying to open %s to see if exists",path_name_ptr);
		throw error;
	}
	
    return( true );
}



/*  PURPOSE:  To parse the command line input pointed to by 'inPtr' into
 *      the command portion (to be placed in the space pointed to by 'firstPtr')
 *      the argument portion (to be placed in the space pointed to by 'restPtr')
 *      and the boolean 'shouldDoInBackground' which is set to 'true' if the
 *      last character is 'BACKGROUND_CHAR', or is 'false' otherwise.
 */
void	parseString	(char* inPtr,
					 char* firstPtr, char* restPtr,
					 bool& shouldDoInBackground
					 )
{
    int indexIn;
    int indexRest;
    int length;
	
    shouldDoInBackground = false;
	
    if  ( (inPtr == NULL) || (firstPtr == NULL) || (restPtr == NULL) )
		throw "NULL ptr to parseString";
	
    length = strlen(inPtr);
	
    if  ( (length > 0)  &&  (inPtr[length-1] == BACKGROUND_CHAR) )
    {
        shouldDoInBackground = true;
        inPtr[length-1] = '\0';
    }
	
	
    for  (indexIn = 0;
          inPtr[indexIn] != '\0' && !isspace(inPtr[indexIn]);
          indexIn++
		  )    
        firstPtr[indexIn] = inPtr[indexIn];
	
    firstPtr[indexIn] = '\0';
    restPtr[0] = '\0';
	
    for  ( ;  isspace(inPtr[indexIn]);  indexIn++)
		if  (inPtr[indexIn] == '\0')
			return;
	
    for  (indexRest = 0;  inPtr[indexIn] != '\0';  indexIn++, indexRest++)
        restPtr[indexRest] = inPtr[indexIn];
	
    restPtr[indexRest] = '\0';
}






/*  PURPOSE:  To do the work of the shell.  No parameters.  No return value.
 */
void	doShell () throw(const char*)

{

	

	
    //	I.  Parameter validity check:
	
	
    //	II.  Fetch and execute user commands:


    while (1)
	{
		//  YOUR LOOP CODE HERE!

		char inputArray[MAX_STRING];
		char commandArray[MAX_STRING];
		char paramArray[MAX_STRING];
		bool background;
		
		//4.a
		printf( "Please enter expression file or \"end\" to end: " );
		//4.b
		getText( inputArray );
		//4.c.1
		if (equals( inputArray, "" ) ){
			//say come again, goto beginning of the loop.
			printf("Come again?\n");
			continue;
		}
		//4.c.2
		else if (equals(inputArray, ENDING_TEXT)){
			//end loop.
			break;
		}
		//4.c.3
		parseString(inputArray, commandArray, paramArray, background);
		//4.c.4
		if (!doesFileExist(commandArray)){
			printf( "I can't find %s\n", commandArray );
			continue;
			//goto beginning of loop
		}
		//4.d.2
		int child_status;
		int forkNum = fork();
		//printf("\nforkNum = %d\n", forkNum);
		
		if (forkNum == 0){
			char  cmdLine[MAX_STRING];
			
			snprintf( cmdLine, MAX_STRING, "./%s",commandArray );
			//HANDLES THE INFAMOUS CASE OF NO ARGUMENTS (BRAGGING RIGHTS + 2 EXTRA CREDIT POINTS?)
			if (equals("", paramArray)) {
				execl( cmdLine, commandArray, NULL, NULL);
				printf("execl failure\n");
				exit(EXIT_FAILURE);
			}
			execl( cmdLine, commandArray, paramArray, NULL );
			printf( "execl failure\n" );
			exit( EXIT_FAILURE );
		}
		//4.d.1
		else if (forkNum < 0) {
			printf("fork() freak-out!");
			exit( EXIT_FAILURE );
		}
		//4.d.3
		else if (forkNum > 0 && background == false) {
			pid_t pid = wait(&child_status);
			if ( !WIFEXITED( child_status ) )
				printf( "Child %d crashed with status %d\n", child_status, WEXITSTATUS( child_status ) );
		}
		
				 
		
	}
	
	
    //	III.  Finished:
	
}


/* WRITE YOUR Ctrl-C FUNCTION HERE */
//5
void int_handler(int sig){
	printf("\nPlease type \"end\" to end: ");
	fflush(stdout);
}


/* WRITE YOUR CHILD-CATCHING FUNCTION HERE */
//6
void chld_handler(int sig){
	int child_status;
	pid_t pid;
	pid = wait(&child_status);
	printf("Child %d ended normally\n", pid);
	
}




/*  PURPOSE:  To control the running of the shell.  No parameters.  Returns 0
 *	to OS.
 */
int  main ()

{
	
    //	I.  Parameter validity check:
	
	
    //	II.  Do shell:
	
	
    //  INSTALL YOUR 'SIGINT' Ctrl-C FUNCTION HERE
	//5:
	signal(SIGINT, int_handler);
    //  INSTALL YOUR 'SIGCHLD CHILD-CATCHING FUNCTION HERE
	//6:
	signal(SIGCHLD, chld_handler);
	
    try	{
		doShell();
	}
    catch  (const char* ptr)
	{
		puts(ptr);
	}
	
	
    //	III.  Finished:
	
    return(0);
}