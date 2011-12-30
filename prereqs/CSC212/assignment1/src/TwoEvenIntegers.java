
/**
 *
 * @author rayelward
 */
import javax.swing.*;

public class TwoEvenIntegers {

    public static void main(String args[]) {

        JOptionPane.showMessageDialog(null, "You need to put in two positive even integers less than 100.");


        int numOne = Integer.parseInt(JOptionPane.showInputDialog("Enter first integer."));
        boolean valid = (numOne < 100 && numOne > 0 && numOne % 2 == 0);
        while (!valid) {
            numOne = Integer.parseInt(JOptionPane.showInputDialog("Error: Enter first integer again."));
            valid = (numOne < 100 && numOne > 0 && numOne % 2 == 0);
        }

        int numTwo = Integer.parseInt(JOptionPane.showInputDialog("Enter second integer."));
        valid = (numTwo < 100 && numTwo > 0 && numTwo % 2 == 0);
        while (!valid) {
            numTwo = Integer.parseInt(JOptionPane.showInputDialog("Error: Enter second integer again."));
            valid = (numTwo < 100 && numTwo > 0 && numTwo % 2 == 0);
        }


        for (double i = (double) numOne; i <= (double) numTwo; i += 0.5) {
            System.out.println(String.format("%.1f", i));
        }

        System.exit(0);
    }
}
