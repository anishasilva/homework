
import java.io.*;

public class BufferInput {

    public static void main(String[] args) {
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
            String line = readBuffer.readLine();
            System.out.println();
            while ((line) != null) {
                line = readBuffer.readLine();
                System.out.println(line);
            }

//close the buffered reader
         //   readBuffer.close();
           // fileReader.close();

        } catch (Exception e) {
        }
    } //end main
} //end class
