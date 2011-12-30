/***************************************************************************
 ****									****
 ****		StatusDisplayWindow.h					****
 ****									****
 ****	    This file defines a class that manages the text window used	****
 ****	for text communication to/from the user and the server.		****
 ****									****
 ****	****	****	****	****	****	****	****	****	****
 ****									****
 ****	Version 1.0		Joseph Phillips		2010 November 7	****
 ****									****
 ***************************************************************************/

class	StatusDisplayWindow
{

  //  I.  Member vars:

  //  YOUR CODE HERE TO MAKE YOUR OWN "WINDOW*" VARIABLE
	WINDOW* windowPtr;

  //  II.  Disallowed auto-created methods:

  /*  Disallow copy-constructor.
   */
  StatusDisplayWindow	(const StatusDisplayWindow&);


  /*  Disallow copy-assignment.
   */
  StatusDisplayWindow&	operator=(const StatusDisplayWindow&);


public:

  //  III.  Constructor(s) and destructor:

  /*  PURPOSE:  To create '*this' object.  No parameters.  No return value.
   */
  StatusDisplayWindow	()
  {

    //  I.  Applicability validity check:


    //  II.  Initialize member vars:

    //  YOUR CODE HERE TO:
    //  (1) Make your window at position row=BOTTOM_BORDER+1, column=1 have
    //      3 rows and RIGHT_BORDER-2 columns.
    //  (2) Allow this window to scroll.
	  initscr();
	  windowPtr = newwin(3, RIGHT_BORDER, BOTTOM_BORDER+1, 1);
	  scrollok(windowPtr, TRUE);
	  

    //  III.  Finished:

  }


  /*  PURPOSE:  To release the resources of '*this' object.  No parameters.
   *  	No return value.
   */
  ~StatusDisplayWindow	()
  {

    //  I.  Applicability validity check:


    //  II.  Release member vars:

    //  YOUR CODE HERE TO:
    //  (1) remove the memory associated with your window
	  delwin(windowPtr);
	  endwin();


    //  III.  Finished:

  }


  /*  PURPOSE:  To display 'chr'.  No return value.
   */
  void	show	(char	chr)
  {

    //  I.  Applicability validity check:


    //  II.  Release member vars:

    //  YOUR CODE HERE TO PRINT 'chr'.  BE SURE IT IS VISIBLE!
	  waddch(windowPtr, chr);
	  wrefresh(windowPtr);


    //  III.  Finished:

  }


  /*  PURPOSE:  To display 'text'.  No return value.
   */
  void	show	(const char*	text)
  {

    //  I.  Applicability validity check:


    //  II.  Release member vars:

    //  YOUR CODE HERE TO PRINT 'text'.  BE SURE IT IS VISIBLE!
	  waddstr(windowPtr, text);
	  wrefresh(windowPtr);


    //  III.  Finished:

  }


  /*  PURPOSE:  To backspace over the last char at the current cursor position,
   *	if not at the first column.  No parameters.  No return value.
   */
  void	backspace	()
  {

	  
    //  I.  Applicability validity check:


    //  II.  Do backspace:

    //  YOUR CODE HERE TO:
    //  (1) Get the current row and column of the cursor in your window
    //  (2) If the col is greater than 0 then:
    //     (a) moves the cursor back to row,col-1
    //     (b) print a space
    //     (c) moves the cursor back to row,col-1 again
    //     MAKE SURE THE CHANGE IS VISIBLE!
	  int row;
	  int col;
	  
	  getyx(windowPtr, row, col);
	  
	  if (col > 0) {
		  wmove(windowPtr, row, col-1);
		  waddch(windowPtr, ' ');
		  wmove(windowPtr, row, col-1);
	  }
	  
	  wrefresh(windowPtr);


    //  III.  Finished:

  }

};
