/*--------------------------------------------------------
 
 1. Raymond Elward / 2/14/2011
 
 2.
 * //xpp3_min-1.1.3.4.O.jar and xstream-1.2.1.jar
 * //must be installed on the systems java classpath
 *
 * javac Handler.java
 * java -Dfirstarg=%1 Handler (when called from shim.bat see below for details on shim.bat)
 
 
 3.
 * In seperate shell window you must open
 * open shim.bat to begin the program.  must have registers pointing .xyz applications at shim.bat. 
 
 
 4. Java version used:
 
 javac 1.6.0_22
 
 5. List of files included in this directory:
 
 * 1. MyWebServer.java
 * 2. Handler.java
 * 3. BCClient.java
 * 4. BCHandler.java
 * 5. comments.html
 * 6. checklist-mimer.html
 * 7. mimer-discussion.html
 * 8. serverlog.txt
 *
 *
 4. Notes:
 
 * Please note that none of this code is mine, only my comments.
 ----------------------------------------------------------*/



/* Handler.java 1.0
Clark Elliott MIMER shim java example

Capture Environment Variables passed from .bat file through java.exe.

Assuming the first argument is a valid file name, read five lines
of data from the file, and display the data on the console.

Note that NO XML is used in this preliminary program, although some
variable names refer to XML for consistency with other programs
in this assignment.

Here is the DOS .bat file to run this Java program:
rem This is shim.bat
rem Have to set classpath in batch, passing as arg does not work:
set classpath=%classpath%;c:/dp/435/java/mime-xml/
rem Pass the name of the first argument to java:
java -Dfirstarg="%1" Handler

To run:

> shim mimer-data.xyz

...where mimer-data.xyz has five lines of ascii data in it.

 */

import java.io.*;
import java.util.*;

//behaviorless class myDataArray used only to hold data.
class myDataArray {

    int num_lines = 0;
    String[] lines = new String[8];
}

public class Handler {

    //setting up field for io
    private static String XMLfileName = "C:\\temp\\mimer.output";
    private static PrintWriter toXmlOutputFile;
    private static File xmlFile;
    private static BufferedReader fromMimeDataFile;

    public static void main(String args[]) {
        int i = 0;
        /*
         * setting up the in stream from the file
         * and a data array to hold the files data.
         */
        BufferedReader in =
                new BufferedReader(new InputStreamReader(System.in));
        myDataArray da = new myDataArray();

        try {
            System.out.println("Executing the java application.");
            System.out.flush();
            Properties p = new Properties(System.getProperties());

            //gets the argument passed in from shim.bat
            String argOne = p.getProperty("firstarg");
            System.out.println("First var is: " + argOne);

            //starts reading the file that is passed from the reader
            fromMimeDataFile = new BufferedReader(new FileReader(argOne));
            // Only allows for five lines of data in input file plus safety:
            while (((da.lines[i++] = fromMimeDataFile.readLine()) != null) && i < 8) {
                System.out.println("Data is: " + da.lines[i - 1]);
            }
            //prints number of lines in the reader
            da.num_lines = i - 1;
            System.out.println("i is: " + i);

            //creates a temp file. and checks if it exists / can be deleted.
            xmlFile = new File(XMLfileName);
            if (xmlFile.exists() == true && xmlFile.delete() == false) {
                throw (IOException) new IOException("XML file delete failed.");
            }
            //creates the temp file
            xmlFile = new File(XMLfileName);
            if (xmlFile.createNewFile() == false) {
                throw (IOException) new IOException("XML file creation failed.");
            } else {
                //outputs the file passed through shim.bat into the temp file on the system.
                toXmlOutputFile =
                        new PrintWriter(new BufferedWriter(new FileWriter(XMLfileName)));
                toXmlOutputFile.println("First arg to Handler is: " + argOne + "\n");
                toXmlOutputFile.println("<This where the persistent XML will go>");
                toXmlOutputFile.close();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
