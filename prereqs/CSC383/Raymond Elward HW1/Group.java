// the Group interface has a template variable to specify what type of
// data is stored in the Group.
public interface Group<E> {
  public void addItem(E item);
  public void removeItem(E item);
  public void retrieveItem(E item);
  public String toString(); // used in printing out the items in the group
}