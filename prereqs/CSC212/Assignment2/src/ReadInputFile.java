
import java.io.*;

public class ReadInputFile {

    public static void main(String[] args) throws IOException {
        try {
            FileReader fileReader = new FileReader("numbers.txt");

//Create a buffer for reading
//Connect the buffer to the correct input stream
            BufferedReader readBuffer = new BufferedReader(fileReader);

            //Read in one line at a time from the buffer
//Keep reading (looping) until the last line is reached
//The conditional is a fairly ugly line - you don't have
//to know everything about it for now - we'll discuss it
//a little more in class.
            String line = null;
            double score = 0, numberOfScores = 0, total = 0;
            //initilizing array to store the scores.
            double[] array = new double[17];

            while ((line = readBuffer.readLine()) != null) {
                score = Double.parseDouble(line);
                //added by Ray to put the scores into the array.
                array[(int) numberOfScores] = score;
                numberOfScores++;
                total += score;
            }
            double highest = array[0], lowest = array[0];
            //simple for loop added by Ray to compute the highest and lowest and also output each value.
            for (int i = 0; i < array.length; i++) {
                if (array[i] > highest) {
                    highest = array[i];
                }
                if (array[i] < lowest) {
                    lowest = array[i];
                }
                System.out.printf(array[i] + "\t");
            }
            //outputs the values needed by assignment (also the 4th piece of code added by RAY!)
            System.out.println("\nThe highest was:" + highest + "\n");
            System.out.println("The lowest was:" + lowest + "\n");
            System.out.println("The average was: " + (total / numberOfScores) + "\n");

//close the buffered reader
            readBuffer.close();
            fileReader.close();

        } catch (Exception e) {
        }
    } //end main
} //end class

