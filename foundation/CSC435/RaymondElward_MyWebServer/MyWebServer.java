/*--------------------------------------------------------

1. Raymond Elward / 1/26/2011

2.
 * javac MyWebServer.java
 * java MyWebServer


3.
 * In seperate shell window you must open
 * java MyWebServer


4. Java version used:

javac 1.6.0_22

5. List of files included in this directory:

 * 1. MyWebServer.java
 * 2. MyListener.java
 * 3. MyChecklist.html
 * 4. http-stream.txt
 * 5. serverlog.txt
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
                if (filename.endsWith(".txt")) {
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
