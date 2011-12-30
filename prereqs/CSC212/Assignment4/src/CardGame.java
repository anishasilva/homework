
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author rayelward
 */
public class CardGame {

    //private fields
    private JLabel yourCardsL;
    private JTextField[] cardTF;
    private JButton dealB, exitB;
    private Card[][] deck;
    private Card[] hand;

    //main to kick off the Gui and start in the driver method.
    public static void main(String[] args) {
        CardGame game = new CardGame();
        game.driver();
    }//end main

    public void driver() {
        //call to method to instantiate the deck
        createDeck();
        //sets up the Gui for the user.
        setupGui();

    }//end driver method

    public void setupGui() {
        //setting up our Frame
        JFrame frame = new JFrame("Card Hand");
        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setting up the content pane.
        Container cp = frame.getContentPane();
        cp.setLayout(new GridLayout(6, 2));

        //setting up our label
        yourCardsL = new JLabel("Press Deal", SwingConstants.RIGHT);

        //setting up our buttons
        dealB = new JButton("Deal!");
        exitB = new JButton("Exit");

        //Setting up our textfields
        cardTF = new JTextField[5];
        for (int i = 0; i < cardTF.length; i++) {
            cardTF[i] = new JTextField(10);
            cardTF[i].setEditable(false);
        }


        //adding elements to the contentpane
        cp.add(yourCardsL);
        cp.add(cardTF[0]);
        cp.add(new JLabel("", SwingConstants.CENTER));
        cp.add(cardTF[1]);
        cp.add(new JLabel("", SwingConstants.CENTER));
        cp.add(cardTF[2]);
        cp.add(new JLabel("", SwingConstants.CENTER));
        cp.add(cardTF[3]);
        cp.add(new JLabel("", SwingConstants.CENTER));
        cp.add(cardTF[4]);
        cp.add(dealB);
        cp.add(exitB);

        //setting the frame to be visible
        frame.setVisible(true);

        //adding the listeners to the buttons
        dealB.addActionListener(new DealButtonHandler());
        exitB.addActionListener(new ExitButtonHandler());


    }//end setupGui Method

    public void createDeck() {
        //instantiating our arrays of hands.
        deck = new Card[4][13];
        hand = new Card[5];
        //loop to instantiate the units in the deck array
        for (int i = 0; i < deck.length; i++) {
            for (int j = 0; j < deck[i].length; j++) {
                switch (i) {
                    case 0:
                        deck[i][j] = new Card(j + 2, "Hearts");
                        break;
                    case 1:
                        deck[i][j] = new Card(j + 2, "Diamonds");
                        break;
                    case 2:
                        deck[i][j] = new Card(j + 2, "Spades");
                        break;
                    case 3:
                        deck[i][j] = new Card(j + 2, "Clubs");
                        break;
                }
            }
        }
    }//end createDeck Method

    public void dealHand() {
        for (int x = 0; x < hand.length; x++) {
            int suit = (int) (Math.random() * 4);
            int number = (int) (Math.random() * 13);


            //nested for loop that checks if the card has already been delt to the user.
            //if the card has already been delt it is a misdeal and we redeal the card.
            for (int i = 0; i < x; i++) {
                boolean valid = !hand[i].equals(deck[suit][number]);
                //if the card is already delt we keep giving random numbers unil we find a valid card for
                //that one in the hand.
                while (!valid) {
                    suit = (int) (Math.random() * 4);
                    number = (int) (Math.random() * 13);
                    valid = !hand[i].equals(deck[suit][number]);
                }
            }
            //assign the randomized card out of the deck to the hand.
            hand[x] = deck[suit][number];
        }
    }//end dealHand method

    public class DealButtonHandler
            implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            dealHand();

            for (int i = 0; i < cardTF.length; i++) {
                cardTF[i].setText(hand[i].toString());
            }

            yourCardsL.setText("Your Cards:");
        }// end actionPerformed method
    }//end DealButtonHandler sub class

    public class ExitButtonHandler
            implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            JOptionPane.showMessageDialog(new JFrame(), "Goodbye. Have a good day!");
            System.exit(0);
        }// end actionPerformed method
    }//end DealButtonHandler sub class
}//end card game class

