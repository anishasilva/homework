/*--------------------------------------------------------

1. Raymond Elward / 2/25/2011

2.
 *
 * javac HostServer.java
 * java HostServer [port]
 *
 * default port is set to 45050


3.
 * instructions:
 *
 * Interact with agents and manage them as they move around web servers
 * use Grade agent batch file for it to work properly.


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

 * Use granding batch file!!!@!!
----------------------------------------------------------*/

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

///*
// * this class provides a new thread for each agent that and has a 20% chance of migrating
// * every 3000 sleep timer. very simple and N.V.I.
// */


class AgentWorker extends Thread { //Worker defined.

    Socket sock;
    AgentHolder parentAgentHolder;
    int localPort;
    //Constructor: assign args s, port to port and agentHolder to local vars:

    AgentWorker(Socket s, int port, AgentHolder ah) {
        sock = s;
        localPort = port;
        parentAgentHolder = ah;
        //new MigrateLoop(this).start();
    }

    public void run() {
        //initializing local varibles for run method of the worker.
        System.out.println("In AgentWorker Thread for Agent.");
        PrintStream out = null;
        BufferedReader in = null;
        String newHost = "localhost";
//        int newHostMainPort = HostServer.port;
//        String buf = "";
//        int newPort = 0;
//        Socket clientSock;
//        BufferedReader fromHostServer;
//        PrintStream toHostServer;
        try {
            out = new PrintStream(sock.getOutputStream());
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            String inLine = in.readLine();
            /*
             * the decision to MIGRATE should be decided autonomously BY THE AGENT.  Here, for
             * illustration, we just allow the web client to instigate the migration.  We also
             * just pick localhost for the destination host for the illustration, but this also
             * should be chosen, autonomously, by the agent.
             */
            System.out.println("HS: line sent to agent " + parentAgentHolder.name + " is " + inLine);
            if (inLine.indexOf("data=") > -1) {
                parentAgentHolder.agentState++; //has the state move up by 1
                String data = inLine.substring(inLine.indexOf("data=") + 5, inLine.indexOf(" H", inLine.indexOf("data=")));
                sendToDb(data, parentAgentHolder.groupId);
                sendHTML(localPort, newHost, inLine, out, parentAgentHolder);
            } else if (!inLine.contains("favicon.ico")) {
                parentAgentHolder.agentState++; //has the state move up by 1
                sendHTML(localPort, newHost, inLine, out, parentAgentHolder);
//                AgentListener.sendHTMLheader(localPort, newHost, inLine, out);
//                out.println("<h3> This is the agent state: " + parentAgentHolder.agentState + "</h3>");
                //AgentListener.sendHTMLsubmit(out);
            }
            int rand = ((int) (Math.random() * 5));
            System.out.println(rand);
            if ( rand == 2 ) //random migration upon gathering data.
            {
                System.out.println("Agent:" + parentAgentHolder.name + " is migrating.");
                migrate();
            }
            sock.close(); //closes the connection, but not the server.
        

        } catch (IOException ioe) {
            System.out.println(ioe);
            ioe.printStackTrace();
        }
    }

    void migrate() throws IOException {
        //connect to name server service port for a new host
        Socket s = new Socket("localhost", 48060);
        PrintStream out = new PrintStream(s.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        out.println("AGENTMIGRATE:&" + parentAgentHolder.agentId);
        out.flush();

        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(AgentWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        String inputFromNS = in.readLine();
        s.close();
        StringTokenizer inFromNS = new StringTokenizer(inputFromNS, "&");
        inFromNS.nextToken();


        String newHost = inFromNS.nextToken();
        int newHostMainPort = Integer.parseInt(inFromNS.nextToken());

        String buf = "";
        int newPort = 0;
        Socket clientSock;
        BufferedReader fromHostServer;
        PrintStream toHostServer;
        /*
         * select server, send server a request for hosting, get port back from server,
         * send HTML pointing to new server and port, kill parent AgentListener loop
         * die:
         */
        clientSock = new Socket(newHost, newHostMainPort);
        fromHostServer = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
        toHostServer = new PrintStream(clientSock.getOutputStream());
        toHostServer.println("MIGRATING:Agent&" + parentAgentHolder.agentId + "&" + parentAgentHolder.groupId + "&" + parentAgentHolder.name + "&" + parentAgentHolder.agentState);
        toHostServer.flush();
        /*
         * We really only needed the port.  The HTML was sent as convenience for starting
         * with an initial request to the HostServer from a web client:
         */
        while (true) {//we read until we find the port number...
            buf = fromHostServer.readLine();
            if (buf.indexOf("[Port=") > -1) {
                break;
            }
        }
        String tempBuf = buf.substring(buf.indexOf("[Port=") + 6, buf.indexOf("]", buf.indexOf("[Port=")));
        newPort = Integer.parseInt(tempBuf);
        System.out.println("HS: newPort for agent " + parentAgentHolder.name + " is:" + newPort);

        //Don't need to print everytime we migrate.
        //The new host is just faked here because its all run on localhost. agent would select:
        //sendHTML(localPort, newHost, inLine, out, parentAgentHolder);
        System.out.println(parentAgentHolder.name + " is Migrating to " + newHost + " " + newPort + "");
        //AgentListener.sendHTMLsubmit(out);
        System.out.println("killing parent listening loop.");
        ServerSocket ss = parentAgentHolder.sock;
        ss.close(); //closes the parent socket by reference.



        /*
         * these update the nameServer with the new port.
         */
        System.out.println("HS: agent " + parentAgentHolder.name + " is registering his/her new port with name server.");
        Socket soc = new Socket("localhost", 48060);
        PrintStream outt = new PrintStream(soc.getOutputStream());
        String agentIdLocal = parentAgentHolder.agentId;

        outt.println("REAGENT:&" + agentIdLocal + "&" + newPort + "&");
        System.out.println("HS: Agent " + parentAgentHolder.name + " registered new port.");
        outt.flush();
        s.close();
    }

    static void sendHTML(int localPort, String newHost, String inLine, PrintStream out, AgentHolder agent) throws IOException {
        /*
         * building dynamic HTML to a file first so i can get the content length and the browser doesnt hang
         */
        String filename = "tempAgentInteractinghtml" + (Math.random() * 500000) + ".html";
        BufferedWriter tempFile = new BufferedWriter(new FileWriter(filename));
        StringBuilder build = new StringBuilder();

        build.append("<html><head></head><body> ");
        build.append("<h2> You  are looking at agent: " + agent.name + ". PORT " + localPort + " on " + newHost + "</h2>");
        build.append("<h3> you sent: " + inLine + "</h3>");
        build.append("<h3> This agents state is:" + agent.agentState + "</h3>");
        build.append("<p>I am in group #" + agent.groupId + "</p>");
        build.append("<p>I may migrate at anytime. Please visit <a href=\"http://localhost:48050\"> NameServer </a> for my newest location because zombies are not implemented.</p>");


        build.append("<FORM method=\"GET\" action=\"http://" + newHost + ":" + localPort + "\">");
        //build.append("Now in Agent Looper starting Agent ListeningLoop\n<br>");
        //build.append("[Port=" + localPort + "]<br>");
        //build.append("<a href=\"http://localhost:"+localPort+"\"> Click to goto agent that you just spawned. </a>");
        build.append("Enter a String of data that you would like me to store and share with my group!:");
        build.append("<INPUT TYPE=\"text\" NAME=\"data\" size=20 value\"YourTextInput\"><p>");
        build.append("<INPUT TYPE=\"submit\" VALUE=\"Submit Text String\">");

        build.append("<p>Below is all the data my group has collected so far:<p>");

        ArrayList<String> data = getGroupData(agent.groupId);
        build.append("<p>" + data.toString() + "</p>");

        build.append("</form></body></html>");

        tempFile.write(build.toString());
        tempFile.flush();
        File tempToDelete = new File(filename);
        long contentLength = tempToDelete.length();
        tempFile.close();

        InputStream file = new FileInputStream(filename);

        out.print("HTTP/1.1 200 OK\r\n"
                + "Content-Length: " + contentLength
                + "\r\nContent-Type: text/html\r\n\r\n");
        //reads from the newly created file out to the user.
        System.out.println("HS: Sending a spawning agent screen to browser.");
        HostFactory.sendFile(out, file);
        //closes the file stream.
        file.close();
        //deletes the temp file dynamically created for the user.
        tempToDelete.delete();

    }

    /*
     * this simple function is used by the agent to get all the data that its group has collected.
     */
    private static ArrayList<String> getGroupData(String groupId) throws IOException {


        Socket sockDb = new Socket("localhost", 6010);
        PrintStream out = new PrintStream(sockDb.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(sockDb.getInputStream()));

        out.println("GET&" + groupId);
        out.flush();
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(AgentWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        String input = in.readLine();
        ArrayList<String> groupInfo = new ArrayList<String>();
        StringTokenizer detoken = new StringTokenizer(input, "&");
        if (detoken.nextToken().equalsIgnoreCase("DATA")) {
            while (detoken.hasMoreTokens()) {
                String what = detoken.nextToken();
                groupInfo.add(what);
            }
        }

        return groupInfo;
    }

    private void sendToDb(String data, String groupId) throws IOException {
        /*
         * sends data to be stored in the database for this agents group.
         */
        Socket sockDb = new Socket("localhost", 6010);
        PrintStream out = new PrintStream(sockDb.getOutputStream());

        out.println("STORE&" + groupId + "&" + data);
        out.flush();
        sockDb.close();
    }
}

class AgentHolder {

    ServerSocket sock; // pointer to the parent looping listener for the agent
    int agentState; //this could be animal data structure, but its just an int for example
    String agentId;// this is the identity of the agent.
    String groupId;
    String name;

    AgentHolder(ServerSocket s) {//constructor for creating instances.
        sock = s;
    }
}

class AgentListener extends Thread { //defines class

    Socket sock;  //class member, socket local to AgentListener class
    int localPort;
    //constructor, assigns args as, prt to local value:

    AgentListener(Socket s, int port) {
        sock = s;
        localPort = port;
    }
    int agentState = 0; // starts the state out as 0
    String agentId;// this is the identity of the agent.
    String groupId;
    String name;

    public void run() {
        BufferedReader in = null;
        PrintStream out = null;
        String newHost = "localhost"; //standard.  can be changed if wanting to do accross multiple machines

        try {

            String buf;
            ServerSocket servsock = null;
            AgentHolder agentHold = null;
            out = new PrintStream(sock.getOutputStream());
            //the I WANT TO COME connection:
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(AgentListener.class.getName()).log(Level.SEVERE, null, ex);
            }

            buf = in.readLine();
            System.out.println("SENT TO AGENTLISTENER: " + buf);


            if (buf.contains("GET")) {


                sendHTMLheader(localPort, newHost, buf, out);


                servsock = new ServerSocket(localPort, 2);
                agentHold = new AgentHolder(servsock);
                agentHold.agentState = agentState;
                agentHold.agentId = agentId;
                agentHold.groupId = groupId;
                agentHold.name = name;

                //if its a new agent it needs to register.
                if (agentId == null) {
                    registerWithNameServer(agentHold);
                }
            } else if (buf.contains("MIGRATING")) {
                StringTokenizer tokens = new StringTokenizer(buf, "&");
                tokens.nextToken();
                agentId = tokens.nextToken();
                groupId = tokens.nextToken();
                name = tokens.nextToken();
                agentState = Integer.parseInt(tokens.nextToken());
                agentHold = new AgentHolder(servsock);
                out.println("[Port=" + localPort + "]");
                out.flush();
                servsock = new ServerSocket(localPort, 2);
                agentHold.agentId = agentId;
                agentHold.groupId = groupId;
                agentHold.name = name;
                agentHold.agentState = agentState;


            }
            while (true) {
                //endless loop that catchs agent connections.
                sock = servsock.accept();
                System.out.println("Got a connection to agent at port " + localPort);
                AgentWorker work = new AgentWorker(sock, localPort, agentHold);
                work.start();

            }

        } catch (IOException ioe) {//handles io exception if the connection fails.
            System.out.println("Either connection failed, or just killed Listener Loop for agent at port " + localPort);
        }
    }

    /*
     * html header created on the fly for client (webBrowsers).
     */
    static void sendHTMLheader(int localPort, String newHost, String inLine, PrintStream out) throws IOException {
        /*
         * building dynamic HTML to a file first so i can get the content length and the browser doesnt hang
         */
        String filename = "tempAgentSpawninghtml" + (Math.random() * 500000) + ".html";
        BufferedWriter tempFile = new BufferedWriter(new FileWriter(filename));
        StringBuilder build = new StringBuilder();

        build.append("<html><head></head><body> ");
        build.append("<h2> You're using Ray's DIA at PORT " + localPort + " on " + newHost + "</h2>");
        build.append("<h3> you sent: " + inLine + "</h3>");
        build.append("<p><a href=\"http://localhost:" + localPort + "\"> Click here to goto the agents location that you just spawned </a></p>");
        build.append("<p><a href=\"http://localhost:48050\"> NameServer: lists all the hosts and agents out right now.</a></p>");
        build.append("</form></body></html>");

        tempFile.write(build.toString());
        tempFile.flush();
        File tempToDelete = new File(filename);
        long contentLength = tempToDelete.length();
        tempFile.close();

        InputStream file = new FileInputStream(filename);

        out.print("HTTP/1.1 200 OK\r\n"
                + "Content-Length: " + contentLength
                + "\r\nContent-Type: text/html\r\n\r\n");
        //reads from the newly created file out to the user.
        System.out.println("HS: Sending a spawning agent screen to browser.");
        HostFactory.sendFile(out, file);
        //closes the file stream.
        file.close();
        //deletes the temp file dynamically created for the user.
        tempToDelete.delete();

    }

    void registerWithNameServer(AgentHolder agentHold) throws IOException {
        /*
         * this takes a new spawning agent and registers it with the name server.
         */
        Socket s = new Socket("localhost", 48060);
        PrintStream out = new PrintStream(s.getOutputStream());
        agentHold.agentId = AgentId.getNewId();

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String agentIdLocal = agentHold.agentId;

        out.println("NEWAGENT:&" + agentIdLocal + "&localhost&" + HostServer.port + "&" + localPort);
        out.flush();
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(AgentListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        String input = in.readLine();
        System.out.println("HS: registered " + input + " with NameServer.");

        String nameLocal = null, groupIdLocal = null;
        StringTokenizer tokens = new StringTokenizer(input, "&");
        if (tokens.nextToken().equals("NEWAGENT:")) {
            nameLocal = tokens.nextToken();
            groupIdLocal = tokens.nextToken();
        }

        agentHold.groupId = groupIdLocal;
        agentHold.name = nameLocal;

        s.close();


    }
}

public class HostServer {

    public static int nextPort;
    public static int port;
    public final static int REGISTERPORT = 48060;
    public final static String NAMESERVER_IP = "localhost";
    public final static int AGENTMIGRATEPORT = 45678;

    public static void main(String args[]) throws IOException {

        String ip = "localhost";
        int q_len = 6; // number of requests allowed at a time.
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            port = 45050; //standard set port
        }
        //Starts thread for listening to migrations
        //Socket agentSock = new ServerSocket(AGENTMIGRATEPORT).accept();
        //new AgentReListener(agentSock, AGENTMIGRATEPORT).start();




        Socket sock;
        Socket registerSocket = new Socket(NAMESERVER_IP, REGISTERPORT);

        ServerSocket servsock = new ServerSocket(port, q_len);



        System.out.println("DIA Master receiver started at " + port + ".\n");

        //registers itself with name server.
        PrintStream out = new PrintStream(registerSocket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(registerSocket.getInputStream()));

        String register = "HOST:&" + ip + "&" + port;
        out.println(register);
        out.flush();
        System.out.println(in.readLine());

        registerSocket.close();


        while (true) {
            ServerSocket newPort = new ServerSocket(0);
            nextPort = newPort.getLocalPort();  //moves to a new port
            newPort.close();
            sock = servsock.accept();
            System.out.println("Starting an AgentListener at port " + nextPort);
            new AgentListener(sock, nextPort).start();
        }
    }
}

class AgentId {

    private volatile static int ID = 1000000000;

    static String getNewId() {
        ID++;
        return ("00" + Integer.toString(ID));
    }
}

class HostFactory {

    static void sendFile(PrintStream out, InputStream file) {
        try {
            byte[] buffer = new byte[2048];
            int i;
            while ((i = file.read(buffer)) > 0) {
                out.write(buffer, 0, i);
            }
            out.flush();
        } catch (IOException x) {
            System.out.println("NS: file trying to serve up is not found!");
        }
    }
}
