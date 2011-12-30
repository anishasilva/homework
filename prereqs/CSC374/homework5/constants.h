/***************************************************************************
 ****									****
 ****		constants.h						****
 ****									****
 ****	    This file defines many constants (and few basic types) used	****
 ****	by both the Battleship client and server programs.		****
 ****									****
 ****	****	****	****	****	****	****	****	****	****
 ****									****
 ****	Version 1.0		Joseph Phillips		2010 November 7	****
 ****									****
 ***************************************************************************/

/*-------------------------------------------------------------------------*
 *---									---*
 *---		Command related simple types and constants:		---*
 *---									---*
 *-------------------------------------------------------------------------*/

#define	encodeCmd(c0,c1,c2,c3)		( ((c0)<< 0) |	\
					  ((c1)<< 8) |	\
					  ((c2)<<16) |	\
					  ((c3)<<24) 	\
					)

typedef		int			Command;

const int	COMMAND_LEN		= sizeof(Command);

const Command	REQUEST_PLAY		= encodeCmd('P','l','y','?');

const Command	YES			= encodeCmd('Y','e','s',' ');

const Command	NO			= encodeCmd('N','o',' ',' ');

const Command	PUT_SHIPS		= encodeCmd('P','u','t',' ');

const Command	ACCEPTED_POSITIONING	= encodeCmd('A','c','p','t');

const Command	ERROR_WITH_POSITIONING	= encodeCmd('E','r','o','r');

const Command	MOVE			= encodeCmd('M','o','v','e');

const Command	YOU_HIT			= encodeCmd('U','H','i','t');

const Command	YOU_MISSED		= encodeCmd('U','M','s','d');

const Command	YOU_GOT_HIT		= encodeCmd('G','t','H','t');

const Command	YOU_WERE_MISSED		= encodeCmd('G','t','M','s');

const Command	YOU_WON			= encodeCmd('U','W','o','n');

const Command	YOU_LOST		= encodeCmd('U','L','s','t');

const Command	REQUEST_QUIT		= encodeCmd('Q','u','i','t');

const Command	YOU_QUIT		= encodeCmd('U','Q','i','t');

const Command	OPPONENT_QUIT		= encodeCmd('O','Q','i','t');

const Command	YOU_SUNK		= encodeCmd('S','u','n','k');

const Command	YOU_GOT_SUNK		= encodeCmd('U','R','S','k');

const Command	IO_ERROR_QUIT		= encodeCmd('I','O','E','r');



/*-------------------------------------------------------------------------*
 *---									---*
 *---	    Board geometry related simple types and constants:		---*
 *---									---*
 *-------------------------------------------------------------------------*/

const int	ROW_LEN			= 3;

const int	COL_LEN			= 4;

const int	NUM_ROWS		= 10;

const int	NUM_COLS		= 10;

const int	LEFT_BOARD_COL_OFFSET	= 2;

const int	RIGHT_BOARD_COL_OFFSET	= NUM_COLS * COL_LEN		+
      					  2 * LEFT_BOARD_COL_OFFSET;

const int	BOTH_BOARD_ROW_OFFSET	= 1;

const int	TOP_BORDER		= 0;

const int	BOTTOM_BORDER		= NUM_ROWS*ROW_LEN+1;

const int	LEFT_BORDER		= 0;

const int	MIDDLE_BORDER		= RIGHT_BOARD_COL_OFFSET-1;

const int	RIGHT_BORDER		= 2*RIGHT_BOARD_COL_OFFSET-3;



/*-------------------------------------------------------------------------*
 *---									---*
 *---	    Communication related simple types and constants:		---*
 *---									---*
 *-------------------------------------------------------------------------*/

#define         INITIAL_HOST	                "localhost.localdomain"

const int	INITIAL_PORT                    = 20000;

const int       ERROR_DESCRIPTOR                = -1;

const int	BUFFER_LEN			= 3;

const int	BUFFER_BYTE_SIZE		= BUFFER_LEN * COMMAND_LEN;

const int       MAX_NUM_WAITING_CLIENTS         = 1;



/*-------------------------------------------------------------------------*
 *---									---*
 *---	    Player and ship id related simple types and constants:	---*
 *---									---*
 *-------------------------------------------------------------------------*/

/*  PURPOSE:  To represent an integer encoding a player.
 */
typedef	enum
	{
	 ME_PLAYER,
	 OPPONENT_PLAYER,

	 NUM_PLAYERS
	}
	PlayerType;


/*  PURPOSE:  To represent an integer encoding a ship.
 */
typedef	enum
	{
	 NO_SHIP		= -1,
	 AIRCRAFT_CARRIER_SHIP,
	 BATTLE_SHIP,
	 CRUISER_SHIP,
	 SUBMARINE_SHIP,
	 DESTROYER_SHIP,

	 ORIGINAL_NUM_SHIPS
	 
	}
	ShipType;



/*-------------------------------------------------------------------------*
 *---									---*
 *---	    Misc. variable related simple types and constants:		---*
 *---									---*
 *-------------------------------------------------------------------------*/

const int	C_STRING_MAX		= 256;
