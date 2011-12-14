/*--------------------------------------------------------

1. Raymond Elward / 2/14/2011

2.
 * //xpp3_min-1.1.3.4.O.jar and xstream-1.2.1.jar
 * //must be installed on the systems java classpath
 *
 * javac BCHandler.java
 *
 * will be called from shim.bat when a .xyz file is requested through
 * MyWebServer
 *


3.
 * A seperate shell will open itself if your shim.bat is configured
 * correctly.
 *
 * java -Dfirstarg=%1 BCHandler
 * is how it will be called from the specific shim.bat


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

 * this program is a stiched together version of Handler.java
 * and BCClient.java.  It is meant to handle requests from shim.bat, and act
 * as a back channel client so that it will send the data back to the server.
 *
 * See comments.html and mimer-discussion.html for full details on how to get
 * this running.
----------------------------------------------------------*/

import java.io.*;  // Get the Input Output libraries
import java.net.*; // Get the Java networking libraries
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.util.Properties;

/**
 *
 * @author Raymond Elward
 */
public class BCHandler {
    //fields for holding the data.

    private static String XMLfileName = "C:\\temp\\mimer.output";
    private static PrintWriter toXmlOutputFile;
    private static File xmlFile;
    private static BufferedReader fromMimeDataFile;
    //inner class to hold the data being passed through the back channel.

    class MyDataArray {

        int num_lines = 0;
        String[] lines = new String[8];
    }

    public static void main(String args[]) {
        int i = 0;
        /*
         * setting up the in stream from the file
         * and a data array to hold the files data.
         */
        String serverName;
        if (args.length < 1) {
            serverName = "localhost";
        } else {
            serverName = args[0];
        }
        BufferedReader in =
                new BufferedReader(new InputStreamReader(System.in));
        myDataArray da = new myDataArray();
        myDataArray daTest = new myDataArray();
        XStream xstream = new XStream();

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

            String xml = xstream.toXML(da);

            sendToBC(xml, serverName);

            System.out.println("\n\nHere is the XML version:");
            System.out.print(xml);

            daTest = (myDataArray) xstream.fromXML(xml); // deserialize data
            System.out.println("\n\nHere is the deserialized data: ");
            for (i = 0; i < daTest.num_lines; i++) {
                System.out.println(daTest.lines[i]);
            }
            System.out.println("\n");

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
                toXmlOutputFile.println(xml);
                toXmlOutputFile.close();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    //function sends the data to the server in XML format.
    static void sendToBC(String sendData, String serverName) {
        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;
        try {
            // Open our connection Back Channel on server:
            sock = new Socket(serverName, 2570);
            toServer = new PrintStream(sock.getOutputStream());
            // Will be blocking until we get ACK from server that data sent
            fromServer =
                    new BufferedReader(new InputStreamReader(sock.getInputStream()));

            toServer.println(sendData);
            toServer.println("end_of_xml");
            toServer.flush();
            // Read two or three lines of response from the server,
            // and block while synchronously waiting:
            System.out.println("Blocking on acknowledgment from Server... ");
            textFromServer = fromServer.readLine();
            if (textFromServer != null) {
                System.out.println(textFromServer);
            }
            sock.close();
        } catch (IOException x) {
            System.out.println("Socket error.");
            x.printStackTrace();
        }
    }
}
