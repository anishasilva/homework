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
   * @throws IllegalArgumentException if any object invariant is violated.
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
  /**
   * Return the value of the attribute.
   */
  public String director() {
    return _director;
  }
  /**
   * Return the value of the attribute.
   */
  public String title() {
    return _title;
  }
  /**
   * Return the value of the attribute.
   */
  public int year() {
    return _year;
  }
  /**
   * Compare the attributes of this object with those of thatObject.
   * @param thatObject the Object to be compared.
   * @return deep equality test between this and thatObject.
   */
  public boolean equals(Object thatObject) {
	  if ( !(thatObject instanceof VideoObj) )
		  return false;
	  
	  VideoObj that = (VideoObj) thatObject;
	  
	  return ( this.year() == that.year() 
			  && this.director().equals(that.director() ) 
			  && this.title().equals( that.title() ) );
  }
  /**
   * Return a hash code value for this object using the algorithm from Bloch:
   * fields are added in the following order: title, year, director.
   */
  private final int HASH_MULTIPLIER = 37;
  public int hashCode() {
	  int result = 17;
	  result = HASH_MULTIPLIER*result + _title.hashCode();
	  result = HASH_MULTIPLIER*result + _year;
	  result = HASH_MULTIPLIER*result + _director.hashCode();

	  return result;
  }
  /**
   * Compares the attributes of this object with those of thatObject, in
   * the following order: title, year, director.
   * @param that the VideoObj to be compared.
   * @return a negative integer, zero, or a positive integer as this
   *  object is less than, equal to, or greater than that object.
   */
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
  /**
   * Return a string representation of the object in the following format:
   * <code>"title (year) : director"</code>.
   */
  public String toString() {
	  String s = title() + " (" + year() + ") : " + director();
	  return s;
  }
}
