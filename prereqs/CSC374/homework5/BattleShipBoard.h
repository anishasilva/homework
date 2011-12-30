/***************************************************************************
 ****									****
 ****		BattleShipBoard.h					****
 ****									****
 ****	    This file defines a class that manages the positioning of	****
 ****	the fleet, the status of the fleet (who's been hit, who's still	****
 ****	afloat), and the display of the fleet.				****
 ****									****
 ****	****	****	****	****	****	****	****	****	****
 ****									****
 ****	Version 1.0		Joseph Phillips		2010 November 7	****
 ****									****
 ***************************************************************************/

/*  PURPOSE:  To represent an integer encoding a player.
 */
class	BattleShipBoard
{

  //  I.  Member vars:

  PlayerType	player;

  //  YOUR CODE TO DECLARE A "WINDOW*" VARIABLE TO DISPLAY THIS BOARD
	WINDOW *windowPtr;

  int		numShipsFloating;

  ShipType	shipAt[NUM_ROWS][NUM_COLS];

  bool		wasShot[NUM_ROWS][NUM_COLS];

  bool		isShipFloating[ORIGINAL_NUM_SHIPS];

  int		bowRow[ORIGINAL_NUM_SHIPS];

  int		bowCol[ORIGINAL_NUM_SHIPS];

  int		sternRow[ORIGINAL_NUM_SHIPS];

  int		sternCol[ORIGINAL_NUM_SHIPS];


  //  II.  Disallowed automatically created method:

  /*  Disallow default constructor.
   */
  BattleShipBoard	();


  /*  Disallow copy constructor.
   */
  BattleShipBoard	(const BattleShipBoard&);


  /*  Disallow copy assignment.
   */
  BattleShipBoard&	operator=(const BattleShipBoard&);


  //  III.  Private and protected methods:

  /*  PURPOSE:  To graphically display ship's position.
   */

  /*  PURPOSE:  To graphically display the position of ship 'ship'.  No return
   *	value.
   */
  void	displayAndRecordOnBoardShipPosition	(ShipType	ship
  						)
						throw()
  {

    //  I.  Applicability validity check:


    //  II.  Display ship's position:

    if  (sternRow[ship] == bowRow[ship])
    {
      int	col;
      int	row	= sternRow[ship];
      int	hiCol	= intMax(sternCol[ship],bowCol[ship]);

      for  (col = intMin(sternCol[ship],bowCol[ship]);  col <= hiCol;  col++)
      {
	shipAt[row][col] = ship;

	if  ( (col == sternCol[ship])  &&  (sternCol[ship] < bowCol[ship]) )
	{
	  //  YOUR CODE HERE TO:
	  //  (1) Move to row=0+row*ROW_LEN, column=col*COL_LEN
	  //  (2) Print "  /-"
	  //  (3) Move to row=1+row*ROW_LEN, column=col*COL_LEN
	  //  (4) Print " |"
	  //  (5) Move to row=2+row*ROW_LEN, column=col*COL_LEN
	  //  (6) Print "  \\-"
	  wmove(windowPtr,0+row*ROW_LEN,col*COL_LEN);
	  waddstr(windowPtr,"  /-");
	  wmove(windowPtr,1+row*ROW_LEN,col*COL_LEN);
	  waddstr(windowPtr," |");
	  wmove(windowPtr,2+row*ROW_LEN,col*COL_LEN);
	  waddstr(windowPtr,"  \\-");
	}
	else  if  ( (col == sternCol[ship]) && (sternCol[ship] > bowCol[ship]) )
	{
	  //  YOUR CODE HERE TO:
	  //  (1) Move to row=0+row*ROW_LEN, column=col*COL_LEN
	  //  (2) Print "-\\"
	  //  (3) Move to row=1+row*ROW_LEN, column=col*COL_LEN
	  //  (4) Print "  |"
	  //  (5) Move to row=2+row*ROW_LEN, column=col*COL_LEN
	  //  (6) Print "-/"
		wmove(windowPtr,0+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"-\\");
		wmove(windowPtr,1+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"  |");
		wmove(windowPtr,2+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"-/");
	}
	else  if  ( (col == bowCol[ship])  &&  (sternCol[ship] < bowCol[ship]) )
	{
	  //  YOUR CODE HERE TO:
	  //  (1) Move to row=0+row*ROW_LEN, column=col*COL_LEN
	  //  (2) Print "\\"
	  //  (3) Move to row=1+row*ROW_LEN, column=col*COL_LEN
	  //  (4) Print " >-"
	  //  (5) Move to row=2+row*ROW_LEN, column=col*COL_LEN
	  //  (6) Print col*COL_LEN
		wmove(windowPtr,0+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"\\");
		wmove(windowPtr,1+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr," >-");
		wmove(windowPtr,2+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"/");
	}
	else  if  ( (col == bowCol[ship])  &&  (sternCol[ship] > bowCol[ship]) )
	{
	  //  YOUR CODE HERE TO:
	  //  (1) Move to row=0+row*ROW_LEN, column=col*COL_LEN
	  //  (2) Print "   /"
	  //  (3) Move to row=1+row*ROW_LEN, column=col*COL_LEN
	  //  (4) Print " -<"
	  //  (5) Move to row=2+row*ROW_LEN, column=col*COL_LEN
	  //  (6) Print "   \\"
		wmove(windowPtr,0+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"   /");
		wmove(windowPtr,1+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr," -<");
		wmove(windowPtr,2+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"   \\");
	}
	else
	{
	  //  YOUR CODE HERE TO:
	  //  (1) Move to row=0+row*ROW_LEN, column=col*COL_LEN
	  //  (2) Print "----"
	  //  (3) Move to row=1+row*ROW_LEN, column=col*COL_LEN
	  //  (4) Print "" (Nothing)
	  //  (5) Move to row=2+row*ROW_LEN, column=col*COL_LEN
	  //  (6) Print "----"
		wmove(windowPtr,0+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"----");
		wmove(windowPtr,1+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"");
		wmove(windowPtr,2+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"----");
	}

      }

    }
    else  if  (sternCol[ship] == bowCol[ship])
    {
      int	row;
      int	col	= sternCol[ship];
      int	hiRow	= intMax(sternRow[ship],bowRow[ship]);

      for  (row = intMin(sternRow[ship],bowRow[ship]);  row <= hiRow;  row++)
      {
	shipAt[row][col] = ship;

	if  ( (row == sternRow[ship])  &&  (sternRow[ship] < bowRow[ship]) )
	{
	  //  YOUR CODE HERE TO:
	  //  (1) Move to row=0+row*ROW_LEN, column=col*COL_LEN
	  //  (2) Print "  _"
	  //  (3) Move to row=1+row*ROW_LEN, column=col*COL_LEN
	  //  (4) Print " / \\"
	  //  (5) Move to row=2+row*ROW_LEN, column=col*COL_LEN
	  //  (6) Print " | |"
		wmove(windowPtr,0+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"  _");
		wmove(windowPtr,1+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr," / \\");
		wmove(windowPtr,2+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr," | |");
	}
	else  if  ( (row == sternRow[ship]) && (sternRow[ship] > bowRow[ship]) )
	{
	  //  YOUR CODE HERE TO:
	  //  (1) Move to row=0+row*ROW_LEN, column=col*COL_LEN
	  //  (2) Print " | |"
	  //  (3) Move to row=1+row*ROW_LEN, column=col*COL_LEN
	  //  (4) Print " \\ /"
	  //  (5) Move to row=2+row*ROW_LEN, column=col*COL_LEN
	  //  (6) Print "  -"
		wmove(windowPtr,0+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr," | |");
		wmove(windowPtr,1+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr," \\ /");
		wmove(windowPtr,2+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"  -");
	}
	else  if  ( (row == bowRow[ship])  &&  (sternRow[ship] < bowRow[ship]) )
	{
	  //  YOUR CODE HERE TO:
	  //  (1) Move to row=0+row*ROW_LEN, column=col*COL_LEN
	  //  (2) Print " \\ /"
	  //  (3) Move to row=1+row*ROW_LEN, column=col*COL_LEN
	  //  (4) Print "  V"
	  //  (5) Move to row=2+row*ROW_LEN, column=col*COL_LEN
	  //  (6) Print "  |"
		wmove(windowPtr,0+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr," \\ /");
		wmove(windowPtr,1+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"  V");
		wmove(windowPtr,2+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"  |");
	}
	else  if  ( (row == bowRow[ship])  &&  (sternRow[ship] > bowRow[ship]) )
	{
	  //  YOUR CODE HERE TO:
	  //  (1) Move to row=0+row*ROW_LEN, column=col*COL_LEN
	  //  (2) Print "  |"
	  //  (3) Move to row=1+row*ROW_LEN, column=col*COL_LEN
	  //  (4) Print "  ^"
	  //  (5) Move to row=2+row*ROW_LEN, column=col*COL_LEN
	  //  (6) Print " / \\"
		wmove(windowPtr,0+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"  |");
		wmove(windowPtr,1+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr,"  ^");
		wmove(windowPtr,2+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr," / \\");
	}
	else
	{
	  //  YOUR CODE HERE TO:
	  //  (1) Move to row=0+row*ROW_LEN, column=col*COL_LEN
	  //  (2) Print " | |"
	  //  (3) Move to row=1+row*ROW_LEN, column=col*COL_LEN
	  //  (4) Print " | |"
	  //  (5) Move to row=2+row*ROW_LEN, column=col*COL_LEN
	  //  (6) Print " | |"
		wmove(windowPtr,0+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr," | |");
		wmove(windowPtr,1+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr," | |");
		wmove(windowPtr,2+row*ROW_LEN,col*COL_LEN);
		waddstr(windowPtr," | |");
	}

      }

    }


    //  YOUR CODE HERE TO MAKE WHAT YOU PRINTED VISIBLE!
	  wrefresh(windowPtr);


    //  III.  Finished:

  }


  /*  PURPOSE:  To tell the user in window 'statusWindow' to choose the
   *	position of ship 'ship'.  No return value.
   */
  void	chooseShipPosition	(StatusDisplayWindow&	statusWindow,
				 ShipType		ship
				)
				throw(const char*)
  {

    //  I.  Applicability validity check:


    //	II.  Place each ship:

    int	     sternR;
    int	     sternC;
    int	     bowR;
    int	     bowC;
    int	     row;
    int	     col;
    bool     haveLegalPlacement	= false;


    //  II.A.  Each iteration attempts another placement for 'ship':

    do
    {

      //  II.A.1.  Ask for position:

      snprintf(cText,C_STRING_MAX,
	       "\nEnter position of the length %d %s, stern to bow: ",
	       getShipLength(ship),getShipName(ship)
	      );
      statusWindow.show(cText);


      //  II.A.2.  Get position:

      while  ( (sternR = getRow(statusWindow)) == -1 )
	statusWindow.backspace();

      sternC = getCol(statusWindow);
      statusWindow.show(" to ");

      while  ( (bowR = getRow(statusWindow)) == -1 )
        statusWindow.backspace();

      bowC = getCol(statusWindow);


      //  II.A.3.  Disallow diagonal ship placement:

      if  ( (sternR != bowR)  &&  (sternC != bowC) )
      {
	statusWindow.show("\nNo diagonals.");
        continue;
      }


      //  II.A.4.  Handle horizontal ship placement:

      if  (sternR == bowR)
      {

        //  Disallow wrong length:

        if  ( (abs(sternC-bowC)+1) != getShipLength(ship) )
	{
	  statusWindow.show("\nWrong length!");
	  continue;
	}


	//  Disallow placement on existing ships:

	for  (col = intMin(sternC,bowC);  col <= intMax(sternC,bowC);  col++)
	  if  (shipAt[sternR][col] != NO_SHIP)
	    break;

	if  (col <= intMax(sternC,bowC))
	{
	  statusWindow.show("\nNo ships on top of ships!");
	  continue;
	}


	//  Plot ship on board and on screen:

	haveLegalPlacement	= true;
      }
      else  if  (sternC == bowC)
      {

        //  Handle vertical ship placement:


        //  Disallow wrong length:

        if  ( (abs(sternR-bowR)+1) != getShipLength(ship) )
	{
	  statusWindow.show("\nWrong length!");
	  continue;
	}


	//  Disallow placement on existing ships:

	for  (row = intMin(sternR,bowR);  row <= intMax(sternR,bowR);  row++)
	  if  (shipAt[row][sternC] != NO_SHIP)
	    break;

	if  (row <= intMax(sternR,bowR))
	{
	  statusWindow.show("\nNo ships on top of ships!");
	  continue;
	}


	//  Plot ship on board and on screen:

	haveLegalPlacement	= true;
      }      

    }
    while  ( !haveLegalPlacement );


    //  II.B.  Record position of and display ship:

    bowRow[ship]	= bowR;
    bowCol[ship]	= bowC;
    sternRow[ship]	= sternR;
    sternCol[ship]	= sternC;
    displayAndRecordOnBoardShipPosition(ship);


    //  III.  Finished:

  }


public :

  /*  PURPOSE:  To initialize '*this' board to be blank and all ships to be
   *  	afloat but unpositioned.  Sets 'player' to 'newPlayer'.  No return
   *	value.
   */
  BattleShipBoard	(PlayerType	newPlayer)
  {

    //  I.  Applicability validity check:


    //  II.  Initialize member vars:

    //	II.A.  Initialize 'player':

    player	= newPlayer;
    // YOUR CODE HERE TO
    // (1) Make your window of size NUM_ROWS*ROW_LEN rows * NUM_COLS*COL_LEN
    //     columns.  If (player==0) it should be placed at row
    //	   BOTH_BOARD_ROW_OFFSET, column LEFT_BOARD_COL_OFFSET; else it should
    //	   be placed at row BOTH_BOARD_ROW_OFFSET column RIGHT_BOARD_COL_OFFSET
    // (2) Make your window non-scrolling
	  initscr();
	  clear();
	  if (player == 0){
		  windowPtr = newwin(NUM_ROWS*ROW_LEN, NUM_COLS*COL_LEN, BOTH_BOARD_ROW_OFFSET, LEFT_BOARD_COL_OFFSET);
	  }
	  else {
		  windowPtr = newwin(NUM_ROWS*ROW_LEN, NUM_COLS*COL_LEN, BOTH_BOARD_ROW_OFFSET, RIGHT_BOARD_COL_OFFSET);
	  }
	  scrollok(windowPtr, FALSE);

		


    //  II.B.  Initialize everything else:

    clear();


    //  III.  Finished:

  }



  /*  PURPOSE:  To release the resources of '*this' object.  No parameters.
   *	No return value.
   */
  ~BattleShipBoard	()
  {

    //  I.  Applicability validity check:


    //  II.  Release resources:

    //  YOUR CODE HERE TO release resources of your window
	  delwin(windowPtr);
	  endwin();


    //  III.  Finished:

  }



  /*  PURPOSE:  To return the number of ships still floating.
   */
  int	getNumShipsFloating () const	throw()
  {
    return(numShipsFloating);
  }


  /*  PURPOSE:  To clear '*this' board.  No parameters.  No return value.
   */
  void	clear	()
  {

    //  I.  Applicability validity check:


    //  II.  Clear '*this' board:

    //  II.A.  Initialize 'numShipsFloating':

    numShipsFloating	= ORIGINAL_NUM_SHIPS;


    //  II.B.  Initialize 'shipAt[][]' and 'wasShot[][]':

    int		row;
    int		col;
    int		ship;

    for  (row = 0;  row < NUM_ROWS;  row++)
      for  (col = 0;  col < NUM_COLS;  col++)
      {
        shipAt[row][col]	= NO_SHIP;
        wasShot[row][col]	= false;
      }


    //  II.B.  Initialize 'isShipFloating[]', 'bowRow[]',
    //	       'bowCol[]', 'sternRow[]' and 'sternCol[]':

    for  (ship = 0;  ship < ORIGINAL_NUM_SHIPS;  ship++)
    {
      isShipFloating[ship] = true;
      bowRow[ship]	   = -1;
      bowCol[ship]	   = -1;
      sternRow[ship]	   = -1;
      sternCol[ship]	   = -1;
    }


    //  III.  Finished:

  }


  /*  PURPOSE:  To tell the user in window 'statusWindow' to choose the
   *	positions of all of their ships.  Server informed of ship positions by
   *	them being write()n to socket file descriptor 'socketFd'.  No return
   *	value.
   */
  void	chooseShipPositions	(StatusDisplayWindow&	statusWindow,
				 int			socketFd
				)
				throw(const char*)
  {

    //  I.  Applicability validity check:


    //	II.  Place each ship:

    //  YOUR CODE HERE TO
    //  (1) Ask the user the position of each ship (use chooseShipPosition())
    //  (2) Then, for each ship:
    //    (a) Get its position from bowRow[],bowCol[],sternRow[],sternCol[] and
    //	      place it into a single integer (use getDoublePosition())
    //    (b) Send the ship integer (0 to (ORIGINAL_NUM_SHIPS-1)) followed by
    //	      position integer to 'socketFd'.
    //	PLEASE NOTE:
    //	(1) There are sizeof(int) bytes in an integer
    //  (2) Make sure you send the integer in proper network endianness 
	  
	  chooseShipPosition(statusWindow, AIRCRAFT_CARRIER_SHIP);
	  
	  chooseShipPosition(statusWindow, BATTLE_SHIP);
	  
	  chooseShipPosition(statusWindow, CRUISER_SHIP);
	  
	  chooseShipPosition(statusWindow, SUBMARINE_SHIP);
	  
	  chooseShipPosition(statusWindow, DESTROYER_SHIP);
	  
	  int shipPosition[ORIGINAL_NUM_SHIPS]; 
	  
	  shipPosition[0] = getDoublePosition( bowRow[AIRCRAFT_CARRIER_SHIP], bowCol[AIRCRAFT_CARRIER_SHIP], 
				   sternRow[AIRCRAFT_CARRIER_SHIP], sternCol[AIRCRAFT_CARRIER_SHIP] );
	  
	  shipPosition[1] = getDoublePosition( bowRow[BATTLE_SHIP], bowCol[BATTLE_SHIP], 
										 sternRow[BATTLE_SHIP], sternCol[BATTLE_SHIP] );
	  
	  shipPosition[2] = getDoublePosition( bowRow[CRUISER_SHIP], bowCol[CRUISER_SHIP], 
										 sternRow[CRUISER_SHIP], sternCol[CRUISER_SHIP] );
	  
	  shipPosition[3] = getDoublePosition( bowRow[SUBMARINE_SHIP], bowCol[SUBMARINE_SHIP], 
										 sternRow[SUBMARINE_SHIP], sternCol[SUBMARINE_SHIP] );
	  
	  shipPosition[4] = getDoublePosition( bowRow[DESTROYER_SHIP], bowCol[DESTROYER_SHIP], 
										 sternRow[DESTROYER_SHIP], sternCol[DESTROYER_SHIP] );
	  
	  int i;
	  int shipNum[ORIGINAL_NUM_SHIPS];
	  int networkShipPosition[ORIGINAL_NUM_SHIPS];
	  
	  for (i = 0; i < ORIGINAL_NUM_SHIPS; i++) {
		  shipNum[i] = (int)htonl(i);
		  networkShipPosition[i] = (int)htonl(shipPosition[i]);
		  send(socketFd, &shipNum[i], sizeof(i), 0);
		  send(socketFd, &networkShipPosition[i], sizeof(int), 0);
							
	  }
	  /*
	  if (send(socketFd, AIRCRAFT_CARRIER_SHIP,sizeof(AIRCRAFT_CARRIER_SHIP), 0) == -1) {
		  perror("send error");
	  }
	  send(socketFd, shipSending[0], sizeof(int), 0);
	  send(socketFd, BATTLE_SHIP, sizeof(BATTLE_SHIP), 0);
	  send(socketFd, shipSending[1], sizeof(int), 0);
	  send(socketFd, CRUISER_SHIP, sizeof(CRUISER_SHIP), 0);
	  send(socketFd, shipSending[2], sizeof(int), 0);
	  send(socketFd, SUBMARINE_SHIP, sizeof(SUBMARINE_SHIP), 0);
	  send(socketFd, shipSending[3], sizeof(int), 0);
	  send(socketFd, DESTROYER_SHIP, sizeof(DESTROYER_SHIP), 0);
	   */

	   
    //  III.  Finished:

  }



  /*  PURPOSE:  To return 'true' if placing ship 'ship' between bow position
   *	<'bowR','bowC'> and stern position <'sternR','sternC'> is legal, or
   *	to return 'false' otherwise.  If it is legal then it places 'ship'
   *	at the specified location.
   */
  bool	wasPlacingShipSuccessful	(ShipType	ship,
					 int		bowR,
					 int		bowC,
					 int		sternR,
					 int		sternC
		  			)
  {

    //  I.  Applicability validity check:


    //  II.  Check location:

    //  II.A.  See if ship has already been placed:

    if  (bowRow[ship] != -1)
      return(false);


    //  II.B.  Disallow diagonals:

    if  ( (sternR != bowR)  &&  (sternC != bowC) )
      return(false);


    //  II.C.  Check horizontal:

    if  (sternR == bowR)
    {
      int	col;


      //  II.C.1.  Disallow wrong length:

      if  ( (abs(sternC-bowC)+1) != getShipLength(ship) )
	return(false);


      //  II.C.2.  Disallow placement on existing ships:

      for  (col = intMin(sternC,bowC);  col <= intMax(sternC,bowC);  col++)
	if  (shipAt[sternR][col] != NO_SHIP)
	  return(false);


      //  II.C.3.  Note on board that a ship is now there:

      for  (col = intMin(sternC,bowC);  col <= intMax(sternC,bowC);  col++)
        shipAt[sternR][col] = ship;

    }


    //  II.D.  Check vertical:

    if  (sternC == bowC)
    {
      int	row;


      //  II.D.1.  Disallow wrong length:

      if  ( (abs(sternR-bowR)+1) != getShipLength(ship) )
	return(false);


      //  II.D.2.  Disallow placement on existing ships:

      for  (row = intMin(sternR,bowR);  row <= intMax(sternR,bowR);  row++)
	if  (shipAt[row][sternC] != NO_SHIP)
	  return(false);


      //  II.D.3.  Note on board that a ship is now there

      for  (row = intMin(sternR,bowR);  row <= intMax(sternR,bowR);  row++)
        shipAt[row][sternC] = ship;

    }


    //  III.  Place ship there:

    bowRow[ship]	= bowR;
    bowCol[ship]	= bowC;
    sternRow[ship]	= sternR;
    sternCol[ship]	= sternC;


    //  IV.  Finished:

    return(true);
  }



  /*  PURPOSE:  To display a hit to row 'row' and column 'col'.  No return
   *	value.
   */
  void	displayHit	(int	row,
  			 int	col
  			)
  {

    //  I.  Applicability validity check:


    //  II.  Display hit:

    //  YOUR CODE HERE TO
    //  (1) Move to row=0+row*ROW_LEN, column=col*COL_LEN+1
    //  (2) Print "X X"
    //  (3) Move to row=1+row*ROW_LEN, column=col*COL_LEN+2
    //  (4) Print "X"
    //  (5) Move to row=2+row*ROW_LEN, column=col*COL_LEN+1
    //  (6) Print "X X"
    //  (7) MAKE YOUR CHANGES VISIBLE
	  wmove(windowPtr,0+row*ROW_LEN,col*COL_LEN+1);
	  waddstr(windowPtr,"X X");
	  wmove(windowPtr,1+row*ROW_LEN,col*COL_LEN+2);
	  waddstr(windowPtr,"X");
	  wmove(windowPtr,2+row*ROW_LEN,col*COL_LEN+1);
	  waddstr(windowPtr,"X X");
	  
	  wrefresh(windowPtr);
	  


    //  III.  Finished:

  }



  /*  PURPOSE:  To display a miss to row 'row' and column 'col'.  No return
   *	value.
   */
  void	displayMiss	(int	row,
  			 int	col
  			)
  {

    //  I.  Applicability validity check:


    //  II.  Display miss:

    //  YOUR CODE HERE TO
    //  (1) Move to row=0+row*ROW_LEN, columncol*COL_LEN+1
    //  (2) Print ". ."
    //  (3) Move to row=1+row*ROW_LEN, columncol*COL_LEN+2
    //  (4) Print "."
    //  (5) Move to row=2+row*ROW_LEN, columncol*COL_LEN+1
    //  (6) Print ". ."
    //  (7) MAKE YOUR CHANGES VISIBLE
	  wmove(windowPtr,0+row*ROW_LEN,col*COL_LEN+1);
	  waddstr(windowPtr,". .");
	  wmove(windowPtr,1+row*ROW_LEN,col*COL_LEN+2);
	  waddstr(windowPtr,".");
	  wmove(windowPtr,2+row*ROW_LEN,col*COL_LEN+1);
	  waddstr(windowPtr,". .");
	  
	  wrefresh(windowPtr);


    //  III.  Finished:

  }


  /*  PURPOSE:  To set 'ship' to the index of the ship hit by shooting row 'row'
   *	column 'col', or to NO_SHIP if there was no ship there.  Sets the shot
   *	position to 'NO_SHIP'.  If no (new) ship was sunk then returns 'false'.
   *	If a ship was sunk then records this in 'isShipFloating[]' and
   *	'numShipsFloating', and returns 'true'.
   */
  bool	wasShipSunkByShooting	(int		row,
  				 int		col,
				 ShipType&	ship
				)
  {

    //  I.  Applicability validity check:


    //  II.  Handle shot:

    //  II.A.  See what, if anything, was hit:

    ship = shipAt[row][col];


    //  II.B.  Handle when hit empty sea:

    if  (ship == NO_SHIP)
      return(false);


    //  II.C.  Handle when hit a ship:

    int count	= 0;	// To count number of ship positions not-yet sunk

    shipAt[row][col]	= NO_SHIP;

    if  (sternRow[ship] == bowRow[ship])
    {
      int col;

      for  (col  = intMin(sternCol[ship],bowCol[ship]);
            col <= intMax(sternCol[ship],bowCol[ship]);
	    col++
	   )
        if  (shipAt[sternRow[ship]][col] != NO_SHIP)
	{
	  count++;
	  break;
	}

    }
    else
    {
      int row;

      for  (row  = intMin(sternRow[ship],bowRow[ship]);
            row <= intMax(sternRow[ship],bowRow[ship]);
	    row++
	   )
        if  (shipAt[row][sternCol[ship]] != NO_SHIP)
	{
	  count++;
	  break;
	}

    }


    //  III.  Finished:

    //  III.A.  Handle when ship sunk:

    if  (count == 0)
    {
      isShipFloating[ship]	= false;
      numShipsFloating--;
      return(true);
    }


    //  III.A.  Handle when ship not yet sunk:

    return(false);
  }

};
