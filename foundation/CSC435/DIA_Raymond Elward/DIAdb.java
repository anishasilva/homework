/*--------------------------------------------------------

1. Raymond Elward / 2/25/2011

2.
 *
 * javac DIAdb.java
 * java DIAdb
 *
 * must be at port 6010 for agents to be able to find it.


3.
 * instructions:
 * works as a database for agents.  Stores the agents information by their
 * group ID.
 *
 * Must have NameServer and HostServer running for this to do anything.


4. Java version used:

javac 1.6.0_22

5. List of files included in this directory:

 * 1. HostServer.java
 * 2. NameServer.java
 * 3. DIAdb.java
 * 4. diaDiscussion.html
 * 5. serverlog.txt
 * 6. checklist-agent.html
 *
4. Notes:

 * Just a simple database connected to by a server that stores and
 * returns data for agents.  N.V.I.
----------------------------------------------------------*/

import java.io.*;
import java.net.*;
import java.util.*;

public class DIAdb {

    /*
     * Starts a loop to handle incoming connections to the data base
     * information is stored by group so each group can get their data.
     */
    public static void main(String[] args) throws IOException {
        int q_len = 6;
        int port = 6010; //standard set port

        Socket sock;

        ServerSocket servSock = new ServerSocket(port, q_len);

        System.out.println("DIAdb up and running at port:" + port);
        while (true) {
            sock = servSock.accept();
            new ClientHandler(sock).start();
        }
    }
}

class ClientHandler extends Thread {

    /*
     * This class will handle incoming requests from agents to either get at their data
     * or to send something to be stored.
     */
    Socket sock;

    ClientHandler(Socket s) {
        sock = s;
    }

    public void run() {
        PrintStream out = null;
        BufferedReader in = null;

        try {
            out = new PrintStream(sock.getOutputStream());
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            String request = in.readLine();

            System.out.println("DIAdb: request sent:" + request);
            StringTokenizer input = new StringTokenizer(request, "&");

            String type = input.nextToken();

            String retVal = "";
            if (type.contains("STORE")) {
                if (Data.addData(input.nextToken(), input.nextToken())) {
                    retVal = "ACCEPTED";
                } else {
                    retVal = "NOTACCEPTED";
                }

            } else if (type.contains("GET")) {
                List<String> data = Data.getList(input.nextToken());
                retVal = "DATA&";
                for (String s : data) {
                    retVal = retVal + s + "&";
                }
            }

            System.out.println("DIAdb: response sent: " + retVal);
            out.println(retVal);
            out.flush();




        } catch (IOException e) {
            System.out.println("DB: Problem reading / writing to the database.");
        }
    }
}

class Data {

    /*
     * This is my immplentation of a thread safe data structure list to store each groups
     * data seperately.
     */
    private static volatile List<String> group567893 = Collections.synchronizedList(new ArrayList<String>());
    private static volatile List<String> group00FF00 = Collections.synchronizedList(new ArrayList<String>());
    private static volatile List<String> groupABCDEF = Collections.synchronizedList(new ArrayList<String>());

    static boolean addData(String groupName, String data) {
        if (groupName.equals("ABCDEF")) {
            groupABCDEF.add(data);
            return true;
        } else if (groupName.equals("567893")) {
            group567893.add(data);
            return true;
        } else if (groupName.equals("00FF00")) {
            group00FF00.add(data);
            return true;
        } else {
            return false;
        }
    }

    static void removeData(String groupName, String data) {
        if (groupName.equals("ABCDEF")) {
            groupABCDEF.remove(data);
        } else if (groupName.equals("567893")) {
            group567893.remove(data);
        } else if (groupName.equals("00FF00")) {
            group00FF00.remove(data);
        }
    }

    static List<String> getList(String groupId) {
        if (groupId.equals("ABCDEF")) {
            return groupABCDEF;
        } else if (groupId.equals("567893")) {
            return group567893;
        } else if (groupId.equals("00FF00")) {
            return group00FF00;
        }
        return null;
    }
}
