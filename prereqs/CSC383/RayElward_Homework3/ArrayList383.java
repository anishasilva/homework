// CSC 383 Sections 401, 410 Fall 2010
// The ArrayList class, as written during the 9/22/2010 lecture

public class ArrayList383<E> {

    private int DEFAULT_SIZE = 10;
    private E[] data;
    private int size;

    public ArrayList383() {
        data = (E[]) new Object[DEFAULT_SIZE]; // new E[DEFAULT_SIZE]
        size = 0;
    }

    public ArrayList383(int s) {
        data = (E[]) new Object[s]; // new E[DEFAULT_SIZE]
        size = 0;
    }

    // add to the end of the list
    public boolean add(E item) {
        if (size == data.length) // ???
        {
            expand();  // replace the array with a bigger one
        }
        data[size] = item;
        size++;
        return true;
    }

    // add in a particular position
    public void add(int pos, E item) {
        if (size == data.length) {
            expand();  // replace the array with a bigger one
        }
        // make room in the array for the new item
        for (int i = size - 1; i >= pos; i--) {
            data[i + 1] = data[i];
        }
        data[pos] = item;
        size++;
    }

    // overwrite the data that is in position pos
    public E set(int pos, E item) {
        E oldItem = data[pos];
        data[pos] = item;
        return oldItem;
    }

    public boolean remove(E item) {
        // find item in the array
        int index = 0;
        for (; index < size; index++) {
            if (data[index].equals(item)) {
                break;
            }
        }
        if (index == size) {
            return false;
        }
        // then remove it
        // index is the position of the item to be remove
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        return true;
    }

    public E remove(int index) {
        E item = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        return item;
    }

    public boolean contains(E item) {
        for (int index = 0; index < size; index++) {
            if (data[index].equals(item)) {
                return true;
            }
        }
        return false;
    }

    public E get(int pos) {
        return data[pos];
    }

    // called when the array is full
    private void expand() {
        E[] newData = (E[]) new Object[data.length * 2];
        // copy item from the old array to the new array
        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    //Added toString method for usefulness and my own testing needs.
    //Kinda imitates the one written in LinkedList383

    public String toString(){
        String s = "[ ";
        for (int x = 0; x < this.size; x++){
            s += this.get(x) + " ";
        }
        s += "]";
        return s;
    }
    //homework 3 defined methods!

    public boolean equals(ArrayList383<E> ALin) {
        if (this.size == ALin.size) {
            for (int index = 0; index < this.size; index++) {
                if (!this.data[index].equals(ALin.data[index])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public int indexOf(E item) {
        for (int index = 0; index < size; index++) {
            if (data[index].equals(item)) {
                return index;
            }
        }
        return -1;
    }

    public ArrayList383<E> subList(int fromIndex, int toIndex) {
        if (fromIndex == toIndex)
            return null;
        ArrayList383<E> tempList = new ArrayList383<E>();

        tempList.size = (toIndex - fromIndex);
        for (int index = fromIndex; index < toIndex; index++) {
            //get the item.
            //set the item into the new list.

            //tempList.add(this.get(index));
            tempList.data[index - fromIndex] = this.data[index];
        }
        return tempList;
    }
}




































































































