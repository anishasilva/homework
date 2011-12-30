
import javax.swing.*;	//for the graphics displays such as windows, buttons, etc
import java.awt.*;
import java.awt.event.*; //for the ActionListener class

public class Idol {
    // Objects are class-level

    private JLabel nameL, ageL, idolL;
    private JTextField nameTF, ageTF, idolTF;
    private JButton voteB, resetB, exitB;
    VoteButtonHandler vbHandler;
    ResetButtonHandler rbHandler;
    ExitButtonHandler ebHandler;

    public void driver() {
        // Create the window
        JFrame idol = new JFrame("Favorite Idol");
        idol.setSize(600, 200);
        idol.setDefaultCloseOperation(idol.EXIT_ON_CLOSE);

        // Get the content pane (an object of type Container)
        Container cp = idol.getContentPane();
        cp.setLayout(new GridLayout(3, 3));

        nameL = new JLabel("Name:", SwingConstants.LEFT);
        ageL = new JLabel("Age:", SwingConstants.LEFT);
        idolL = new JLabel("Favorite Idol:", SwingConstants.LEFT);

        nameTF = new JTextField(10);
        ageTF = new JTextField(10);
        idolTF = new JTextField(10);

        voteB = new JButton("Vote For Favorite");
        resetB = new JButton("Reset Form");
        exitB = new JButton("Exit");

        // Add our components to the content pane container
        cp.add(nameL);
        cp.add(ageL);
        cp.add(idolL);

        cp.add(nameTF);
        cp.add(ageTF);
        cp.add(idolTF);

        cp.add(voteB);
        cp.add(resetB);
        cp.add(exitB);


        idol.setVisible(true);


        // Register the listeners with their respective components
        voteB.addActionListener(new VoteButtonHandler());
        resetB.addActionListener(new ResetButtonHandler());
        exitB.addActionListener(new ExitButtonHandler());

    }

    // In order to run our program, we need to instantiate this class
    // and then invoke the driver() method
    public static void main(String[] args) {
        Idol r = new Idol();
        r.driver();
    }

    //This class implements the ActionListener interface
    //It handles the event to submit the vote.
    private class VoteButtonHandler
            implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.out.println(String.format("%-10s%s\n%-10s%s\n%-10s%s\n",
                    "Name:", nameTF.getText(),
                    "Age:", ageTF.getText(),
                    "Fave:", idolTF.getText()));
        }
    }//end class VoteButtonHandler

    //This class implements the ActionListener interface
    //It handles the event to reset the form.
    private class ResetButtonHandler
            implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            nameTF.setText("");
            ageTF.setText("");
            idolTF.setText("");
        }
    }//end class ResetButtonHandler

    //This class implements the ActionListener interface
    //It is created to create a listener (aka "handler") for our exitB button
    private class ExitButtonHandler
            implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        } //end method actionPerformed
    } //end class ExitButtonHandler
} //end class Idol

