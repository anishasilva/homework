/*--------------------------------------------------------

1. Raymond Elward / 2/14/2011

2.
 * //xpp3_min-1.1.3.4.O.jar and xstream-1.2.1.jar
 * //must be installed on the systems java classpath
 *
 * javac MyWebServer.java
 * java MyWebServer


3.
 * In seperate shell window you must open
 * java MyWebServer


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

 * One weird thing I noticed while going into directories is that it can't if
 * the directory name has a space in it. The client sents "%20" instead of a space
 * and I didn't write code to handle that because it wasn't acknowledged in the
 * assignment sheet.
----------------------------------------------------------*/

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import com.thoughtworks.xstream.XStream;

//worker class is instantiated as a thread everytime a client makes a request.
class Worker extends Thread {

    Socket sock;
    //constructor to assign the parameter as the socket.

    //constructor creates a worker from the socket passed by the client that connected.
    Worker(Socket s) {
        sock = s;
    }

    public void run() {
        //setting the in/out streams to null
        PrintStream out = null;
        BufferedReader in = null;

        try {
            //instantiating the in/out streams to
            out = new PrintStream(sock.getOutputStream());
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            try {
                String input;
                String filename;
                //reads the input from the client.
                input = in.readLine();
                System.out.println("Client request:");
                System.out.println(input);
                //parses the first line of input to get the name of the file requested.
                StringTokenizer request = new StringTokenizer(input, " ");
                if (request.nextToken().equals("GET") && request.hasMoreElements()) {
                    filename = request.nextToken();
                } else {
                    throw new FileNotFoundException();
                }
                boolean isFile = true;

                if (filename.startsWith("/cgi/addnums.fake")) {
                    addNums(out, filename);
                    isFile = false;
                }

                //simply security case to not allow the client to enter superdirectories.
                if (filename.indexOf("..") >= 0) {
                    throw new FileNotFoundException();
                }

                //case statements to find the correct MIME TYPE being requested.
                //or if it ends with "/" it sends them the directory

                String contentType = "text/plain";
                if (filename.endsWith(".xyz")){
                    contentType = "application/xyz";
                }else if (filename.endsWith(".txt")) {
                    contentType = "text/plain";
                } else if (filename.endsWith(".html") || filename.endsWith(".htm")) {
                    contentType = "text/html";
                } else if (filename.endsWith(".wml")) {
                    contentType = "text/vnd.wap.wml";
                } else if (filename.endsWith("/")) {
                    isFile = false;
                    sendDirectory(out, filename);
                } else if (isFile) {
                    throw new FileNotFoundException();
                }


                if (isFile == true) {
                    prepFile(out, filename, contentType);
                }

                //prints error message if the server can't be read.
            } catch (FileNotFoundException x) {
                fileNotFound(out, x);
            }
            //close connection.  the server is still running though
            sock.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    void addNums(PrintStream out, String filename) {
        //the Filename sent in should be something like:
        // /cgi/addnums.fake-cgi?person=YourName&num1=4&num2=5
        //
        try {
            StringTokenizer input = new StringTokenizer(filename, "=");
            if (input.countTokens() != 4) {
                throw new IOException();
            }
            input.nextToken();
            int indexToRemove = -1;
            String name = input.nextToken();

            //these 2 loops get the needed data from the GET client request.
            for (int i = 0; i < name.length(); i++)
                if (name.charAt(i) == '&'){
                    indexToRemove = i;
                    break;
                }
            name = name.substring(0, indexToRemove);


            String num1 = input.nextToken();
            for (int i = 0; i < num1.length(); i++)
                if (num1.charAt(i) == '&'){
                    indexToRemove = i;
                    break;
                }
            int n1 = Integer.parseInt(num1.substring(0, indexToRemove));

            int n2 = Integer.parseInt(input.nextToken());
            int result = n1 + n2;

            out.print("HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n\r\n" + "Hello " + name
                    + ",\r\nThe result of adding " + n1 + " and " + n2 + " is: " + result);
            out.flush();
            System.out.println("\nServer sending:\n" + "Hello " + name + ",The result of adding " + n1 + " and " + n2 + " is: " + result);
            

        } catch (IOException x) {
            fileNotFound(out, x);
        }
    }

    /*
     * prepFile method
     * gets the header ready and ships a file to the browser.
     */
    void prepFile(PrintStream out, String filename, String contentType) {
        try {
            if (filename.indexOf("/") == 0) {
                filename = filename.substring(1);
            }
            InputStream file = new FileInputStream(filename);
            File f1 = new File(filename);
            long contentLength = f1.length();

            //prints the header for the file being served.
            out.print("HTTP/1.1 200 OK\r\n"
                    + "Content-Length: " + contentLength
                    + "\r\nContent-Type: " + contentType + "\r\n\r\n");

            //message on server console to let the server user know what the client is accessing
            System.out.println("\nServer sending:\ncontentType: " + contentType + "\nfilename: " + filename + "\n");


            //writes the file to a buffer to write to the client.
            sendFile(out, file);
        } catch (IOException x) {
            fileNotFound(out, x);
        }
    }

    void sendDirectory(PrintStream out, String filename) {
        //gets the list of files in the directory that was requested
        //a lot of the ideas for this are taken from ReadFiles.java on the
        //assignment sheet.
        try {

            File f1 = new File("./" + filename + "/");

            BufferedWriter tempFile = new BufferedWriter(new FileWriter("temp032452345647612315.html"));

            File[] fileStrDirs = f1.listFiles();

            tempFile.write("<pre>" + "\r\n\r\n");
            tempFile.write("<h1>Index of " + filename + "</h1>" + "\r\n\r\n");
            //for loop goes through every item in the directory to dynamically create a file.
            for (int i = 0; i < fileStrDirs.length; i++) {
                //making it so hidden files don't show up and makes it show the temp.html that is this directory display
                //doesn't show up.
                if (fileStrDirs[i].getName().startsWith(".") || fileStrDirs[i].getName().startsWith("temp032452345647612315.html")) {
                    continue;
                }
                if (fileStrDirs[i].isDirectory()) {
                    tempFile.write("<a href=\"" + fileStrDirs[i].getName() + "/\">/" + fileStrDirs[i].getName() + "</a> <br>\n");
                }
                if (fileStrDirs[i].isFile()) {
                    tempFile.write("<a href=\"" + fileStrDirs[i].getName() + "\">" + fileStrDirs[i].getName() + "</a> <br>\n");
                }
                tempFile.flush();
            }
            tempFile.write("</pre>\r\n\r\n");
            File tempToDelete = new File("temp032452345647612315.html");
            long contentLength = tempToDelete.length();
            tempFile.close();

            InputStream file = new FileInputStream("temp032452345647612315.html");

            out.print("HTTP/1.1 200 OK\r\n"
                    + "Content-Length: " + contentLength
                    + "\r\nContent-Type: text/html\r\n\r\n");
            //reads from the newly created file out to the user.
            System.out.println("\nServerSending:\n" + "\nDirectory: " + filename + "\n");
            sendFile(out, file);
            //closes the file stream.
            file.close();
            //deletes the temp file dynamically created for the user.
            tempToDelete.delete();

        } catch (IOException x) {
            fileNotFound(out, x);
        }

    }
    /*
     * fileNotFound method
     * used to report error to the client
     * and to print a stackTrace of where the error occured to server.
     * invoked when there is an file not found or io error.
     */

    void fileNotFound(PrintStream out, Exception x) {

        out.print("HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n\r\n" + "404 Error file not found.");
        x.printStackTrace();
        out.flush();

    }

    /*
     * Sends the file to the client.
     * Here the client is the browser.
     */
    void sendFile(PrintStream out, InputStream file) {
        try {
            byte[] buffer = new byte[2048];
            int i;
            while ((i = file.read(buffer)) > 0) {
                out.write(buffer, 0, i);
            }
            out.flush();
        } catch (IOException x) {
            fileNotFound(out, x);
        }
    }
}

public class MyWebServer {

    //boolean value that tells the server when to be active or shutdown by a client.
    public static boolean controlSwitch = true;

    public static void main(String a[]) throws IOException {
        //sets the amount of clients that can connect to the server at the same time at 6 and the port connection at 6000
        int q_len = 6;
        int port = 2540; // PORT NUMER 2540
        Socket sock;

        BCLooper AL = new BCLooper(); // create a DIFFERENT thread for Back Door Channel
	Thread t = new Thread(AL);
	t.start();  // ...and start it, waiting for Back Channel input


        //constructs the server.
        ServerSocket servsock = new ServerSocket(port, q_len);

        //displays that the server is up and running.
        System.out.println("Ray's Web Server up and running at port " + port + "\n");

        //loop that runs until a client requests shutdown.
        //the loop will spawn a new thread for any client that connects as a new Worker
        while (controlSwitch) {
            sock = servsock.accept();
            new Worker(sock).start();
            //shutdown bug::
            //try {Thread.sleep(10000);} catch(InterruptedException ex) {}
        }
    }
}


/*
 * The following is the code snippits from the BClooper.txt
 * this is here to get an effect means of XML communication between the client and server
 * for back channel communication.
 */

class myDataArray {
  int num_lines = 0;
  String[] lines = new String[10];
}
//Back Channel worker handles admin connections at 2570 port (our back channel)
class BCWorker extends Thread {
    private Socket sock;
    private int i;
    BCWorker (Socket s){sock = s;}
    PrintStream out = null; BufferedReader in = null;

    //initializing fields for receiving data.
    String[] xmlLines = new String[15];
    String[] testLines = new String[10];
    String xml;
    String temp;
    XStream xstream = new XStream();
    final String newLine = System.getProperty("line.separator");
    myDataArray da = new myDataArray();

    public void run(){
      System.out.println("Called BC worker.");
      try{
          //initializing the reader and stream for input and output.
	in =  new BufferedReader(new InputStreamReader(sock.getInputStream()));
	out = new PrintStream(sock.getOutputStream()); // to send ack back to client
	i = 0; xml = "";
	while(true){
	  temp = in.readLine();
	  if (temp.indexOf("end_of_xml") > -1) break;
	  else xml = xml + temp + newLine; // Should use StringBuilder in 1.5
	}
        //printing out all the XML data to the server console.
	System.out.println("The XML marshaled data:");
	System.out.println(xml);
	out.println("Acknowledging Back Channel Data Receipt"); // send the ack
	out.flush(); sock.close();

        da = (myDataArray) xstream.fromXML(xml); // deserialize / unmarshal data
	System.out.println("Here is the restored data: ");
        //sending data to the server console.
	for(i = 0; i < da.num_lines; i++){
	  System.out.println(da.lines[i]);
	}
      }catch (IOException ioe){
      } // end run
    }
}

class BCLooper implements Runnable {
  public static boolean adminControlSwitch = true;

  public void run(){ // RUNning the Admin listen loop
    System.out.println("In BC Looper thread, waiting for 2570 connections");

    int q_len = 6; /* Number of requests for OpSys to queue */
    int port = 2570;  // Listen here for Back Channel Connections
    Socket sock;

    try{
      ServerSocket servsock = new ServerSocket(port, q_len);
      while (adminControlSwitch) {
	// wait for the next ADMIN client connection:
	sock = servsock.accept();
	new BCWorker (sock).start();
      }
    }catch (IOException ioe) {System.out.println(ioe);}
  }
}


