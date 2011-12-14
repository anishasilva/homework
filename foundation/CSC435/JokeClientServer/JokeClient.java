
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/*--------------------------------------------------------

1. Raymond Elward / 1/18/2011

2.
 * javac JokeClient.java
 * java JokeClient <server>


3.
 * In seperate shell windows you must open
 * java JokeServer
 * java JokeClient

this is currently implemented to run on localhost
 * but if you start the server on a different ip address you can connect by
 * java JokeServer 000.000.000.000
 * java JokeClient 000.000.000.000
 *
 * port is set at 6001

4. Java version used:

javac 1.6.0_22

5. List of files included in this directory:

 * 1. JokeServer.java
 * 2. JokeClient.java
 * 3. JokeClientAdmin.java
 *
4. Notes:

 * All the joke / proverbs recieved from the server will be stored locally
 * on the clients system in the same directory in "JokeOutput.txt"
 *
 * I did do the bragging rights part.  After every return from the server
 * I save the state to state.txt.  this file is loaded only if there is a crash
 * or a ctrl+c out of the client.  upon normal exiting (typing "no") the
 * state.txt will be deleted so the next time you fire up the client it is
 * a clean state.
----------------------------------------------------------*/
public class JokeClient {

    static String jokeState;
    static String proverbState;
    static StringTokenizer dataTo, dataFrom;
    static ArrayList<String> jokes;

    public static void main(String args[]) {
        //starting the states and list of jokes.
        jokes = new ArrayList<String>();
        if (jokeState == null)
            jokeState = "00000";
        if (proverbState == null)
            proverbState = "00000";
        //bragging rights function.
        readStateFromDisk();
        //getting server name from the command line, or local host if none typed in.
        String serverName;
        if (args.length < 1) {
            serverName = "localhost";
        } else {
            serverName = args[0];
        }

        //introduction to the server.
        System.out.println("\nWelcome to Ray's Joke/Proverb Client! \n");

        System.out.println("We are using server:" + serverName + " at port 6001.\n");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        //reads in the users name.
        System.out.print("Enter your name: ");
        String name = "";
        try {
            name = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String choice;
            do {
                //checks what the users wants.  yes/no
                System.out.print("Would you like to hear a joke/proverb? (yes/no): ");
                System.out.flush();
                choice = in.readLine();
                //if the user says yes, then we goto the getJoke function.
                if (choice.indexOf("no") < 0) {
                    getJoke(serverName, choice, name);
                }
                //loop keeps going until user quits.
            } while (choice.indexOf("no") < 0);
            //if user quits it exits
            System.out.println("Okay.  Have a good day.");
            //prints all the jokes to the JokeOutpput.txt
            PrintWriter outFile = new PrintWriter(new FileWriter("JokeOutput.txt"));
            for (String s : jokes){
                outFile.println(s);
            }
            outFile.flush();
            outFile.close();
            //bragging rights: deletes the save state file because it's only needed if a crash happens.
            new File("state.txt").delete();
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
/*
 * Starts connection with the server.
 * will send the clients state and name.  The server will then send back a
 * joke or proverb and the updated state of the client.
 */
    public static void getJoke(String serverName, String choice, String name) {
        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;
        String joke;

        try {
            //gets the streams all set up
            PrintWriter outFile = new PrintWriter(new FileWriter("JokeOutput.txt"));
            sock = new Socket(serverName, 6001);
            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toServer = new PrintStream(sock.getOutputStream());
            //sets the choice/state/username in the format to be read by the server.
            String stringToServer = choice + "&" + jokeState + "&" + proverbState + "&" + name;
            toServer.println(stringToServer);
            toServer.flush();
            //reads the line returned from the server.
            textFromServer = fromServer.readLine();
            //unrolls and formats the data to get the joke, and the state
            dataFrom = new StringTokenizer(textFromServer, "&");
            joke = dataFrom.nextToken();
            //adds the joke to our running collection for output at the end.
            jokes.add(joke);
            //sets the updated states.
            System.out.println("\n\"" + joke + "\"\n");
            try {
                jokeState = dataFrom.nextToken();
                proverbState = dataFrom.nextToken();
            } catch (NoSuchElementException noe) {
            }
            //added for saving state after the client exits:

            //bragging rights: writes the states to state.txt.
            BufferedWriter write = new BufferedWriter(new FileWriter("state.txt"));
            write.write(jokeState + "\n" + proverbState);
            write.flush();
            

        } catch (IOException x) {
            System.out.println("Socket error.");
            x.printStackTrace();
        }

    }

    private static void readStateFromDisk() {
        BufferedReader read;
        try {
            //checks the file state.txt if there was any saved state for this execution.
            read = new BufferedReader(new FileReader("state.txt"));
        } catch (FileNotFoundException ex) {
            return;
        }
        try{
            jokeState = read.readLine();
            proverbState = read.readLine();
        } catch (IOException ex){}

    }
}
