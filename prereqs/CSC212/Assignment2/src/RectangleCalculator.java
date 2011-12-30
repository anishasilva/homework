
import javax.swing.*;	//for the graphics displays such as windows, buttons, etc
import java.awt.*;
import java.awt.event.*; //for the ActionListener class

public class RectangleCalculator {
    // Objects should be class-level

    private JLabel nameL, ageL, idolL;
    private JTextField nameTF, ageTF, idolTF;
    private JButton voteB, resetB, exitB;
    VoteButtonHandler vbHandler;
    ResetButtonHandler rbHandler;
    //CalculateButtonHandler cbHandler;
    ExitButtonHandler ebHandler;

    public void driver() {
        // Create the window
        JFrame rect = new JFrame("Favorite Idol");
        rect.setSize(700, 300);
        rect.setDefaultCloseOperation(rect.EXIT_ON_CLOSE);

        // Get the content pane (an object of type Container)
        Container cp = rect.getContentPane();
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


        rect.setVisible(true);

        // Instantiate listeners
        vbHandler = new VoteButtonHandler();
        rbHandler = new ResetButtonHandler();
        //cbHandler = new CalculateButtonHandler();
        ebHandler = new ExitButtonHandler();

        // Register the listeners with their respective components
        voteB.addActionListener(vbHandler);
        resetB.addActionListener(rbHandler);
        //calculateB.addActionListener(cbHandler);
        exitB.addActionListener(ebHandler);

    }

    // In order to run our program, we need to instantiate this class
    // and then invoke the driver() method
    public static void main(String[] args) {
        RectangleCalculator r = new RectangleCalculator();
        r.driver();
    }

    private class VoteButtonHandler
            implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.out.println(String.format("%-10s%s\n%-10s%s\n%-10s%s\n",
                    "Name:", nameTF.getText(),
                    "Age:", ageTF.getText(),
                    "Fave:", idolTF.getText()));
        }
    }

    private class ResetButtonHandler
            implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            nameTF.setText("");
            ageTF.setText("");
            idolTF.setText("");
        }
    }
    /*
    //This class implements the ActionListener interface
    //It is created to create a listener (aka "handler") for our calculateB button
    private class CalculateButtonHandler
    implements ActionListener {
    // Implementing an interface rquires writing a body for ALL of the methods in that interface.
    // In the case of ActionListener, there is only one method, so that is all we have to implement.
    // The actionPerformed() method will be automatically invoked whenever an event is generated
    // by the component.  (Of course, we have to remember to first register the listener with the component).

    public void actionPerformed(ActionEvent e) {
    double width, length, area, perimeter;
    length = Double.parseDouble(lengthTF.getText());
    width = Double.parseDouble(widthTF.getText());
    area = length * width;
    perimeter = (length + width) * 2;

    areaTF.setText("" + area); // the setText() method changes the display of a text field
    perimeterTF.setText("" + perimeter);
    } //end method actionPerformed
    } //end class CalculateButtonHandler*/

    //This class implements the ActionListener interface
    //It is created to create a listener (aka "handler") for our exitB button
    private class ExitButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        } //end method actionPerformed
    } //end class ExitButtonHandler
} //end class RectangleCalculator

