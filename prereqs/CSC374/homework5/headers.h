/***************************************************************************
 ****									****
 ****		headers.h						****
 ****									****
 ****	    This file includes header files common to both the		****
 ****	Battleship client and server programs.				****
 ****									****
 ****	****	****	****	****	****	****	****	****	****
 ****									****
 ****	Version 1.0		Joseph Phillips		2010 November 7	****
 ****									****
 ***************************************************************************/



/*---	----	----	----	----	----	----	----	----	---*
 *---									---*
 *---			    Common inclusion:				---*
 *---									---*
 *---	----	----	----	----	----	----	----	----	---*/

#include	<stdlib.h>		// For standard libraries
#include	<stdio.h>		// For I/O
#include	<string.h>		// For memcpy()
#include	<ctype.h>		// For isdigit()

#include	<sys/socket.h>		// For socket()
#include	<netinet/in.h>		// For sockaddr_in and htons()

#include	<errno.h>

#include	<ncurses.h>

using		namespace	std;



/*---	----	----	----	----	----	----	----	----	---*
 *---									---*
 *---			    Common inclusion:				---*
 *---									---*
 *---	----	----	----	----	----	----	----	----	---*/

#include	"constants.h"

#include	"declare.h"

#include	"StatusDisplayWindow.h"

#include	"BattleShipBoard.h"
