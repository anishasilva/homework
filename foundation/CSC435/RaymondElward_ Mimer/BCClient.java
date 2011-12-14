/*--------------------------------------------------------
 
 1. Raymond Elward / 2/14/2011
 
 2.
 * //xpp3_min-1.1.3.4.O.jar and xstream-1.2.1.jar
 * //must be installed on the systems java classpath
 *
 * javac BCClient.java
 * java BCClient
 
 
 3.
 * In seperate shell window you must open
 * java MyWebServer
 * java BBClient
 
 
 *then you will use the client to pass XML to the server.
 
 
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



/* file is: BCClient.java   5-5-07  1.0

For use with webserver back channel. Written for Windows.

This program may contain bugs. Note: version 1.0.

To compile:

rem jcxclient.bat
rem java compile BCClient.java with xml libraries:
set classpath=%classpath%C:\dp\435\java\mime-xml\;c:\Program Files\Java\jdk1.5.0_05\lib\xstream-1.2.1.jar;c:\Program Files\Java\jdk1.5.0_05\lib\xpp3_min-1.1.3.4.O.jar;
javac -cp "c:\Program Files\Java\jdk1.5.0_05\lib\xstream-1.2.1.jar;c:\Program Files\Java\jdk1.5.0_05\lib\xpp3_min-1.1.3.4.O.jar" BCClient.java

Note that both classpath mechanisms are included. One should work for you.

Requires the Xstream libraries contained in .jar files to compile, AND to run.
See: http://xstream.codehaus.org/tutorial.html


To run:

rem rxclient.bat
rem java run BCClient.java with xml libraries:
set classpath=%classpath%C:\dp\435\java\mime-xml\;c:\Program Files\Java\jdk1.5.0_05\lib\xstream-1.2.1.jar;c:\Program Files\Java\jdk1.5.0_05\lib\xpp3_min-1.1.3.4.O.jar;
java BCClient

This is a standalone program to connect with MyWebServer.java through a
back channel maintaining a server socket at port 2570.

----------------------------------------------------------------------*/

import java.io.*;  // Get the Input Output libraries
import java.net.*; // Get the Java networking libraries
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

class myDataArray {
  int num_lines = 0;
  String[] lines = new String[8];
}

public class BCClient{
	//setting up the fields for in and out data.
  private static String XMLfileName = "C:\\temp\\mimer.output";
  private static PrintWriter      toXmlOutputFile;
  private static File             xmlFile;

  public static void main (String args[]) {
	  //setting up the address of the server that we will be connecting to.
      String serverName;
      String argOne = "WillBeFileName";
      if (args.length < 1) {
          serverName = "localhost";
      } else {
          serverName = args[0];
      }
	  
	  //XStream for marsheling our data for us.
      XStream xstream = new XStream();
      String[] testLines = new String[4];
      int i;
      myDataArray da = new myDataArray();
      myDataArray daTest = new myDataArray();

    System.out.println("Clark Elliott's back channel Client.\n");
    System.out.println("Using server: " + serverName + ", Port: 2540 / 2570");
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    try {
      String userData;
      do {
		  //gets the string text that we want to send to the back channel of the server.
	System.out.print
	  ("Enter a string to send to back channel of webserver, (quit) to end: ");
	System.out.flush ();
	userData = in.readLine ();
	da.lines[0] = "You "; da.lines[1] = "typed "; da.lines[2] = userData;
	da.num_lines = 3;
		  
		  //changes our object containing our data to xml
	String xml = xstream.toXML(da);
		  
	if (userData.indexOf("quit") < 0){
		//sending the users input to the server
	  sendToBC(xml, serverName);

		//printing to the console the users input in XML format
	  System.out.println("\n\nHere is the XML version:");
	  System.out.print(xml);

		//unmarsheling the data to check if it is converting back and forth correctly.
	  daTest = (myDataArray) xstream.fromXML(xml); // deserialize data
	  System.out.println("\n\nHere is the deserialized data: ");
		//printing to the console the users input in deserialized (original version plus the headers "you typed"
	  for(i=0; i < daTest.num_lines; i++){System.out.println(daTest.lines[i]);}
	  System.out.println("\n");

		//creates a temp file. and checks if it exists / can be deleted
	  xmlFile = new File(XMLfileName);
	  if (xmlFile.exists() == true && xmlFile.delete() == false){
	    throw (IOException) new IOException("XML file delete failed.");
	  }
		
		//creates a temp file
	  xmlFile = new File(XMLfileName);
	  if (xmlFile.createNewFile() == false){
	    throw (IOException) new IOException("XML file creation failed.");
	  }
	  else{
		  //outputs the data passed from the users into a temp file on the system.
	    toXmlOutputFile =
	      new PrintWriter(new BufferedWriter(new FileWriter(XMLfileName)));
	    toXmlOutputFile.println("First arg to Handler is: " + argOne + "\n");
	    toXmlOutputFile.println(xml);
	    toXmlOutputFile.close();
	  }
	}
		  //loops if the user doesnt want to quit.
      } while (userData.indexOf("quit") < 0);
      System.out.println ("Cancelled by user request.");


    } catch (IOException x) {x.printStackTrace ();}
  }



  static void sendToBC (String sendData, String serverName){
    Socket sock;
    BufferedReader fromServer;
    PrintStream toServer;
    String textFromServer;
    try{
      // Open our connection Back Channel on server:
      sock = new Socket(serverName, 2570);
      toServer   = new PrintStream(sock.getOutputStream());
      // Will be blocking until we get ACK from server that data sent
      fromServer =
	new  BufferedReader(new InputStreamReader(sock.getInputStream()));

      toServer.println(sendData);
      toServer.println("end_of_xml");
      toServer.flush();
      // Read two or three lines of response from the server,
      // and block while synchronously waiting:
      System.out.println("Blocking on acknowledgment from Server... ");
      textFromServer = fromServer.readLine();
      if (textFromServer != null){System.out.println(textFromServer);}
      sock.close();
    } catch (IOException x) {
      System.out.println ("Socket error.");
      x.printStackTrace ();
    }
  }
}
