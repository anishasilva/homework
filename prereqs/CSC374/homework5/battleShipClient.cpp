/***************************************************************************
 ****									****
 ****		battleShipClient.cpp					****
 ****									****
 ****	    This file includes						****
 ****									****
 ****	****	****	****	****	****	****	****	****	****
 ****									****
 ****	Version 1.0		Joseph Phillips		2010 November 7	****
 ****									****
 ***************************************************************************/

/*
 * Compile and link with:
 *  g++ -g -c battleShipClient.cpp
 *  g++ -g -c battleShipCommon.cpp
 *  g++ -o battleShipClient battleShipClient.o battleShipCommon.o -lncurses
 */


#include	"headers.h"
#include	<unistd.h>	// For sleep()


/*  PURPOSE:  To obtain the hostname and port of the server from the command
 *  	line arguments 'argc' and 'argv', and from whatever else the user
 *	enters.  Set 'hostName' to the host name and 'portNumber' to the port
 *	number.  No return value.
 */
int	obtainHostnameAndPort	(int		argc,
				 const char*	argv[],
                                 const char*&   hostName,
                                 int&           portNumber
				)
{

  //  I.  Applicability validity check:


  //  II.  

  char*       newLinePtr;
  static char hostNameSpace[C_STRING_MAX];

  portNumber    = (argc > 1) ? atoi(argv[1]) : INITIAL_PORT;
  hostName      = (argc > 2) ? argv[2] : INITIAL_HOST;

  if  (argc <= 2)
  {
    printf("Hostname [%s]? ",hostName);
    fgets(hostNameSpace,C_STRING_MAX,stdin);
    newLinePtr = strchr(hostNameSpace,'\n');

    if  (newLinePtr != NULL)
      *newLinePtr = '\0';

    if  (hostNameSpace[0] != '\0')
      hostName = hostNameSpace;

  }

  if  (argc <= 1)
  {
    char        portSpace[C_STRING_MAX];

    printf("Port [%d]? ",portNumber);
    fgets(portSpace,C_STRING_MAX,stdin);
    newLinePtr = strchr(portSpace,'\n');

    if  (newLinePtr != NULL)
      *newLinePtr = '\0';

    if  ( isdigit(portSpace[0]) )
      portNumber = atoi(portSpace);

  }


  //  III.  Finished:

}



/*  PURPOSE:  To initialize the whole screen.  Set 'statusWindowPtr' to a
 *	window to which the status of the game is displayed to the user.  No
 *	return value.
 */
void	initializeScreen	(StatusDisplayWindow*& statusWindowPtr)
{

  //  I.  Applicability validity check:


  //  II.  Initialize the screen:

  int	row;
  int	col;

  //  YOUR CODE HERE TO
  //  (1) Start ncurses
  //  (2) Clear the screen
	initscr();
	clear();
  statusWindowPtr = new StatusDisplayWindow();


  for  (row = 0;  row <= 9;  row++)
  {
    //  YOUR CODE HERE TO:
    //  Print 'A' thru 'J' at row 'row*ROW_LEN+2' and columns 'LEFT_BORDER'
    //  'MIDDLE_BORDER' and 'RIGHT_BORDER' of the *whole screen*.
	  char display = 'A' + row;
	  move(row*ROW_LEN+2, LEFT_BORDER);
	  addch(display);
	  move(row*ROW_LEN+2, MIDDLE_BORDER);
	  addch(display);
	  move(row*ROW_LEN+2, RIGHT_BORDER);
	  addch(display);
  }

  for  (col = 0;  col <= 9;  col++)
  {
    //  YOUR CODE HERE TO:
    //  Print '0' thru '9' at rows 'TOP_BORDER' and 'BOTTOM_BORDER'; and
    //  columns 'col * COL_LEN + 4' of the *whole screen*.
	  move(TOP_BORDER, col * COL_LEN + 4);
	  addch(48 + col);
	  move(BOTTOM_BORDER, col * COL_LEN + 4);
	  addch(48 + col);
  }

  for  (col = 0;  col <= 9;  col++)
  {
    //  YOUR CODE HERE TO:
    //  Print '0' thru '9' at rows 'TOP_BORDER' and 'BOTTOM_BORDER'; and
    //  columns '3 + NUM_COLS*COL_LEN + col*COL_LEN + 3' of the *whole screen*.
	  move(TOP_BORDER, 3 + NUM_COLS * COL_LEN + col * COL_LEN + 3);
	  addch(48 + col);
	  move(BOTTOM_BORDER, 3 + NUM_COLS * COL_LEN + col * COL_LEN + 3);
	  addch(48 + col);
  }

  //  YOUR CODE HERE TO MAKE YOUR CHANGES VISIBLE!
	refresh();



  //  III.  Finished:

}



/*  PURPOSE:  To shutdown the whole graphics environment.  Delete()s
 *	'statusWindowPtr'.  No return value.
 */
void	shutDownScreen	(StatusDisplayWindow* statusWindowPtr)
{

  //  I.  Applicability validity check:


  //  II.  Shut the screen down:

  safeDelete(statusWindowPtr);
  //  YOUR CODE HERE TO stop ncurses
	endwin();


  //  III.  Finished:

}



/*  PURPOSE:  To receive and return a command from socket 'serverSocketFd'.
 *	Sets 'position' to any position associated with the command (or sets
 *	it to -1 otherwise), and sets 'ship' to the index of any ship
 *	associated with the command (or sets it to -1 otherwise).
 */
Command	getCommand	(int		serverSocketFd,
			 int&		row,
			 int&		col,
			 ShipType&	ship
			)
{

  //  I.  Applicability validity check:


  //  II.  Get command:

  Command	command;
  int		numBytes;
  int		buffer[BUFFER_LEN];

  //  YOUR CODE HERE TO:
  //  (1) rio_recv() from 'serverSocketFd' into 'buffer' up to
  //       'BUFFER_BYTE_SIZE' chars.  Be sure *not* to wait for the chars
  //	   if they are not there.
	numBytes = rio_recv(serverSocketFd, buffer, BUFFER_BYTE_SIZE, MSG_DONTWAIT);
  //  (2) Be sure you convert any integers your read from network to host
  //  	  endianness
	int i;
	for (i = 0; i < BUFFER_LEN; i++){
		buffer[i] = (int)ntohl(buffer[i]);
	}
  //  (3) If you only read 'COMMAND_LEN' bytes then:
  //      (a) set 'row' and 'col' to -1
  //      (b) set 'ship' to NO_SHIP
  //	  (c) return the (converted) value in 'buffer[0]':
	if (numBytes == COMMAND_LEN){
		row = -1;
		col = -1;
		ship = NO_SHIP;
		return (buffer[0]);
	}
  //  (4) Otherwise, set 'row' and 'col' to the position recorded in 'buffer[1]'
  //      (use getRowCol())
	else {
		getRowCol(buffer[1], row, col);
		
  //  (5) Set 'ship' to the (ShipType) casting of 'buffer[2]'
		ship = (ShipType) buffer[2];
  //  (6) Store 'buffer[0]' in 'command'
		command = buffer[0];
  //  (7) Empty 'buffer[0]' by setting it to 0
		buffer[0] = 0;
  //  (8) Return 'command'
		return(command);
	}


  //  III.  Finished:
	// Change this to return 'command', once you've given it a value
}



/*  PURPOSE:  To tell the user in window 'statusWindow' to shoot, and to inform
 *	the server thru the descriptor 'serverSocketFd' of the shot.  No return
 *	value.
 */
void	move	(StatusDisplayWindow&	statusWindow,
		 int			serverSocketFd
		)
{

  //  I.  Applicability validity check:


  //  II.  Move:

  int	row;
  int	col;
  int	positionBuffer;

  statusWindow.show("\nWhere would you like to shoot (or 'Q') to quit? ");
  row			= getRow(statusWindow);

  if  (row == -1)
  {
    //  YOUR CODE HERE TO:
    //  (1) Make 'positionBuffer' equal 'REQUEST_QUIT', but in network format
    //  (2) Send it to 'serverSocketFd'.  It has length 'COMMAND_LEN'
	  positionBuffer = (int)htonl(REQUEST_QUIT);
	  send(serverSocketFd, &positionBuffer, COMMAND_LEN, 0);
    return;
  }


  col			= getCol(statusWindow);
  //  YOUR CODE HERE TO:
  //  (1) Make 'positionBuffer' equal 'getPosition(row,col)', but in network
  //      format
  //  (2) Send it to 'serverSocketFd'.  It has length 'sizeof(int)'
	positionBuffer = (int)htonl( getPosition(row, col) );
	send(serverSocketFd, &positionBuffer, sizeof(int), 0);


  //  III.  Finished:

}



/*  PURPOSE:  To play the Battleship game where the user is informed of the
 *	current status in 'statusWindow' and the server is communicated to via
 *	file descriptor 'serverSocketFd'.  No return value.
 */
void	playGame	(StatusDisplayWindow&	statusWindow,
			 int			serverSocketFd
			)
			throw(const char*)
{

  //  I.  Applicability validity check:


  //  II.  Play game:

  BattleShipBoard	myBoard(ME_PLAYER);
  BattleShipBoard	oppenentBoard(OPPONENT_PLAYER);
  bool			stillPlaying	= true;

  statusWindow.show("\nPlease wait . . .");

  while  (stillPlaying)
  {
    int		row;
    int		col;
    ShipType	ship;

    switch ( getCommand(serverSocketFd,row,col,ship) )
    {
    case ACCEPTED_POSITIONING :
      statusWindow.show("\nPositions accepted -- please wait . . .");
      break;

    case ERROR_WITH_POSITIONING :
      myBoard.clear();
      //  Do _not_ 'break', go on to re-PUT_SHIPS:

    case PUT_SHIPS :
      myBoard.chooseShipPositions(statusWindow,serverSocketFd);
      break;

    case MOVE :
      move(statusWindow,serverSocketFd);
      break;

    case YOU_HIT :
      snprintf(cText,C_STRING_MAX,"\nYou hit their %s!",getShipName(ship));
      statusWindow.show(cText);
      oppenentBoard.displayHit(row,col);
      break;

    case YOU_MISSED :
      statusWindow.show("\nSorry, you missed.");
      oppenentBoard.displayMiss(row,col);
      break;

    case YOU_GOT_HIT :
      snprintf(cText,C_STRING_MAX,"\nOuch!  They hit your %s!",
	       getShipName(ship)
	      );
      statusWindow.show(cText);
      myBoard.displayHit(row,col);
      break;

    case YOU_WERE_MISSED :
      statusWindow.show("\nWhew!  They missed you.");
      myBoard.displayMiss(row,col);
      break;

    case YOU_SUNK :
      snprintf(cText,C_STRING_MAX,"\nYou hit their %s, and sunk the sucker!",
      	       getShipName(ship)
	      );
      statusWindow.show(cText);
      oppenentBoard.displayHit(row,col);
      break;

    case YOU_GOT_SUNK :
      snprintf(cText,C_STRING_MAX,"\nOh no!  They sunk your %s!",
      	       getShipName(ship)
	      );
      statusWindow.show(cText);
      myBoard.displayHit(row,col);
      break;

    case YOU_QUIT :
      statusWindow.show("\nYour fleet scrambles out of danger!"
		       "\nYou concede the High Seas to your opponent and loose."
      		       );
      stillPlaying = false;
      sleep(10);
      break;

    case OPPONENT_QUIT :
      statusWindow.show("\nThe oppenent's fleet steams off to safety!"
      			"\nThe High Seas are yours, you win!"
      		       );
      stillPlaying = false;
      sleep(10);
      break;

    case YOU_WON :
      statusWindow.show("\nYour fleet is battered but victorious!"
      			"\nThe High Seas are yours, you win!"
      		       );
      stillPlaying = false;
      sleep(10);
      break;

    case YOU_LOST :
      statusWindow.show("\nBetter go down to DAVY JONE'S LOCKER!"
      			"\nThat's where you'll find the remnants of your fleet."
      		       );
      stillPlaying = false;
      sleep(10);
      break;

    case IO_ERROR_QUIT :
      statusWindow.show("\nAn unrecoverable I/O error has occured.  Quiting.");
      stillPlaying = false;
      sleep(10);
      break;

    default :
      
      break;
    }

  }


  //  III.  Finished:

}



/*  PURPOSE:  To run the Battleship client progam.  'argc' tells the number of
 *	command line arguments coming from the OS and 'argv[]' lists those
 *	arguments.  Returns 'EXIST_SUCCESS' to OS on success or 'EXIT_FAILURE'
 *	otherwise.
 */
int	main	(int		argc,
		 const char*	argv[]
		)
{

  //  I.  Applicability validity check:



  //  II.  Play game:

  int		status			= EXIT_SUCCESS;

  try
  {

    //  II.A.  Ask user hostname and port to which to connect:

    int		serverPort;
    const char*	hostName;

    printf("Please make the screen at least %d rows x %d columns.\n",
    	   BOTTOM_BORDER+5,RIGHT_BORDER+3
          );
    obtainHostnameAndPort(argc,argv,hostName,serverPort);


    //  II.B.  Set up socket connection:

    int		serverSocketFd;

    serverSocketFd = getSocketDescriptor(hostName,serverPort);

    if  (serverSocketFd < 0)
    {
      snprintf(cText,C_STRING_MAX,"Could not connect to server on port %d\n",
      	       serverPort
	      );
      perror(cText);
      status = EXIT_FAILURE;
    }


    //  II.C.  Play game:

    StatusDisplayWindow*	statusDisplayWindowPtr	= NULL;

    if  (serverSocketFd >= 0)
    {
      initializeScreen(statusDisplayWindowPtr);
      playGame(*statusDisplayWindowPtr,serverSocketFd);
    }


    //  II.D.  Clean up after game:

    if  (serverSocketFd >= 0)
    {
      shutDownScreen(statusDisplayWindowPtr);
      //  YOUR CODE HERE TO close() 'serverSocketFd'
		close(serverSocketFd);
    }

  }
  catch  (const char* msg)
  {
    perror(msg);
    status = EXIT_FAILURE;
  }


  //  III.  Finished:

  return(status);
}
