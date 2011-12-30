/***************************************************************************
 ****									****
 ****		declare.h						****
 ****									****
 ****	    This file declares functions that the client and server	****
 ****	use that are defined in battleShipCommon.cpp.  Also defines	****
 ****	some very simple inline functions.				****
 ****									****
 ****	****	****	****	****	****	****	****	****	****
 ****									****
 ****	Version 1.0		Joseph Phillips		2010 November 7	****
 ****									****
 ***************************************************************************/



/*---	----	----	----	----	----	----	----	----	---*
 *---									---*
 *---			    Variable declarations:			---*
 *---									---*
 *---	----	----	----	----	----	----	----	----	---*/

class				StatusDisplayWindow;

extern	char			cText[C_STRING_MAX];



/*---	----	----	----	----	----	----	----	----	---*
 *---									---*
 *---		    (re-)Declaration of C I/O functions whose		---*
 *---		     names would be mangled by C++ compiler:		---*
 *---									---*
 *---	----	----	----	----	----	----	----	----	---*/

extern "C"
{
  int   close(int );
  int   read (int , void*, size_t);
  int   write(int , const void*, size_t);
}



/*---	----	----	----	----	----	----	----	----	---*
 *---									---*
 *---			    Function declarations:			---*
 *---									---*
 *---	----	----	----	----	----	----	----	----	---*/

inline int	intMin		(int i, int j)	{ return( (i<j) ? i : j); }

inline int	intMax		(int i, int j)	{ return( (i>j) ? i : j); }

ssize_t 	rio_recv	(int fd, void* usrbuf, size_t n, int flags);

int		getSocketDescriptor
				(const char* hostname, int portNumber)
				throw (const char*);

int    		getRow		(StatusDisplayWindow&);

int    		getCol		(StatusDisplayWindow&);

const char*	getShipName	(ShipType	)
	      			throw(const char*);

int		getShipLength	(ShipType	)
	      			throw(const char*);

inline int	getPosition	(int row, int col)
				{ return( (row << 8) | (col << 0) ); }

inline int	getDoublePosition
				(int row0, int col0, int row1, int col1)
				{
				  return( (row0 << 24) |
					  (col0 << 16) |
					  (row1 <<  8) |
				  	  (col1 <<  0)
					);
				}

inline void	getRowCol	(int position, int& row, int& col)
       				{
				  row = (position >> 8) & 0xFF;
				  col = (position >> 0) & 0xFF;
				}

inline void	getDoubleRowCol	(int position,
       				 int& row0, int& col0,
       				 int& row1, int& col1
				)
       				{
				  row0 = (position >> 24) & 0xFF;
				  col0 = (position >> 16) & 0xFF;
				  row1 = (position >>  8) & 0xFF;
				  col1 = (position >>  0) & 0xFF;
				}


#define		safeDelete(p)	{ if ((p)!=NULL) { delete(p); (p)=NULL; } }
