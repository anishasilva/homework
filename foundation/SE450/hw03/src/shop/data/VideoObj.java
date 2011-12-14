package shop.data;

/**
 * Implementation of Video interface.
 * @see Data
 */
final class VideoObj implements Video {
  private final String _title;
  private final int    _year;
  private final String _director;

  /**
   * Initialize all object attributes.
   * Title and director are "trimmed" to remove leading and final space.
   * @throws IllegalArgumentException if object invariant violated.
   */
  VideoObj(String title, int year, String director) {
	  try {
		  title = checkString(title);
		  director = checkString(director);
	  } catch (IllegalArgumentException e) {
		  throw e;
	  }
	  
	  if (year >= 5000 || year <= 1800)
		  throw new IllegalArgumentException();
	  
    _title = title;
    _year = year;
    _director = director;
  }
  private String checkString(String stringIn){
	  if (stringIn == null || stringIn.isEmpty())
		  throw new IllegalArgumentException();
	  
	  if (stringIn.charAt(0) == ' ')
		  stringIn = stringIn.substring(1);
	  
	  if ( stringIn.isEmpty() )
		  throw new IllegalArgumentException();
	  
	  if (stringIn.charAt( stringIn.length() - 1 ) == ' ')
		  stringIn = stringIn.substring(0, stringIn.length() - 1);
	  
	  if ( stringIn.isEmpty() )
		  throw new IllegalArgumentException();
	
	  return stringIn;
  }

  public String director() {
    return _director;
  }

  public String title() {
    return _title;
  }

  public int year() {
    return _year;
  }

  public boolean equals(Object thatObject) {
	  if ( !(thatObject instanceof VideoObj) )
		  return false;
	  
	  VideoObj that = (VideoObj) thatObject;
	  
	  return ( this.year() == that.year() 
			  && this.director().equals(that.director() ) 
			  && this.title().equals( that.title() ) );
  }

  private final int HASH_MULTIPLIER = 37;
  public int hashCode() {
	  int result = 17;
	  result = HASH_MULTIPLIER*result + _title.hashCode();
	  result = HASH_MULTIPLIER*result + _year;
	  result = HASH_MULTIPLIER*result + _director.hashCode();

	  return result;
  }

  public int compareTo(Video that) {
	  int compare = _title.compareTo(that.title());

	  if ( compare != 0 )
		  return compare;
	  compare = _year - that.year();

	  if (compare != 0)
		  return compare;
	  compare = _director.compareTo(that.director());
	  return compare;
  }

  public String toString() {
	  String s = title() + " (" + year() + ") : " + director();
	  return s;
  }
}
