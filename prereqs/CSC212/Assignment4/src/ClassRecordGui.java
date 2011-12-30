
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author rayelward
 */
public class ClassRecordGui {

    //Class level fields for GUI and file writer.
    private JLabel firstNameL, lastNameL, idNumL, totalHomeworkGradeL, midtermGradeL, finalGradeL, letterGradeL;
    private JTextField firstNameTF, lastNameTF, idNumTF, totalHomeworkGradeTF, midtermGradeTF, finalGradeTF, letterGradeTF;
    private JButton submitB, exitB;
    private BufferedWriter writeBuffer;
    private ArrayList<ClassRecord> arrStudentList;

    public void driver() {
        //creating the window
        JFrame frame = new JFrame("Student Entry");
        frame.setSize(450, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //instantiating the file writer and buffer
        try {
            writeBuffer = new BufferedWriter(new FileWriter("StudentList.txt"));
        } catch (IOException e) {
        }

        //Adding the content pane
        Container cp = frame.getContentPane();
        cp.setLayout(new GridLayout(8, 2));

        //instantiating the labels
        firstNameL = new JLabel("First Name:", SwingConstants.RIGHT);
        lastNameL = new JLabel("Last Name:", SwingConstants.RIGHT);
        idNumL = new JLabel("ID #:", SwingConstants.RIGHT);
        totalHomeworkGradeL = new JLabel("Total Homework Grade:", SwingConstants.RIGHT);
        midtermGradeL = new JLabel("Midterm Grade:", SwingConstants.RIGHT);
        finalGradeL = new JLabel("Final Grade:", SwingConstants.RIGHT);
        letterGradeL = new JLabel("Letter Grade:", SwingConstants.RIGHT);

        //creating the textfields
        firstNameTF = new JTextField(10);
        lastNameTF = new JTextField(10);
        idNumTF = new JTextField(10);
        totalHomeworkGradeTF = new JTextField(10);
        midtermGradeTF = new JTextField(10);
        finalGradeTF = new JTextField(10);
        letterGradeTF = new JTextField(10);

        //creating the buttons
        submitB = new JButton("Submit Entry");
        exitB = new JButton("Exit");

        //instantiate our list!
        arrStudentList = new ArrayList<ClassRecord>();


        //adding the elements to the content pane
        cp.add(firstNameL);
        cp.add(firstNameTF);
        cp.add(lastNameL);
        cp.add(lastNameTF);
        cp.add(idNumL);
        cp.add(idNumTF);
        cp.add(totalHomeworkGradeL);
        cp.add(totalHomeworkGradeTF);
        cp.add(midtermGradeL);
        cp.add(midtermGradeTF);
        cp.add(finalGradeL);
        cp.add(finalGradeTF);
        cp.add(letterGradeL);
        cp.add(letterGradeTF);
        cp.add(submitB);
        cp.add(exitB);



        //make frame visible
        frame.setVisible(true);

        //adding the listeners to the buttons
        submitB.addActionListener(new SubmitButtonHandler());
        exitB.addActionListener(new ExitButtonHandler());

    }

    //main to instantiate the class and let it go to driver.
    public static void main(String[] args) {
        ClassRecordGui go = new ClassRecordGui();
        go.driver();
    }

    private class ExitButtonHandler
            implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //loop to write to our file all the instances of the list
            try {

                for (int i = 0; i < arrStudentList.size(); i++) {
                    writeBuffer.write(arrStudentList.get(i) + "\n\n");
                }

                writeBuffer.close();
            } catch (Exception ex) {
            }

            System.exit(0);
        }//end actionPerformed method
    }//end exitButtonHandler class

    //Class that stores the entered student into a List
    private class SubmitButtonHandler
            implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            //first setting our temp object of ClassRecord to the current textfields
            ClassRecord tempClass = new ClassRecord();
            tempClass.setFirstName(firstNameTF.getText());
            tempClass.setLastName(lastNameTF.getText());
            tempClass.setIdNum(Long.parseLong(idNumTF.getText()));
            tempClass.setFinalGrade(Integer.parseInt(finalGradeTF.getText()));
            tempClass.setMidtermGrade(Integer.parseInt(midtermGradeTF.getText()));
            tempClass.setTotalHwGrade(Integer.parseInt(totalHomeworkGradeTF.getText()));
            tempClass.setLetterGrade(letterGradeTF.getText().charAt(0));

            //add the temp class to the array.
            arrStudentList.add(tempClass);

            //reset the fields for better use on the user.
            JOptionPane.showMessageDialog(new JFrame(), "Student #" + idNumTF.getText() + " has been submitted.");
            lastNameTF.setText("");
            idNumTF.setText("");
            firstNameTF.setText("");
            finalGradeTF.setText("");
            midtermGradeTF.setText("");
            totalHomeworkGradeTF.setText("");
            letterGradeTF.setText("");



        }//end actionPerformed Method
    }//end SubmitButtonHandler sub class
} //end ClassRecordGui class

