/*
 *	This program to see if a mama thread and baby thread
 *	share the same global memory, stack memory and heap memory.
 */

#include <cstdlib>
#include <string>
#include <pthread.h>
#include <iostream>

using namespace std;

class	Book
{
  int		numPages;
  string	title;
  string	author;
  int		currentPage;
public :
  Book (int newNumPages, const string& newTitle, const string& newAuthor)
  {
    numPages	= newNumPages;
    title	= newTitle;
    author	= newAuthor;
    currentPage	= 1;
  }

  int	getNumPages () const	{ return(numPages); }

  const string&	getTitle () const	{ return(title); }

  const string&	getAuthor () const	{ return(author); }

  int	getCurrentPage () const	{ return(currentPage); }

  void	turnPage ()	{ currentPage++; }

};


ostream&	operator<<	(ostream& os, const Book& book)
{
  os << "We're on page " << book.getCurrentPage()
     << " of " << book.getNumPages()
     << " of " << book.getTitle()
     << " by " << book.getAuthor();
  return(os);
}


int	global	= 6;

Book*	bookPtr	= NULL;

void*	readToBaby	(void* ptr)
{
  global++;
  bookPtr->turnPage();

  int* iPtr = (int*)ptr;

  (*iPtr)++;

  return(NULL);
}


int main ()
{
  int local = global*2;
  pthread_t thread;
  bookPtr = new Book(40,"Green Eggs and Ham","Dr. Seuss");

  cout << "global == " << global << endl;
  cout << "local == " << local << endl;
  cout << *bookPtr << endl;

  pthread_create(&thread,NULL,readToBaby,(void*)&local);
  pthread_join(thread,NULL);

  cout << "global == " << global << endl;
  cout << "local == " << local << endl;
  cout << *bookPtr << endl;
  delete(bookPtr);
  return(EXIT_SUCCESS);
}
