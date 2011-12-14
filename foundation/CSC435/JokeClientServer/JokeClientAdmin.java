
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/*--------------------------------------------------------

1. Raymond Elward / 1/18/2011

2.
 * javac JokeClientAdmin.java
 * java JokeClientAdmin <server>


3.
 * In seperate shell windows you must open
 * java JokeServer
 * java JokeClientAdmin

this is currently implemented to run on localhost
 * but if you start the server on a different ip address you can connect by
 * java JokeServer 000.000.000.000
 * java JokeClientAdmin 000.000.000.000
 *
 * port is set at 6010

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
----------------------------------------------------------*/
public class JokeClientAdmin {
    public static void main(String args[]){
        //gets the server name from the command line or defaults to localhost.
        String serverName;
        if (args.length < 1) {
            serverName = "localhost";
        } else {
            serverName = args[0];
        }

        //greeting and setting up for user input.
        System.out.println("\nWelcome to Ray's admin client for the Joke/proverb server.\n");

        System.out.println("We are using server:" + serverName + " at port 6010\n");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            String choice;
            do {
                System.out.println("What mode do you want to go into?\n(\"joke-mode\" / \"proverb-mode\" / \"maintenance-mode\")\n");
                System.out.print("Also \"Shutdown\" to shutdown the server.\n or \"Quit\" to end this client: " );
                System.out.flush();
                choice = in.readLine();
                //transmiting the user input to the server.
                if (choice.indexOf("Proverb") < 0||choice.indexOf("proverb-mode") < 0){
                    transmit(choice, serverName);
                } else if (choice.indexOf("Joke") < 0||choice.indexOf("joke-mode") < 0){
                    transmit(choice, serverName);
                } else if (choice.indexOf("Maintenance") < 0||choice.indexOf("maintenance-mode") < 0){
                    transmit(choice, serverName);
                } else if (choice.indexOf("Quit") < 0)
                    System.out.println("Wrong input.  Try again.");
                //loops until the user says quit.  then exits.
            } while (choice.indexOf("Quit") < 0);
            System.out.println("Okay.  Goodbye then.");
        } catch (IOException x ){
            x.printStackTrace();
        }
        
    }
    /*
     * transmit the request given from the user to the server to change the mode.
     */

    private static void transmit(String choice, String serverName) {
        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;

        try {
            //connects with the server.
            sock = new Socket(serverName, 6010);
            fromServer = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );
            toServer = new PrintStream( sock.getOutputStream() );

            //prints the choice the admin got to the server thread.
            toServer.println(choice);
            toServer.flush();
            //reads back what the servers response is.
            textFromServer = fromServer.readLine();

            //prints that response to screen
            System.out.println("\n**" + textFromServer + "**\n");

        } catch (IOException x){
            System.out.println("Socket error.");
            x.printStackTrace();
        }
    }
}
