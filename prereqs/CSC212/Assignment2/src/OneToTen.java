
import java.io.*;
import javax.swing.*;

/**
 *
 * @author rayelward
 */
public class OneToTen {

    int[] array = new int[500];

    //Begins our program by instantiating the class ans then transcribing the file with the
    //loadFile method and then computing and prompting the user with the go method.
    public static void main(String[] args) {

        OneToTen oneToTen = new OneToTen();
        oneToTen.loadFile();
        oneToTen.go();
        System.exit(0);
    }

    public void go() {

        //asking the user for their number.
        int num = Integer.parseInt(JOptionPane.showInputDialog("Pick a number 1 - 10."));
        int count = 0;


        boolean valid = num > 0 && num < 11;

        //Tests if there was valid input.
        while (!valid) {
            JOptionPane.showMessageDialog(new JFrame(), "Only enter a number between 1 and 10. ");
            num = Integer.parseInt(JOptionPane.showInputDialog("Pick a new number between 1 - 10!!"));
            valid = num > 0 && num < 11;
        }
        //counts up the occurances of the users number in the file.
        for (int i = 0; i < array.length; i++) {
            if (array[i] == num) {
                count++;
            }
        }
        //displays the occurances for the user.
        JOptionPane.showMessageDialog(new JFrame(), "There are " + count + " instances of " + num + " in the file.");

    }

    public void loadFile()  {
        try {
            FileReader fileReader = new FileReader("oneToTen.txt");

            BufferedReader readBuffer = new BufferedReader(fileReader);

            String line = null;
            int count = 0;

            while ((line = readBuffer.readLine()) != null) {
                array[count++] = Integer.parseInt(line);
            }

            readBuffer.close();
            fileReader.close();

        } catch (Exception e) { }
    }
}
