
/**
 *
 * @author rayelward
 */
public class Item implements Comparable<Item> {

    private String name;
    private String id;
    private double price;
    private int quantity;
/** default constructor
 * Postcondition: sets name and id to null, sets price and quantity to -1.
 */
    public Item(){
        setName(null);
        setId(null);
        setPrice(-1);
        setQuantity(-1);
    }

    public Item(String name, String id, double price, int quantity) {
        setName(name);
        setId(id);
        setPrice(price);
        setQuantity(quantity);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int compareTo(Item i) {
        return this.getName().compareToIgnoreCase(i.getName());
        // enables the TreeSet<Item> class to compare
        // Items to place them in their "natural" ordering
        // within the Set.  Note that we want to alphabetize
        // by item name
    }

    public String toString() {
        return String.format("%-10s%s\n%-10s%s\n%-10s%.2f\n%-10s%d\n\n",
                "Name:", getName(),
                "ID:", getId(),
                "Price:", getPrice(),
                "Quantity:", getQuantity());
    }
}
