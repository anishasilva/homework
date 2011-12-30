
import java.util.*;

// in ShoppingCart.java
public class ShoppingCart implements Group<Item> {
    // the items in the shopping cart, stored in both a Map and a Set.
    // In the Map, the Item objects are keyed by their ID's
    // (which are Strings).  In the Set, the items are sorted by
    // their name.

    private HashMap<String, Item> keyedItems = new HashMap<String, Item>();
    private TreeSet<Item> sortedItems = new TreeSet<Item>();

    // you'll need a default constructor
    public ShoppingCart() {
    }

    // you'll also need at least the methods that are required by the
    // Group<E> interface
    public void retrieveItem(Item e) {
        if (keyedItems.containsKey(e.getId())) {
            System.out.println(keyedItems.get(e.getId()));
        } else {
            System.out.println("\nItem can't be found in the cart.\n");
        }
    }

    public void addItem(Item e) {
        // call getItemFromId; if it doesn't
        // return null, display an error
        // message
        if (keyedItems.containsKey(e.getId()) && sortedItems.contains(e)) {
            System.out.println("Error: The item you are trying to add to the cart: " + e + ", is already in there!");
        } else {
            keyedItems.put(e.getId(), e);
            sortedItems.add(e);
        }


        // add to sortedItems, and
        // put to keyedItems
    }

    public void removeItem(Item item) {
        // call getItemFromId; if it does return
        // null, display error message
        if (keyedItems.containsKey(item.getId()) && sortedItems.contains(item)) {
            keyedItems.remove(item.getId());
            sortedItems.remove(item);
        } else {
            System.out.println("Error: the Item you are trying to remove: " + item + ", does not exist in the Shopping Cart!");
        }
        // remove from keyedItems,
        // remove from sortedItems
    }

    public Item getItemFromId(String id) {
        for (Item i : sortedItems) {
            if (id.equals(i.getId())) {
                return i;
            }
        }
        return null;
    }

    public String toString() {
        String s = "";
        for (Item i : sortedItems) {
            s += i;
        }

        return s;
        // ...
    }
}
