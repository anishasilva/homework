
import javax.swing.*;

/**
 *
 * @author rayelward
 */
public class Card {

    //fields!
    private int number;
    private String suit;

    //default constructor
    //post condition: sets the number to 0 and the set to a blank string
    public Card() {
        setNumber(0);
        setSuit("");
    }

    //constructor
    //lets user set the number and string
    public Card(int numIn, String suitIn) {
        setNumber(numIn);
        setSuit(suitIn);
    }

    //mutator methods
    public void setNumber(int numIn) {
        if (numIn < 2 || numIn > 14) {
            JOptionPane.showMessageDialog(new JFrame(), "Not a valid card number:" + numIn);
            System.exit(-1);
        } else {
            number = numIn;
        }
    }

    public void setSuit(String suitIn) {
        if (suitIn.equals("Clubs") || suitIn.equals("Hearts") || suitIn.equals("Diamonds") || suitIn.equals("Spades")) {
            suit = suitIn;
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Not a valid card suit:" + suitIn);
            System.exit(-1);
        }
    }

    //accessor methods
    public int getNumber() {
        return number;
    }

    public String getSuit() {
        return suit;
    }

    //equals method
    public boolean equals(Card cardIn) {
        return (cardIn.getNumber() == this.getNumber() && cardIn.getSuit().equals(this.getSuit()));
    }

    //overriding the parent toString. will return a String of which card is which.
    public String toString() {
        if (getNumber() == 11) {
            return String.format("%s of %s\n", "Jack", getSuit());
        } else if (getNumber() == 12) {
            return String.format("%s of %s\n", "Queen", getSuit());
        } else if (getNumber() == 13) {
            return String.format("%s of %s\n", "King", getSuit());
        } else if (getNumber() == 14) {
            return String.format("%s of %s\n", "Ace", getSuit());
        } else {
            return String.format("%d of %s\n", getNumber(), getSuit());
        }
    }
}
