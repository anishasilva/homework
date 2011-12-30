
import javax.swing.*;	//for the graphics displays such as windows, buttons, etc
import java.awt.*;
import java.awt.event.*;

public class AddressBook {

    private JLabel nameL, numberL;
    private JTextField nameTF, numberTF;
    private JButton submitB, exitB;
    SubmitButtonHandler vbHandler;
    ExitButtonHandler ebHandler;

    public void driver(){

        JFrame address = new JFrame("Address Book");
        address.setSize(400, 300);
        address.setDefaultCloseOperation(address.EXIT_ON_CLOSE);

        Container cp = address.getContentPane();
        cp.setLayout(new GridLayout(3,3));

        nameL = new JLabel("Name:", SwingConstants.LEFT);
        numberL = new JLabel("Phone #:", SwingConstants.LEFT);

        nameTF = new JTextField(10);
        numberTF = new JTextField(10);

        submitB = new JButton("Submit");
        exitB = new JButton("Exit");

        cp.add(nameL);
        cp.add(numberL);

        cp.add(nameTF);
        cp.add(numberTF);

        cp.add(submitB);
        cp.add(exitB);

        address.setVisible(true);

        submitB.addActionListener(new SubmitButtonHandler());
        exitB.addActionListener(new ExitButtonHandler());
    }

    public static void main(String[] args) {
        AddressBook r = new AddressBook();
        r.driver();
    }




    private class ExitButtonHandler
            implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        } //end method actionPerformed
    }


    private class SubmitButtonHandler
            implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //submit stuff
        } //end method actionPerformed
    }
}
