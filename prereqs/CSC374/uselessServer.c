/*
 * Compile with:
 *      gcc -o uselessServer uselessServer.c -lncurses
 */


#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include <sys/socket.h> // socket(), bind()
#include <netinet/in.h> // sockaddr_in, htons()
#include <errno.h>

#include <ncurses.h>    // initscr(), clear(), refresh(), wmove(), waddstr(),
                        // endwin()


const int       NUM_CLIENTS = 2;

const int       LINE_LENGTH = 80;

const int       PORT_NUMBER = 5000;

const char      STOP_CHAR   = (char)0x1B; // Stop client-handling thread when
                                          // receive this char (the Escape char)

int             numCharsProcessed = 0;    // Keeps track to of number of chars
                                          // processed so far


/*  PURPOSE:  To ask for a server socket file descriptor for reliable
 *      connections, bind the server socket to port 'PORT_NUMBER', and tell the
 *      OS that this thread is ready to listen to the port.  Returns either the
 *      socket file descriptor on success or -1 on error.
 */
int startServer ()
{
  struct sockaddr_in socketInfo;
  int                status;
  int                socketDs = socket(AF_INET, SOCK_STREAM, 0);

  if  (socketDs == -1)
  {
    perror("socket()");
    return(-1);
  }

  memset((void*)&socketInfo,'\0',sizeof(socketInfo));
  socketInfo.sin_addr.s_addr = INADDR_ANY;
  socketInfo.sin_family      = AF_INET;
  socketInfo.sin_port        = htons(PORT_NUMBER);

  status = bind(socketDs,(struct sockaddr*)&socketInfo,sizeof(socketInfo));

  if  (status == -1)
  {
    perror("socket()");
    return(-1);
  }

  if  (listen(socketDs,NUM_CLIENTS) == -1)
  {
    perror("socket()");
    return(-1);
  }

  return(socketDs);
}



/*  PURPOSE:  To increment 'numCharsProcessed', move the cursor to row = 10,
 *      column = 20, build a descriptive string, write that string, and update
 *      the screen.  All of this is protected by 'mutexLock'.  No parameters.
 *      No return value.
 */
void  oneMoreChar ()
{
  char line[LINE_LENGTH];

  numCharsProcessed++;
  wmove(stdscr,10,20);
  snprintf(line,LINE_LENGTH,"Useless server on port %d, %d chars processed.",
	   PORT_NUMBER,numCharsProcessed
	  );
  waddstr(stdscr,line);
  refresh();
}



/*  PURPOSE:  To repeatedly read chars from the client from file descriptor
 *      'connectDs', add a random number in the range [-2, -1, 0, +1, +2] to
 *      them, write that added char back to 'connectDs', and to call
 *      'oneMoreChar()'.  This loop is continued until STOP_CHAR is received.
 *      When it is, the loop is ended and 'connectDs' is closed.  Returns NULL.
 */
void* doServer (int connectDs)
{

  while (true)
  {
    char c;

    if  ( read(connectDs,&c,1) == -1 )
    {
      perror("read");
      break;
    }

    if  (c == STOP_CHAR)
      break;

    c += ((rand() % 5) - 2);

    if  ( write(connectDs,&c,1) == -1 )
    {
      perror("write");
      break;
    }

    oneMoreChar();
  }

  close(connectDs);
  return(NULL);
}


/*  PURPOSE:  To close 'socketDs'.  No return value.
 */
void stopServer (int socketDs)
{
  close(socketDs);
}



/*  PURPOSE:  To start the ncurses windowing package, clear the screen
 *      move the cursor to row 10, column 20, build some text that
 *      tells the port and how many chars have been processed so far,
 *      write that text, and update the screen.  No parameters.  No
 *      return value.
 */
void startWindow ()
{
  char line[LINE_LENGTH];

  initscr();
  clear();
  wmove(stdscr,10,20);

  snprintf(line,LINE_LENGTH,"Useless server on port %d, %d chars processed.",
	   PORT_NUMBER,numCharsProcessed
	  );
  waddstr(stdscr,line);
  refresh();
}



/*  PURPOSE:  To end the ncurses windowing package.  No parameters.  No
 *      return value.
 */
void stopWindow ()
{
  wmove(stdscr,12,0);
  endwin();
}



/*  PURPOSE:  To secure port 'PORT_NUMBER' to listen to clients, to make
 *      'NUM_CLIENTS' POSIX threads to handle that number of clients, to
 *      display the number of chars processed by either thread as they are
 *      processed, and to close windowing, mutex locking and the socket when
 *      finished.  Returns EXIT_SUCCESS on succes or EXIT_FAILURE otherwise.
 */
int main ()
{
  int       socketDs;
  int       clientIn;
  int       clientIn1;
  pthread_t clientThread[NUM_CLIENTS];

  if  ( (socketDs = startServer()) == -1 )
  {
    fprintf(stderr,"Error connecting to socket.\n");
    return(EXIT_FAILURE);
  }

  startWindow();

    int connectDs = accept(socketDs,NULL,NULL);
    doServer(connectDs);


  stopWindow();
  stopServer(socketDs);
  return(EXIT_SUCCESS);
}
