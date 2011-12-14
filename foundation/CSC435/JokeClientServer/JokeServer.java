
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/*--------------------------------------------------------

1. Raymond Elward / 1/18/2011

2.
 * javac JokeServer.java
 * java JokeServer


3.
 * In seperate shell windows you should open
 * java JokeServer
 * java JokeClient
 * java JokeCilientAdmin

this is currently implemented to run on localhost
 * but if you start the server on a different ip address you can connect by
 * java JokeServer 000.000.000.000
 * java JokeClient 000.000.000.000
 *
 * port is set at 6001 for clients
 * port is set at 6010 for Admins

4. Java version used:

javac 1.6.0_22

5. List of files included in this directory:

 * 1. JokeServer.java
 * 2. JokeClient.java
 * 3. JokeClientAdmin.java
 *
4. Notes:

 * All the joke / proverbs recieved from the server will be stored locally
 * on the clients system in the same directory in "JokeOutput.txt".
 * 
 * I use the not so scalable version of storing and randomizing jokes for people.
 * I had this implemented before it was discouraged in class and didn't have time 
 * to reimplement.
 *
----------------------------------------------------------*/
public class JokeServer {
    /*
     * jokeProverb variable: true = Joke mode;
     * false = Proverb mode.
     */

    public static boolean jokeProverb = true;
    public static boolean maintenanceMode = false;
    public static boolean clientControlSwitch = true;

    public static void main(String args[]) throws IOException {
        //amount of clients
        int q_len = 6;
        //port number for clients
        int port = 6001;
        //socket for the client.
        Socket sock;

        //starts a thread to loop around and listen for admins
        AdminLooper AL = new AdminLooper();
        Thread t = new Thread(AL);
        t.start();

        //assigns the port and settings to a server socket.
        ServerSocket servsock = new ServerSocket(port, q_len);

        System.out.println("Ray's joke server is up and running at port: " + port);

        //loop to continuously accept clients.
        while (clientControlSwitch) {
            sock = servsock.accept();
            new ClientWorker(sock).start();
        }
    }
}
/*
 * Keeps a loop running for admin clients that wish to connect.
 */

class AdminLooper implements Runnable {

    public static boolean adminControlSwitch = true;

    public void run() {
        System.out.println("this is the admin inner run function! port 6010");
        //maximum admin server q's is 6
        int q_len = 6;
        //port set on a different channel than the client.
        int port = 6010;
        //admin client socket
        Socket sock;
        //loop to continuously accept admin clients.
        try {
            ServerSocket servsock = new ServerSocket(port, q_len);
            while (adminControlSwitch) {
                sock = servsock.accept();
                new AdminWorker(sock).start();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
/*
 * this class handles clients, it interprts the state they send and then submits a
 * joke or proverb depening on what mode they are running in.
 */

class ClientWorker extends Thread {

    Socket clientSock;
    String jokeState;
    String proverbState;

    ClientWorker(Socket clientSock) {
        this.clientSock = clientSock;
    }

    public void run() {
        //This handles the worker threads. by first creating streams to get
        //information from the client.
        PrintStream out = null;
        BufferedReader in = null;

        try {
            //starts the streams
            out = new PrintStream(clientSock.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
            PrintWriter outFile = new PrintWriter(new FileWriter("JokeOutput.txt"));

            //checks to make sure the server isn't in maintenanceMode or being shutdown.
            if (JokeServer.clientControlSwitch == false) {
                System.out.println("Listener is now shutting down.");
                out.println("Server is now shutting down.  Cheers!");
            } else if (JokeServer.maintenanceMode == true) {
                System.out.println("Admin client has put the server in maintenance mode.");
                out.println("The server is temporarily unavailable -- check-back shortly.");
            } else {
                try {
                    //reads the line sent from the client.
                    String fromClient = in.readLine();
                    //parses the line sent from the client.
                    StringTokenizer sFromClient = new StringTokenizer(fromClient, "&");
                    //gives warning if the input from the client isnt correctly formated.
                    if (sFromClient.countTokens() != 4) {
                        System.out.print("Wrong format for this server."
                                + "Must be <statement>&<jokeState>&<proverbState>&<name>\n");
                    }
                    //first index shows choice "yes" or "no" for next joke
                    String choice = sFromClient.nextToken();
                    //second index tells the server what jokes this client has seen.
                    jokeState = sFromClient.nextToken();
                    //reset jokes if they've seen them all.
                    if (jokeState.equals("11111")) {

                        jokeState = "00000";
                    }
                    //third index tells the server what proverbs this client has seen.
                    proverbState = sFromClient.nextToken();
                    //resets the proverbs if they've seen them all
                    if (proverbState.equals("11111")) {
                        proverbState = "00000";
                    }
                    //last index is the name the person gave to the client.
                    String name = sFromClient.nextToken();

                    //if the client wants a joke then we send them to the printJoke
                    //or printProverb function depending on which state the server is it.
                    if (choice.indexOf("yes") > -1 || choice.indexOf("Yes") > -1) {
                        if (JokeServer.jokeProverb == true) {
                            out.println(printJoke(name) + "&" + jokeState + "&" + proverbState);
                            System.out.println("Looking up Joke.");
                        } else if (JokeServer.jokeProverb == false) {
                            out.println(printProverb(name) + "&" + jokeState + "&" + proverbState);
                            System.out.println("Looking up Proverb.");
                        }
                        //if the client doesnt want a joke, we say goodbye.
                    } else if (choice.indexOf("no") > -1 || choice.indexOf("No") > -1) {
                        JokeServer.clientControlSwitch = false;
                        out.println("Sorry to hear that.");
                        out.println("Goodbye.  Please type quit to exit.");
                        //if we don't know what they want, we say we don't understand.
                    } else {
                        out.println("I'm sorry.  I did not understand your command.  Please try again.");
                    }

                } catch (IOException ioe) {
                    System.out.println("server read error");
                    ioe.printStackTrace();

                }
            }
            clientSock.close();

        } catch (IOException x) {
            System.out.println("Server read error.");
            x.printStackTrace();
        }
    }

    private String printJoke(String name) {
        //checks which jokes have been used in this client's jokeState.
        String joke = "";
        boolean zero, one, two, three, four;
        if (jokeState.charAt(0) == '1') {
            zero = true;
        } else {
            zero = false;
        }
        if (jokeState.charAt(1) == '1') {
            one = true;
        } else {
            one = false;
        }
        if (jokeState.charAt(2) == '1') {
            two = true;
        } else {
            two = false;
        }
        if (jokeState.charAt(3) == '1') {
            three = true;
        } else {
            three = false;
        }
        if (jokeState.charAt(4) == '1') {
            four = true;
        } else {
            four = false;
        }

        //randomizes a new joke.  not scalable.
        while (true) {
            int randomPick = (int) (Math.random() * 5);
            if (randomPick == 0 && zero == false) {
                joke = "Joke A:Why did " + name + " cross the road?  To get to the other side.";
                jokeState = "1" + jokeState.substring(1);
                return joke;
            } else if (randomPick == 1 && one == false) {
                joke = "Joke B:Why was the Energizer Bunny arrested? He was charged with battery.";
                jokeState = jokeState.substring(0, 1) + "1" + jokeState.substring(2);
                return joke;
            } else if (randomPick == 2 && two == false) {
                joke = "Joke C:What do you call a fish with no eyes?  A fsh!";
                jokeState = jokeState.substring(0, 2) + "1" + jokeState.substring(3);
                return joke;
            } else if (randomPick == 3 && three == false) {
                joke = "Joke D:Two pretzels walk into a bar.  One was a salted.";
                jokeState = jokeState.substring(0, 3) + "1" + jokeState.substring(4);
                return joke;
            } else if (randomPick == 4 && four == false) {
                joke = "Joke E:Why don't aliens eat clowns? Because they taste funny.";
                jokeState = jokeState.substring(0, 4) + "1";
                return joke;
            }
        }


    }

    private String printProverb(String name) {
        //checks what proverbs have been told to this client.
        String proverb = "";
        boolean zero, one, two, three, four;
        if (proverbState.charAt(0) == '1') {
            zero = true;
        } else {
            zero = false;
        }
        if (proverbState.charAt(1) == '1') {
            one = true;
        } else {
            one = false;
        }
        if (proverbState.charAt(2) == '1') {
            two = true;
        } else {
            two = false;
        }
        if (proverbState.charAt(3) == '1') {
            three = true;
        } else {
            three = false;
        }
        if (proverbState.charAt(4) == '1') {
            four = true;
        } else {
            four = false;
        }

        //randomizes a new proverb for the client.  not scalable.

        while (true) {
            int randomPick = (int) (Math.random() * 5);
            if (randomPick == 0 && zero == false) {
                proverb = "Proverb 1:Ignorance is bliss.";
                proverbState = "1" + proverbState.substring(1);
                return proverb;
            } else if (randomPick == 1 && one == false) {
                proverb = "Proverb 2:It is important to remember that if " + name + " is early to bed and early to rise, then " + name + " will be healthy, wealthy and wise. ";
                proverbState = proverbState.substring(0, 1) + "1" + proverbState.substring(2);
                return proverb;
            } else if (randomPick == 2 && two == false) {
                proverb = "Proverb 3:Don't kill a mosquito with a bazooka.";
                proverbState = proverbState.substring(0, 2) + "1" + proverbState.substring(3);
                return proverb;
            } else if (randomPick == 3 && three == false) {
                proverb = "Proverb 4:A good beginning makes a good ending.";
                proverbState = proverbState.substring(0, 3) + "1" + proverbState.substring(4);
                return proverb;
            } else if (randomPick == 4 && four == false) {
                proverb = "Proverb 5:A person is known by the company (s)he keeps.";
                proverbState = proverbState.substring(0, 4) + "1";
                return proverb;
            }
        }
    }
}

/*
 * this class handles an admin thread.
 * The server changes modes depending on their requests.
 */
class AdminWorker extends Thread {

    Socket adminSock;

    AdminWorker(Socket adminSock) {
        this.adminSock = adminSock;
    }

    public void run() {
        PrintStream out = null;
        BufferedReader in = null;

        try {
            out = new PrintStream(adminSock.getOutputStream());
            in = new BufferedReader(new InputStreamReader(adminSock.getInputStream()));
            try {
                String choice = in.readLine();
                System.out.println("Admin is on the server.");
                //there is currently a bug with this implementaion that kind of springs from the
                //InetClientServer bug.  After an admin pings the Server to shut down the server will not
                //completely shut down until the client pings the server for some more output.
                if (choice.indexOf("Shutdown") > -1) {
                    JokeServer.clientControlSwitch = false;
                    AdminLooper.adminControlSwitch = false;
                    System.out.println("Admin has sent a shutdown request.");
                    out.println("Shutdown request has be recorded.");
                    out.println("please send final shutdown request to listner.");
                    //changes the server to joke mode.
                } else if (choice.indexOf("Joke") > -1 || choice.indexOf("joke-mode") > -1) {
                    if (JokeServer.maintenanceMode == true) {
                        JokeServer.maintenanceMode = false;
                        out.println("Now out of maintenance mode.");
                    }
                    JokeServer.jokeProverb = true;
                    out.println("Setting to Joke mode.");

                    //changes the server to proverb mode.
                } else if (choice.indexOf("Proverb") > -1 || choice.indexOf("proverb-mode") > -1) {
                    if (JokeServer.maintenanceMode == true) {
                        JokeServer.maintenanceMode = false;
                        out.println("Now out of maintenance mode.");
                    }
                    JokeServer.jokeProverb = false;
                    out.println("Setting to Proverb mode.");
                    //changes the server to maintenance mode.
                } else if (choice.indexOf("Maintenance") > -1 || choice.indexOf("maintenance-mode") > -1) {
                    if (JokeServer.maintenanceMode == true) {
                        JokeServer.maintenanceMode = false;
                        out.println("Now out of maintenance mode.");
                    } else {
                        JokeServer.maintenanceMode = true;
                        out.println("Set to maintenance mode.");
                    }
                    //lets the admin end its session.
                } else if (choice.indexOf("quit") > -1 || choice.indexOf("Quit") > -1) {
                    AdminLooper.adminControlSwitch = false;
                    out.println("Sorry to hear that.");
                    out.println("Goodbye.  Please type quit to exit.");

                } else {
                    out.println("I'm sorry.  I did not understand your command.  Please try again.");
                }


            } catch (IOException ioe) {
                System.out.println("Server read error.");
                ioe.printStackTrace();
            }
        } catch (IOException x) {
            System.out.println("Server read error.");
            x.printStackTrace();
        }
    }
}
