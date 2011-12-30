
import java.util.*;

class TreeNode383<E extends Comparable> {

    private E data;
    private TreeNode383<E> left;
    private TreeNode383<E> right;
    private TreeNode383<E> parent;

    public TreeNode383() {
        left = right = parent = null;
    }

    public TreeNode383(E d, TreeNode383<E> l, TreeNode383<E> r,
            TreeNode383<E> p) {
        data = d;
        left = l;
        right = r;
        parent = p;
    }

    public E getData() {
        return data;
    }

    public void setData(E d) {
        data = d;
    }

    public TreeNode383<E> getLeft() {
        return left;
    }

    public void setLeft(TreeNode383<E> l) {
        left = l;
    }

    public TreeNode383<E> getRight() {
        return right;
    }

    public void setRight(TreeNode383<E> r) {
        right = r;
    }

    public TreeNode383<E> getParent() {
        return parent;
    }

    public void setParent(TreeNode383<E> p) {
        parent = p;
    }

    public String toString() {
        String answer = "";
        if (left != null) {
            answer += left.toString();
        }
        answer += data + " ";
        if (right != null) {
            answer += right.toString();
        }
        return answer;
    }
}

public class TreeSet383<E extends Comparable> {

    private TreeNode383<E> root = null;
    private int size;

    public TreeSet383() {
        size = 0;
    }

    public boolean add(E newData) {
        if (root == null) {
            root = new TreeNode383<E>(newData, null, null, null);
            size++;
            return true;
        } else {
            TreeNode383<E> current = root;
            while (true) {
                // compare data in current with newData
                int comp = current.getData().compareTo(newData);
                if (comp > 0) { // insert into left subtree
                    TreeNode383<E> next = current.getLeft();
                    if (next == null) {
                        current.setLeft(new TreeNode383<E>(newData, null, null, current));
                        size++;
                        return true;
                    } else {
                        current = next;
                    }
                } else if (comp < 0) { // insert into right subtree
                    TreeNode383<E> next = current.getRight();
                    if (next == null) {
                        current.setRight(new TreeNode383<E>(newData, null, null, current));
                        size++;
                        return true;
                    } else {
                        current = next;
                    }
                } else {
                    return false; // data already in tree
                }
            }
        }
    }

    public boolean remove(E item) {
        // find item to remove
        TreeNode383<E> current = root;
        // code from add can help you write this
        //returns false if treeset is empty.
        if (root == null) {
            return false;
        }
        //find node we're removing.  look at add method.
        while (true) {
            int comp = current.getData().compareTo(item);
            if (comp > 0) {
                if (current.getLeft() == null) {
                    return false;
                }
                TreeNode383<E> next = current.getLeft();
                current = next;
            } else if (comp < 0) {
                if (current.getRight() == null) {
                    return false;
                }
                TreeNode383<E> next = current.getRight();
                current = next;
            } else if (comp == 0) {
                break;
            }
        }

        //sets tree to null if trying to delete the last node in a set.
        if (size == 1 && current == root) {
            root = null;
            size--;
            return true;
        }
        //find the parent of the current node.
        TreeNode383<E> parent = current.getParent();

        // if current is a leaf, simply remove it
        if (current.getLeft() == null && current.getRight() == null) {
            if (parent.getRight() == current) // could be .equals but == is better
            {
                parent.setRight(null);
            } else {
                parent.setLeft(null);
            }
            size--;
            return true;
        } // if current has 1 child, replace it with its child
        //this takes care of the case if its the root with only 1 child. and its a right child
        else if (((current.getLeft() == null && current.getRight() != null)) && current == root) {
            TreeNode383<E> child = current.getRight();
            child.setParent(null);
            root = child;
            size--;
            return true;
            //this takes care of the case if its the root with only 1 child. and its a left child.
        } else if ((current.getLeft() != null && current.getRight() == null) && current == root) {
            TreeNode383<E> child = current.getLeft();
            child.setParent(null);
            root = child;
            size--;
            return true;
        } //checks to see if left child
        else if ((current.getLeft() != null && current.getRight() == null)) {
            //makes a right child var
            TreeNode383<E> child = current.getLeft();
            //need to check what kind of child the original was
            if (current == parent.getRight()) {
                child.setParent(parent);
                //links current parent to child
                parent.setRight(child);
            } else {
                child.setParent(parent);
                //links current parent to child
                parent.setLeft(child);
            }
            size--;
            return true;
        } //checks to see if right child.
        else if ((current.getLeft() == null && current.getRight() != null)) {
            //makes a left child var
            TreeNode383<E> child = current.getRight();
            //links child to current's parent
            //need to check what kind of child the original was
            if (current == parent.getRight()) {
                child.setParent(parent);
                //links current parent to child
                parent.setRight(child);
            } else {
                child.setParent(parent);
                //links current parent to child
                parent.setLeft(child);
            }
            size--;
            return true;
        } // if current has 2 children, do as described in notes and in lecture
        // find leftmost node of right subtree of node to be deleted
        // promote this node to replace deleted node
        // then promote that node's child to replace it
        //else if current has a left child, and current has a right child.
        else if (current.getLeft() != null && current.getRight() != null) {

            //declaring the nodes i'll need to switch the pointers around on.
            TreeNode383<E> rightChild = current.getRight();
            TreeNode383<E> leftChild = current.getLeft();
            TreeNode383<E> replace = leftMost(rightChild);
            TreeNode383<E> replaceParent = replace.getParent();
            TreeNode383<E> replaceChild = replace.getRight();




            //takes care of the case if we are removing the root
            //don't want any null pointer exceptions when trying to get the parent of replace.
            if (current == root) {
                root = replace;
            } else {
                //sets the pointers between the new node and it's parent
                replace.setParent(parent);
                if (current == parent.getRight()) {
                    parent.setRight(replace);
                } else if (current == parent.getLeft()) {
                    parent.setLeft(replace);
                }
            }

            //points the new node to the old node's left child.
            replace.setLeft(leftChild);
            leftChild.setParent(replace);

            //points the new node to the old node's right child
            if (rightChild != replace) {
                replace.setRight(rightChild);
                rightChild.setParent(replace);
                replaceParent.setLeft(null);
                //if replace had children we need to promote them to replace's old position.
                if (replaceChild != null) {
                    replaceChild.setParent(replaceParent);
                    replaceParent.setLeft(replaceChild);
                }
            }


            size--;
            return true;



        }
        return false;

    }

    private TreeNode383<E> leftMost(TreeNode383<E> start) {
        TreeNode383<E> left = start;
        while (true) {
            if (left.getLeft() == null) {
                return left;
            } else {
                left = left.getLeft();
            }
        }
    }

    private TreeNode383<E> rightMost(TreeNode383<E> start) {
        TreeNode383<E> right = start;
        while (true) {
            if (right.getRight() == null) {
                return right;
            } else {
                right = right.getRight();
            }
        }
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {

            //current initialized as having leftmost as its right child.
            //this way the first call to next() will iterate to the leftmost of the root.
            private TreeNode383<E> current = new TreeNode383<E>(null, null, leftMost(root), null); // you should write leftmost
            private TreeNode383<E> last = new TreeNode383<E>(null, null, null, rightMost(root));
            private TreeNode383<E> rightM = rightMost(root);

            //return
            public boolean hasNext() {
                return current != rightM;
            }

            public E next() {
                //checking if its the last node in the set so we can end correctly.
                rightM.setRight(last);
                if (current == last) {
                    current = null;
                    rightM.setRight(null);
                    return null;
                }
                TreeNode383<E> parent = current.getParent();

                //if the current node has a right child.
                if (current.getRight() != null) {
                    // then go to leftmost descendent of rights child
                    TreeNode383<E> leftMost = leftMost(current.getRight());
                    current = leftMost;
                } //else if current has no right child, and is a left child
                //  then go to current.getParent( )
                else if (current.getRight() == null && parent.getLeft() == current) {
                    current = parent;
                } //else go to the first ancestor whose data is > current.getData( )
                else {

                    TreeNode383<E> ancestor = parent;
                    while (true) {
                        //get the parent of current

                        int comp = ancestor.getData().compareTo(current.getData());
                        //if parent is greaterthan current return
                        if (comp > 0) {
                            current = ancestor;
                            break;
                        } //if parent is less than current make the parent current
                        else if (comp < 0) {
                            current = ancestor;
                        }
                        ancestor = current.getParent();
                    }
                }
                //sets the data to be returned.
                E data;
                if (current != null) {
                    data = current.getData();
                } else {
                    data = null;
                }
                //gets rid of the pointer we had linking a last node
                rightM.setRight(null);
                return data;

            }

            public void remove() {
                //NOT NEEDED FOR THE ASSIGNMENT!
            }
        };
    }

    public String toString() {
        if (root == null) {
            return "[ ]";
        }
        return "[ " + root.toString() + "]";
    }

    public static void main(String[] args) {
        TreeSet383<Integer> tree = new TreeSet383<Integer>();
        //test add and tree node / treeset.
        for (int i = 0; i < 20; i++) {
            int x = (int) (Math.random() * 100);
            tree.add(x);
            System.out.print(x + " ");
        }

        //test iterator.
        System.out.println("\n" + "Start the iterator test: \n" + tree);
        for (Iterator<Integer> i = tree.iterator(); i.hasNext();) {
            System.out.print(i.next() + " ");
        }
        System.out.println("\n" + tree);


        //tests remove with leaf, one child and TWO child positions!
        System.out.println("\n" + "Start the remove test:\n");

        System.out.println("\n" + tree);
        Scanner sc = new Scanner(System.in);
        int count;
        while (tree.root != null) {
            count = -2;
            System.out.println("enter a number to be removed(-1 to quit):");
            count = Integer.parseInt(sc.next());
            if (count == -1) {
                System.exit(count);
            }
            tree.remove(count);
            System.out.println("\n" + tree);
        }


    }
}
