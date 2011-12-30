
import java.util.Scanner;
// HW1.java
// I'll leave this one for you to write.

public class HW1 {

    ShoppingCart Items = new ShoppingCart();
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        HW1 go = new HW1();
        go.driver();

        // then code to interact with the user, in which the user first enters which
        // action is to be performed, then additional information such as the ID of
        // an Item, or the Item's name, ID, and price
        System.exit(0);
    }

    public void driver() {
        int input = 0;
        do {
            System.out.println("Commands for your shopping cart:\n"
                    + "1-Add Item.\n"
                    + "2-Delete an Item.\n"
                    + "3-Retrieve an Item.\n"
                    + "4-Print all cart Items. \n"
                    + "5-Exit");
            boolean valid = false;
            while (!valid) {
                try {
                    input = Integer.parseInt(sc.next());
                    valid = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid command.  Please reenter:");
                }
            }
            switch (input) {
                case 1:
                    addItem();
                    break;
                case 2:
                    removeItem();
                    break;
                case 3:
                    retrieveItem();
                    break;
                case 4:
                    print();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid command.  Please retry.");

            }

        } while (input != 5);
        System.out.println("\n\nGOODBYE! Have a good DAY!\n\n");
    }

    public void addItem() {
        double price = 0;
        int quantity = 0;
        System.out.println("Enter Item name:");
        String name = sc.next();

        System.out.println("Enter Item ID:");
        String id = sc.next();

        boolean valid = false;

        while (!valid) {
            System.out.println("Enter Item price:");
            try {
                price = Double.parseDouble(sc.next());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Not a valid Price number!\nPLEASE REENTER");
            }
        }

        valid = false;
        while (!valid) {
            System.out.println("Enter Item quantity:");
            try {
                quantity = Integer.parseInt(sc.next());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Not a valid quantity number!\nPLEASE REENTER");
            }
        }

        System.out.println("\n");
        Item temp = new Item(name, id, price, quantity);
        Items.addItem(temp);
    }

    public void removeItem() {

        System.out.println("\nEnter Item ID you want to remove:");
        String id = sc.next();
        if (Items.getItemFromId(id) != null) {
            Items.removeItem(Items.getItemFromId(id));
            System.out.println("\nITEM " + id + " REMOVED\n");
        } else {
            System.out.println("\nERROR: Item not found in the list.  Can't be removed\n");
        }
    }

    public void retrieveItem() {
        System.out.println("\nEnter Item ID you want to retrieve:");
        String id = sc.next();
        if (Items.getItemFromId(id) != null) {
            Items.retrieveItem(Items.getItemFromId(id));
        } else {
            System.out.println("\nERROR: Item not found in the list.  Can't be retrieved."
                    + "\n");
        }
    }

    public void print() {
        System.out.println(Items);
    }
}

