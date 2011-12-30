/*
 * Compile with:
 *      gcc -o uselessClient uselessClient.c -lncurses
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include <sys/socket.h> // socket()
#include <netinet/in.h> // sockaddr_in, htons()
#include <netdb.h>      // gethostbyname()
#include <errno.h>

#include <ncurses.h>    // initscr(), clear(), refresh(), wmove(), waddch(),
                        // endwin()


const int PORT = 5000;

const char      STOP_CHAR   = (char)0x1B; // Escape char



int   connectToServer ()
{
  int                connectDs;
  struct sockaddr_in socketInfo;
  struct hostent*    hostPtr = gethostbyname("localhost.localdomain");

  if  (hostPtr == NULL)
  {
    perror("gethostbyname");
    return(-1);
  }

  if  ( (connectDs = socket(AF_INET,SOCK_STREAM,0)) == -1 )
  {
    perror("socket()");
    return(-1);
  }


  memset(&socketInfo,'\0',sizeof(socketInfo));
  socketInfo.sin_family      = AF_INET;
  socketInfo.sin_addr.s_addr = ((struct in_addr*)(hostPtr->h_addr))->s_addr;
  socketInfo.sin_port        = htons(PORT);

  if (connect(connectDs,(struct sockaddr*)&socketInfo,sizeof(socketInfo)) == -1)
  {
    perror("connect");
    return(-1);
  }

  return(connectDs);
}



void  startWindow()
{
  initscr();
  clear();
  refresh();
  scrollok(stdscr,TRUE);
  wmove(stdscr,1,1);
}


void  doClient (int connectDs)
{

  while  (true)
  {
    int  i = getch();
    char c = (char) i;

    if  (write(connectDs,&c,1) == -1)
    {
      perror("write");
      return;
    }

    if  (i == STOP_CHAR)
      break;

    if (read(connectDs,&c,1) == -1)
    {
      perror("write");
      return;
    }

    waddch(stdscr,c);
    waddch(stdscr,' ');
    refresh();
  }

}


void  stopWindow()
{
  endwin();
}



int main ()
{
  int connectDs;

  if  ( (connectDs = connectToServer()) == -1 )
    return(EXIT_FAILURE);

  startWindow();
  doClient(connectDs);
  stopWindow();

  close(connectDs);
  return(EXIT_SUCCESS);
}
