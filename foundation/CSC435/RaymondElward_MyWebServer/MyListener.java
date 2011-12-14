/*--------------------------------------------------------

1. Raymond Elward / 1/26/2011

2.
 * javac MyListener.java
 * java MyListener


3.
 * In seperate shell windows you must open
 * java MyListener
 *
 * port is set to 2540


4. Java version used:

javac 1.6.0_22

5. List of files included in this directory:

 * 1. MyWebServer.java
 * 2. MyListener.java
 * 3. MyChecklist.html
 * 4. http-stream.txt
 * 5. serverlog.txt
 *
4. Notes:

 * 95% of the code is straight copied from the pdf.  Added in my own comments
 * of my own understanding of what the code was doing.
----------------------------------------------------------*/

import java.io.*;
import java.net.*;

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
                String input = "";
                //reads the input from the client.
                while ((input = in.readLine()) != null) {
                    System.out.println(input);
                }

                out.println("Got your request");
                out.flush();

                //prints error message if the server can't be read.
            } catch (IOException x) {
                System.out.println("Server read error");
                x.printStackTrace();
            }
            //close connection.  the server is still running though
            sock.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}

public class MyListener {

    //boolean value that tells the server when to be active or shutdown by a client.
    public static boolean controlSwitch = true;

    public static void main(String a[]) throws IOException {
        //sets the amount of clients that can connect to the server at the same time at 6 and the port connection at 6000
        int q_len = 6;
        int port = 2540; // changed port number
        Socket sock;

        //constructs the server.
        ServerSocket servsock = new ServerSocket(port, q_len);

        //displays that the server is up and running.
        System.out.println("Ray's port listener up and running at port " + port + "\n");

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
