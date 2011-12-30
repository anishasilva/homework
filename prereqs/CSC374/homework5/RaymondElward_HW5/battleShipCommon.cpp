/***************************************************************************
 ****									****
 ****		battleShipCommon.cpp					****
 ****									****
 ****	    This file includes						****
 ****									****
 ****	****	****	****	****	****	****	****	****	****
 ****									****
 ****	Version 1.0		Joseph Phillips		2010 November 7	****
 ****									****
 ***************************************************************************/

#include	"headers.h"

#include	<sys/socket.h>		// For socket()
#include	<netdb.h>		// For gethostbyname()


/*  PURPOSE:  To be a space for the application to compose text to the user.
 */
char		cText[C_STRING_MAX];



/*  PURPOSE:  To be the "robust" version of recv() in the manner advocated by
 *      Bryant and O'Hallaron.  'fd' tells the file descriptor from which to
 *      read.  'usrbuf' tells the buffer into which to read.  'n' tells the
 *      length of 'usrbuf'.  'flags' tells the flags to send to 'recv()'.
 *      Returns number of bytes read or -1 on some type of error.
 */
ssize_t rio_recv(int fd, void* usrbuf, size_t n, int flags) 
{

  //  Copy and pace the book's rio_read() code here
  //  ONLY ONE CHANGE!
  //  (1) Use recv() instead of read().
  //      recv() wants four arguments instead of read()'s three.
  //      Make the fourth argument 'flags'
/*
*	int cnt;
*	while (rp->rio_cnt <= 0) {
*		rp->rio_cnt = recv(rp->rio_fd, rp->rio_buf, sizeof(rp->rio_buf), flags);
*		if (rp->rio_cnt < 0) {
*			if (errno != EINTR) {
*				return -1;
*			}
*			else if (rp->rio_cnt < 0)
*				return 0;
*			else {
*				rp->rio_bufptr = rp->tio_buf;
*			}
*		}
*	}
*	cnt = n;
*	if (rp->rio_dnt < n)
*		cnt = rp->rio_cnt;
*	memcpy(usrbuf, rp->rio_bufptr, cnt);
*	rp->rio_bufptr += cnt;
*	rp->rio_cnt -= cnt;
*	return cnt;
*/
	
	size_t nleft = n;
	ssize_t nread;
	char *bufp = (char*)usrbuf;
	
	while (nleft > 0) {
		if ( (nread = recv(fd, bufp, nleft, flags) ) < 0 ) {
			if (errno == EINTR)
				nread = 0;
			else 
				return -1;
		}
		else if (nread == 0) 
			break;
		nleft -= nread;
		bufp += nread;
	}
	return (n - nleft);
						   
	//return (0); // Replace this line with the return() statement of copied code
}



/*  PURPOSE:  To setup and return a socket to connect with a server on
 *	the machine named 'hostname' using port number 'portNumber'.
 *      Returns 'ERROR_DESCRIPTOR' on error.
 */
int	getSocketDescriptor (const char* hostname, int portNumber)
	throw (const char*)
{

  //  I.  Parameter validity check:


  //  II.  Attempt to get socket descriptor:

  //  YOUR CODE HERE TO:
  //  (1) Make a 'hostent*' var point to value returned by
  //     'gethostbyname(hostname)'.  If it returns 'NULL' then do:
  //	 perror("gethostbyname") and then return(ERROR_DESCRIPTOR).
	hostent* hostentVar = gethostbyname(hostname);
	if ( (hostentVar) == NULL){
		perror("gethostbyname");
		return(ERROR_DESCRIPTOR);
	}

  //  (2) Make a TCP/IP communicating socket and put the file descriptor in an
  //      integer.  If it returns -1 then do:
  //      perror("socket") and then return(ERROR_DESCRIPTOR).
	int connectDs;
	if ( (connectDs = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
		perror("socket");
		return(ERROR_DESCRIPTOR);
	}

  //  (3) Do the following code:
  sockaddr_in	socketInfo;
  
  memset(&socketInfo, 0, sizeof(socketInfo));
  socketInfo.sin_family	     = AF_INET;
  socketInfo.sin_addr.s_addr = ((in_addr *)(hostentVar->h_addr))->s_addr;
  socketInfo.sin_port	     = htons(portNumber);

  //  (4) Attempt to connect to the socket given by your socket file descriptor
  //      and 'socketInfo'.  If it returns -1 then do perror("connect") and
  //	  then return(ERROR_DESCRIPTOR)
	if (connect(connectDs, (sockaddr*)&socketInfo, sizeof(socketInfo)) == -1) {
		perror("connect");
		return(ERROR_DESCRIPTOR);
	}
	
	

  //  (5) Lastly return your socket file descriptor integer:


  //  III.  Return initialized socket descriptor:

  return(connectDs);    // Replace with your socket file descriptor int
}



/*  PURPOSE:  To get and return a row number.  Returns -1 for 'Q':
 */
int	getRow	(StatusDisplayWindow&	statusWindow)
{

  //  I.  Applicability validity check:


  //  II.  Get the row:

  int	row;

  noecho();

  do
  {
    row = toupper(getch());
  }
  while  (((row < 'A') || (row > 'J')) &&  (row != 'Q') );

  statusWindow.show((char)row);


  //  III.  Finished:

  if  (row == 'Q')
    return(-1);

  return(row - 'A');
}


/*  PURPOSE:  To get and return a column number.
 */
int	getCol	(StatusDisplayWindow&	statusWindow)
{

  //  I.  Applicability validity check:


  //  II.  Get the row:

  int	col;

  noecho();

  do
  {
    col = toupper(getch());
  }
  while  ( (col < '0') || (col > '9') );

  statusWindow.show((char)col);


  //  III.  Finished:

  return(col - '0');
}



/*  PURPOSE:  To hold the names of the ships.
 */
static const char*	shipNameArray[ORIGINAL_NUM_SHIPS]
			= {"aircraft carrier",
			   "battleship",
			   "cruiser",
			   "submarine",
			   "destroyer"
			  };


/*  PURPOSE:  To return a pointer to the name of the ship indexed by 'ship'.
 *	Throws exception if 'ship' is out-of-bounds.
 */
const char*	getShipName	(ShipType	ship)
      		throw(const char*)
{

  //  I.  Applicability validity check:

  if  ( (ship < 0)  ||  (ship >= ORIGINAL_NUM_SHIPS) )
  {
    snprintf(cText,C_STRING_MAX,"Ship index %d out-of-bounds in getShipName()",
    	     ship
	    );
    throw cText;
  }


  //  II.  Return name:

  return(shipNameArray[ship]);
}



/*  PURPOSE:  To hold the names of the ships.
 */
static int	shipLength[ORIGINAL_NUM_SHIPS]
			= {5,	// aircraft carrier
			   4,	// battleship
			   3,	// cruiser
			   3,	// submarine
			   2	// destroyer
			  };



/*  PURPOSE:  To return the length of the ship indexed by 'ship'.
 *	Throws exception if 'ship' is out-of-bounds.
 */
int	getShipLength	(ShipType	ship)	throw(const char*)
{

  //  I.  Applicability validity check:

  if  ( (ship < 0)  ||  (ship >= ORIGINAL_NUM_SHIPS) )
  {
    snprintf(cText,C_STRING_MAX,
	     "Ship index %d out-of-bounds in getShipLength()",
    	     ship
	    );
    throw cText;
  }


  //  II.  Return name:

  return(shipLength[ship]);
}
