// CSC 383 Sections 401, 410 Fall 2010
// The LLNode class -- used in LinkedList383

public class LLNode<E> {
  private LLNode<E> previous, next;
  private E data;

  public LLNode( ) { }

  public LLNode(E item) { data = item; }

  public E getData( ) { return data; }

  public void setData(E item) { data = item; }

  public LLNode<E> getNext( ) { return next; }

  public LLNode<E> getPrevious( ) { return previous; }

  public void setNext(LLNode<E> n) { next = n; }

  public void setPrevious(LLNode<E> p) { previous = p; }
}

