// CSC 383 Sections 401, 410 Fall 2010
// The LinkedList383 class, as presented during the 9/29/2010 lecture
import java.util.Iterator;

public class LinkedList383<E> {

    private LLNode<E> first, last;
    int size = 0;

    public LinkedList383() {
        first = new LLNode();
        last = new LLNode();
        first.setNext(last);
        last.setPrevious(first);
        size = 0;
    }

    private void insertAfter(LLNode<E> node, E item) {
        LLNode<E> newNode = new LLNode<E>(item);
        LLNode<E> next = node.getNext();
        node.setNext(newNode);
        newNode.setPrevious(node);
        next.setPrevious(newNode);
        newNode.setNext(next);
        size++;
    }

    private void removeNode(LLNode<E> node) {
        LLNode<E> p = node.getPrevious();
        LLNode<E> n = node.getNext();
        p.setNext(n);
        n.setPrevious(p);
        size--;
    }

    public String toString() {
        String answer = "[ ";
        LLNode<E> node = first.getNext();
        for (int i = 0; i < size; i++) {
            answer += node.getData() + " ";
            node = node.getNext();
        }
        return answer + "]";
    }

    public boolean contains(E item) {
        LLNode<E> node = first.getNext();
        for (int i = 0; i < size; i++) {
            if (node.getData().equals(item)) {
                return true;
            } else {
                node = node.getNext();
            }
        }
        return false;
    }

    public boolean add(E item) {
        insertAfter(last.getPrevious(), item);
        return true;
    }

    public void add(int index, E item) {
        LLNode<E> node = first;
        for (int i = 0; i < index; i++) {
            if (node == last) {
                return;
            }
            node = node.getNext();
        }
        insertAfter(node, item);
    }

    public boolean remove(E item) {
        LLNode<E> node = first.getNext();
        for (int i = 0; i < size; i++) {
            if (node.getData().equals(item)) {
                removeNode(node);
                return true;
            } else {
                node = node.getNext();
            }
        }
        return false;
    }

    public E remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        LLNode<E> node = first.getNext();
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        removeNode(node);
        return node.getData();
    }

    public E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        LLNode<E> node = first.getNext();
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        return node.getData();
    }

    public E set(int index, E item) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        LLNode<E> node = first.getNext();
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        E answer = node.getData();
        node.setData(item);
        return answer;
    }

    public Iterator<E> iterator() {
        // anonymous inner class
        return new Iterator() { // this means my anonymous inner class
            // implements Iterator

            private LLNode<E> node = first;

            public boolean hasNext() {
                return node.getNext() != last;
            }

            public E next() {
                node = node.getNext();
                return node.getData();
            }

            public void remove() {
                LLNode<E> p = node.getPrevious();
                LLNode<E> n = node.getNext();
                p.setNext(n);
                n.setPrevious(p);
                node = p;
                size--;
            }
        };
    }

    public boolean equals(LinkedList383<E> LLin) {
        if (this.size == LLin.size) {
            LLNode<E> node1 = this.first.getNext();
            LLNode<E> node2 = LLin.first.getNext();
            for (int index = 0; index < size; index++) {
                if (!node1.getData().equals(node2.getData())) {
                    return false;
                }
                node1 = node1.getNext();
                node2 = node2.getNext();
            }
            return true;
        }
        return false;
    }

    public int indexOf(E item) {
        LLNode<E> node = this.first.getNext();
        for (int index = 0; index < this.size; index++) {
            if (node.getData().equals(item)) {
                return index;
            }
            node = node.getNext();
        }
        return -1;
    }

    public LinkedList383<E> subList(int fromIndex, int toIndex) {
        if (fromIndex == toIndex) {
            return null;
        }

        LinkedList383<E> tempList = new LinkedList383<E>();

        LLNode<E> node = this.first.getNext();
        LLNode<E> tempNodePrev = new LLNode<E>();

        tempList.size = (toIndex - fromIndex);

        for (int index = 0; index < toIndex; index++) {
            //if (index >= fromIndex)
            //    tempList.add(this.get(index));
            LLNode<E> tempNode = new LLNode<E>(node.getData());

            if (index == fromIndex) {
                tempList.first.setNext(tempNode);
                tempNode.setPrevious(tempList.first);
            } else if (index > fromIndex && index < (toIndex - 1)) {
                tempNodePrev.setNext(tempNode);
                tempNode.setPrevious(tempNodePrev);
            } else if (index == toIndex - 1) {
                tempNode.setPrevious(tempNodePrev);
                tempNode.setNext(tempList.last);
                tempNodePrev.setNext(tempNode);
                tempList.last.setPrevious(tempNode);
            }
            tempNodePrev = tempNode;
            node = node.getNext();
        }

        return tempList;
    }
}
