import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;

/**
 * An Inventory implemented using a <code>HashMap&lt;Video,Record&gt;</code>.
 * Keys are Videos; Values are Records.
 *
 * @objecttype Mutable Collection of Records
 * @objectinvariant
 *   Every key and value in the map is non-<code>null</code>.
 * @objectinvariant
 *   Each value <code>r</code> is stored under key <code>r.video</code>.
 */
final class InventorySet {
  /** @invariant <code>_data != null</code> */
  private final Map<VideoObj,Record> _data = new HashMap<VideoObj,Record>();

  InventorySet() { }

  /**
   * Return the number of Records.
   */
  public int size() {
    return _data.size();
  }

  /**
   *  Return a copy of the record for a given Video; if not present, return <code>null</code>.
   */
  public Record get(VideoObj v) {
	  
	  Record r = _data.get(v);
	  if (r == null)
		  return null;
	  
	  return r.copy();
  }

  /**
   * Return a copy of the records as a collection.
   * Neither the underlying collection, nor the actual records are returned.
   */
  public Collection<Record> toCollection() {
    // Recall that an ArrayList is a Collection.
	  Collection<Record> arrRecord = new ArrayList<Record>();
	  
	  Collection<Record> dataCol = _data.values();
	  
	  arrRecord.addAll(dataCol);
	  
	  return arrRecord;
  }

  /**
   * Add or remove copies of a video from the inventory.
   * If a video record is not already present (and change is
   * positive), a record is created. 
   * If a record is already present, <code>numOwned</code> is
   * modified using <code>change</code>.
   * If <code>change</code> brings the number of copies to be zero,
   * the record is removed from the inventory.
   * @param video the video to be added.
   * @param change the number of copies to add (or remove if negative).
   * @throws IllegalArgumentException if video null, change is zero,
   *  if attempting to remove more copies than are owned, or if
   *  attempting to remove copies that are checked out.
   * @postcondition changes the record for the video
   */
  public void addNumOwned(VideoObj video, int change) {
	  if ( video == null || change == 0)
		  throw new IllegalArgumentException();
	  if ( !_data.containsKey(video) && change > 0){
		  Record r = new Record(video, change, 0, 0);
		  _data.put(video, r);
	  }
	  else if ( _data.containsKey(video) ){
		  Record r = _data.get(video);
		  
		  
		  if ( (r.numOwned + change) == 0){
			  //throws exception if attempting to remove copies that are out.
			  if( r.numOut > 0 )
				  throw new IllegalArgumentException();
			  _data.remove(video);  
		  }
		//throws exception if attempting to remove copies that are out or more than owned.
		  else if ( (r.numOwned + change) < r.numOut )
			  throw new IllegalArgumentException();
		  r.numOwned += change;
	  }
  }

  /**
   * Check out a video.
   * @param video the video to be checked out.
   * @throws IllegalArgumentException if video has no record or numOut
   * equals numOwned.
   * @postcondition changes the record for the video
   */
  public void checkOut(VideoObj video) {
	  if ( !_data.containsKey(video) )
		  throw new IllegalArgumentException();
	  Record r = _data.get(video);
	  if (r == null || (r.numOut == r.numOwned) )
		  throw new IllegalArgumentException();
	  r.numRentals++;
	  r.numOut++;
	  
  }
  
  /**
   * Check in a video.
   * @param video the video to be checked in.
   * @throws IllegalArgumentException if video has no record or numOut
   * non-positive.
   * @postcondition changes the record for the video
   */
  public void checkIn(VideoObj video) {
	  if ( !_data.containsKey(video) )
		  throw new IllegalArgumentException();
	  Record r = _data.get(video);
	  if (r.numOut < 0)
		  throw new IllegalArgumentException();
	  r.numOut--;
  }
  
  /**
   * Remove all records from the inventory.
   * @postcondition <code>size() == 0</code>
   */
  public void clear() {
	  _data.clear();
  }

  /**
   * Return the contents of the inventory as a string.
   */
  public String toString() {
    StringBuilder buffer = new StringBuilder();
    buffer.append("Database:\n");
    for (Record r : _data.values()) {
      buffer.append("  ");
      buffer.append(r);
      buffer.append("\n");
    }
    return buffer.toString();
  }
}
