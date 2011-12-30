
/**
 *
 * @author rayelward
 */
import javax.swing.*;

public class FiveDecimalNumbers {

    public static void main(String args[]) {
        boolean valid = false;
        int count = 1;
        double[] array = new double[5];

        JOptionPane.showMessageDialog(null, "You need to put in 5 positive decimal numbers.");

        while (!valid) {
            String st = null;
            switch (count) {
                case 1:
                    st = "st";
                    break;
                case 2:
                    st = "nd";
                    break;
                case 3:
                    st = "rd";
                    break;
                default:
                    st = "th";
            }// end switch
            double input = Double.parseDouble(JOptionPane.showInputDialog("Enter the " + count + st + " number:"));
            if (input > 0) {
                array[count++ - 1] = input;
            }
            if (count > array.length) {
                valid = true;
            }
        }// end while loop.

        double largest = array[0], smallest = array[0], average, total = 0;

        for (int i = 0; i < array.length; i++) {
            if (largest < array[i]) {
                largest = array[i];
            }
            if (smallest > array[i]) {
                smallest = array[i];
            }
            total += array[i];
        }//end for loop.

        average = (total / array.length);

        JOptionPane.showMessageDialog(null, String.format("%s%.2f\n%s%.2f\n%s%.2f\n",
                "Largest:", largest,
                "Smallest:", smallest,
                "Average:", average));

        System.exit(0);
    }
}
