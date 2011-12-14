// TODO: complete the methods
/**
 * Immutable Data Class for video objects.
 * Comprises a triple: title, year, director.
 *
 * @objecttype Immutable Data Class
 * @objectinvariant
 *   Title is non-null, no leading or final spaces, not empty string.
 * @objectinvariant
 *   Year is greater than 1800, less than 5000.
 * @objectinvariant
 *   Director is non-null, no leading or final spaces, not empty string.
 */
final class VideoObj implements Comparable<VideoObj> {
  /** @invariant non-null, no leading or final spaces, not empty string */
  private final String _title;
  /** @invariant greater than 1800, less than 5000 */
  private final int    _year;
  /** @invariant non-null, no leading or final spaces, not empty string */
  private final String _director;

  /**
   * Initialize all object attributes.
   * Title and director are "trimmed" to remove leading and final space.
   * @throws IllegalArgumentException if any object invariant is violated.
   */
  VideoObj(String title, int year, String director) {
    // TODO
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
    // TODO
    return _director;
  }

  /**
   * Return the value of the attribute.
   */
  public String title() {
    // TODO
    return _title;
  }

  /**
   * Return the value of the attribute.
   */
  public int year() {
    // TODO
    return _year;
  }

  /**
   * Compare the attributes of this object with those of thatObject.
   * @param thatObject the Object to be compared.
   * @return deep equality test between this and thatObject.
   */
  public boolean equals(Object thatObject) {
    // TODO
	  
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
  private int hcode;
  private final int HASH_MULTIPLIER = 37;
  public int hashCode() {
    // TODO
	  if (hcode == 0) {
		  hcode = 17;
		  hcode = HASH_MULTIPLIER * hcode + _year;
		  hcode = HASH_MULTIPLIER * hcode + _director.hashCode();
		  hcode = HASH_MULTIPLIER * hcode + _title.hashCode();
	  }
	  return hcode;
  }

  /**
   * Compares the attributes of this object with those of thatObject, in
   * the following order: title, year, director.
   * @param that the VideoObj to be compared.
   * @return a negative integer, zero, or a positive integer as this
   *  object is less than, equal to, or greater than that object.
   */
  public int compareTo(VideoObj that) {
    // TODO
	  int compare = _title.compareTo(that._title);

	  if ( compare != 0 )
		  return compare;
	  compare = _year - that._year;

	  if (compare != 0)
		  return compare;
	  compare = _director.compareTo(that._director);
	  return compare;
	  
  }

  /**
   * Return a string representation of the object in the following format:
   * <code>"title (year) : director"</code>.
   */
  public String toString() {
    // TODO
	  String s = title() + " (" + year() + ") : " + director();
    return s;
  }
}
