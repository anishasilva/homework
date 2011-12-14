/*--------------------------------------------------------

1. Raymond Elward / 2/25/2011

2.
 *
 * javac NameServer.java
 * java NameServer
 *
 * default port is set to 48050


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
import java.util.*;

/**
 *
 * @author rayelward
 */
public class NameServer {

    public static void main(String args[]) throws IOException {
        int q_len = 6;
        int comPort = 48050;
        Socket com;


        System.out.println("NS: in NameServer Thread");

        /*
         * Starts thread for service communication.
         */
        ServiceLooper SL = new ServiceLooper();
        Thread t = new Thread(SL);
        t.start();

        /*
         * starts public display
         */
        ServerSocket comSock = new ServerSocket(comPort, q_len);

        while (true) {
            com = comSock.accept();
            new ComWorker(com).start();
        }
    }
}

class ComWorker extends Thread {

    Socket sock;

    ComWorker(Socket s) {
        sock = s;
    }

    public void run() {
        System.out.println("NS: in communication NameServer worker!");

        PrintStream out;
        BufferedReader in;

        try {
            out = new PrintStream(sock.getOutputStream());
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            try {
                String input;
                String filename;
                //getting input from the browser.
                input = in.readLine();

                //parses the first line of input to get the name of the file requested.
                StringTokenizer request = new StringTokenizer(input, " ");
                if (request.nextToken().equals("GET") && request.hasMoreElements()) {
                    filename = request.nextToken();
                } else {
                    throw new FileNotFoundException();
                }

                if (filename.endsWith("/")) {
                    nameServerHTML(out, filename);
                }


            } catch (FileNotFoundException e) {
            }

        } catch (IOException e) {
            System.out.println("NS: problem serving HTML to user displaying info");
        }


    }

    private void nameServerHTML(PrintStream out, String filename) throws IOException {
        String fileN = "tempnameserver" + (Math.random() * 8000) + ".html";
        BufferedWriter tempFile = new BufferedWriter(new FileWriter(fileN));
        StringBuilder build = new StringBuilder();

        build.append("<html>\n");
        build.append("<head></head>\n");
        build.append("<body>\n");
        build.append("<center> <H1> Agent Name Server </H1></center>\n");
        build.append("<h2> Agents</h2>\n");
        build.append("<table style=\"background-color:white\" cellspacing=\"0\" \ncellpadding=\"3\"\n");
        build.append("border=\"1\" width=\"100%\">\n");
        build.append("<tr>\n");
        build.append("<th width=\"15%\"> Agent Name and Hot link </th>\n");
        build.append("<th width=\"15%\">Current Location</th>\n");
        build.append("<th width=\"15%\">Group</th>\n");
        build.append("<th width=\"15%\">Group ID </th>\n");
        //build.append("<th width=\"10%\">Kill the Agent</th>\n");
        build.append("</tr>\n");

        List<AgentInfo> agents = AgentInfoList.getList();
        for (int i = 0; i < agents.size(); i++) {
            build.append("<tr>\n");
            build.append("<td> <a href=\"http://").append(agents.get(i).getIp()).append(":").append(agents.get(i).getPort()).append("\">").append(agents.get(i).getName()).append("</a></td>\n");
            build.append("<td> IP: ").append(agents.get(i).getIp()).append(" Port: ").append(agents.get(i).getPort()).append("</td>\n");
            build.append("<td bgcolor=\"#").append(agents.get(i).getGroupId()).append("\"></td>\n");
            build.append("<td>#").append(agents.get(i).getGroupId()).append("</td>\n");
            //build.append("<td> <a href=\"http://127.0.0.1:4570/dienow\"> Kill! </a> </td>\n");
            build.append("</tr>\n");
        }
        build.append("</table>\n");
        build.append("<hr>\n");
        build.append("<h2>HostServers</h2>\n");
        build.append("<table style=\"background-color:white\" cellspacing=\"0\" \ncellpadding=\"3\"\n");
        build.append("border=\"1\" width=\"100%\">\n");
        build.append("<tr>\n");
        build.append("<th width=\"15%\"> HostServer Location </th>\n");
        build.append("<th width=\"15%\"> Other Information? </th>\n");
        build.append("</tr>\n");

        List<HostInfo> hosts = HostInfoList.getList();
        //insert for loop to display each host's
        for (int i = 0; i < hosts.size(); i++) {
            build.append("<tr>\n");
            build.append("<td> <a href=\"http://").append(hosts.get(i).getIp()).append(":").append(hosts.get(i).getPort()).append("/\"> IP: ").append(hosts.get(i).getIp()).append(" Port: ").append(hosts.get(i).getPort()).append(" </a> </td>\n");
            build.append("<td> Now hosting: ").append(hosts.get(i).getNumAgents()).append(" agents </td>\n");
            build.append("</tr>\n");
        }
        build.append("</table>\n");
        build.append("</body>\n");
        build.append("</html>\n");


        tempFile.write(build.toString());
        tempFile.flush();
        File tempToDelete = new File(fileN);
        long contentLength = tempToDelete.length();
        tempFile.close();

        InputStream file = new FileInputStream(fileN);

        out.print("HTTP/1.1 200 OK\r\n"
                + "Content-Length: " + contentLength
                + "\r\nContent-Type: text/html\r\n\r\n");
        //reads from the newly created file out to the user.
        System.out.println("NS: Sending list of agents and hosts to browser.");
        sendFile(out, file);
        //closes the file stream.
        file.close();
        //deletes the temp file dynamically created for the user.
        tempToDelete.delete();

    }

    void sendFile(PrintStream out, InputStream file) {
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
/*
 * service places and stores data to pass between the HostServer or Agent
 * and NameServer
 */

class ServiceLooper extends Thread {

    public void run() {
        int q_len = 6;
        int servPort = 48060;
        Socket serv;
        try {
            ServerSocket servSock = new ServerSocket(servPort, q_len);


            while (true) {
                serv = servSock.accept();
                new ServWorker(serv).start();
            }
        } catch (IOException e) {
            System.out.println("NS: problem with service connection on NameServer");
        }
    }
}

class ServWorker extends Thread {

    Socket serv;

    ServWorker(Socket s) {
        this.serv = s;
    }
    /*
     * handles request from the hostserver / agent.
     */

    public void run() {
        System.out.println("NS: in service NameServer worker!");

        PrintStream out;
        BufferedReader in;

        try {
            out = new PrintStream(serv.getOutputStream());
            in = new BufferedReader(new InputStreamReader(serv.getInputStream()));

            String input = in.readLine();
            StringTokenizer parsedIn = new StringTokenizer(input, "&");

            
            //if the message sent is a host registering, take the info and put it in
            //the host info list.
            if (input.contains("HOST:")) {
                parsedIn.nextToken();
                String ip = parsedIn.nextToken();
                int port = Integer.parseInt(parsedIn.nextToken());
                HostInfo newHost = new HostInfo(ip, port);
                HostInfoList.add(newHost);

                out.println("Host registered with NameServer: " + newHost.toString());
                out.flush();

            } else if (input.startsWith("NEWAGENT:")) {
                //register agent with its ID number... assign it a name
                //give all the names of group members
                parsedIn.nextToken();
                AgentInfo agent = newAgent(parsedIn);

                out.println("NEWAGENT:&" + agent.getName() + "&" + agent.getGroupId());
                out.flush();


            } else if (input.startsWith("AGENTMIGRATE:")) {
                /*
                 * updates info on the agent.
                 */
                 parsedIn.nextToken();
                 String agentId = parsedIn.nextToken();
                 AgentInfo agent = null;
                 List<AgentInfo> agentList = AgentInfoList.getList();
                 for (AgentInfo a : agentList){
                     if (a.agentId.equals(agentId))
                         agent = a;
                 }

                 //agent.getCurLocation().setNumAgents(agent.getCurLocation().getNumAgents() - 1);
                 agent.getCurLocation().incNumAgents();
                 HostInfo newHost = HostInfoList.getRandomHost();
                 agent.setCurLocation(newHost);
                 //newHost.setNumAgents(newHost.getNumAgents() + 1);
                 newHost.incNumAgents();

                 out.println("AGENTMIGRATE:&" + newHost.getIp() + "&" + newHost.getPort());
                 out.flush();

                 

                 
                
            } else if (input.startsWith("REAGENT:")){
                System.out.println("NS: receiving: ");
                parsedIn.nextToken();
                String agentId = parsedIn.nextToken();
                int newPort = Integer.parseInt(parsedIn.nextToken());
                List<AgentInfo> agentList = AgentInfoList.getList();
                for (AgentInfo a : agentList){
                    if (a.agentId.equals(agentId))
                        a.setPort(newPort);
                }
                
            }
            else {
                System.out.println("NS: Agent or HostServer requesting unimplemented action from service port.");
            }


        } catch (IOException e) {
            System.out.println("NS: issue with connection in service NameServer worker!");
        }


    }

    private static AgentInfo newAgent(StringTokenizer in) {
        /*
         * This function gets a random name and group ID for a registering
         * agent, then returns the name and port of all the ports its communicating with.
         */
        AgentInfo agent = new AgentInfo();
        agent.setGroupId(Factory.getRandomGroup());
        agent.setName(Factory.getRandomAgentName());
        String agentId = in.nextToken();
        String ip = in.nextToken();
        int port = Integer.parseInt(in.nextToken());
        int agentPort = Integer.parseInt(in.nextToken());
        agent.setAgentId(agentId);
        agent.setCurLocation(new HostInfo(ip, port));
        agent.setPort(agentPort);
        AgentInfoList.add(agent);

        /*
         * adds to the tally of how many agents are using the host.
         */
        List<HostInfo> hosts = HostInfoList.getList();

        for (HostInfo h : hosts) {
            if (h.equals(agent.getCurLocation())) {
                h.incNumAgents();
            }
        }

        return agent;


    }
}

class HostInfo implements Serializable {

    private int numAgents;
    private String ip;
    private int port;
    private volatile static List<AgentInfo> _agents = Collections.synchronizedList(new ArrayList<AgentInfo>());

    static boolean add(AgentInfo agentInfo) {
        return _agents.add(agentInfo);
    }

    static List<AgentInfo> getList() {
        return _agents;
    }

    HostInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.numAgents = 0;
    }

    String getIp() {
        return ip;
    }

    int getPort() {
        return port;
    }
    public void incNumAgents(){
        numAgents++;
    }
    public void decNumAgents(){
        numAgents--;
    }

    public int getNumAgents() {
        return numAgents;
    }

    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.ip != null ? this.ip.hashCode() : 0);
        hash = 53 * hash + this.port;
        return hash;
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof HostInfo)) {
            throw new IllegalArgumentException();
        }
        return (this.port == ((HostInfo) that).port && this.ip.equals(((HostInfo) that).ip));
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("IP: ").append(getIp()).append(" Port: ").append(getPort());
        return build.toString();
    }
}

class AgentInfo implements Serializable {

    String agentId;
    String groupId;
    String name;
    HostInfo curLocation;
    int port;

    public AgentInfo(String groupId, String name, HostInfo curLocation) {
        this.groupId = groupId;
        this.name = name;
        this.curLocation = curLocation;
    }

    AgentInfo() {
    }

    public HostInfo getCurLocation() {
        return curLocation;
    }

    public void setCurLocation(HostInfo curLocation) {
        this.curLocation = curLocation;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AgentInfo other = (AgentInfo) obj;
        if ((this.groupId == null) ? (other.groupId != null) : !this.groupId.equals(other.groupId)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.curLocation != other.curLocation && (this.curLocation == null || !this.curLocation.equals(other.curLocation))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.groupId != null ? this.groupId.hashCode() : 0);
        hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 59 * hash + (this.curLocation != null ? this.curLocation.hashCode() : 0);
        return hash;
    }

    String getIp() {
        return curLocation.getIp();
    }
}
/*
 * static list to of all the hosts connected to the system
 */

class HostInfoList {

    private volatile static List<HostInfo> _hosts = Collections.synchronizedList(new ArrayList<HostInfo>());

    static HostInfo getRandomHost(){
        int ranHost = (int) (_hosts.size() * Math.random());
        return _hosts.get(ranHost);
    }
    static boolean add(HostInfo hostInfo) {
        return _hosts.add(hostInfo);
    }

    static HostInfo randomHost() {
        int pick = (int) (Math.random() * _hosts.size());
        return _hosts.get(pick);
    }

    static boolean remove(HostInfo hostInfo) {
        return _hosts.remove(hostInfo);
    }

    static List<HostInfo> getList() {
        return _hosts;
    }
}
/*
 * static list of all the agents in the system
 */

class AgentInfoList {

    private volatile static List<AgentInfo> _agents = Collections.synchronizedList(new ArrayList<AgentInfo>());

    static boolean add(AgentInfo agentInfo) {
        return _agents.add(agentInfo);
    }

    static List<AgentInfo> getList() {
        return _agents;
    }
}

class Factory {
    /*
     * static function to get a random group Id
     */

    private static volatile List<String> _names = Collections.synchronizedList(new ArrayList<String>());
    private static volatile int count = 0;

    static String getRandomGroup() {
        String groupIds[] = new String[3];
        groupIds[0] = "ABCDEF";
        groupIds[1] = "00FF00";
        groupIds[2] = "567893";
        return groupIds[((int) (Math.random() * 3))];
    }
    /*
     * static function to get a name randomly
     */

    static String getRandomAgentName() {

        if (_names.isEmpty() && count == 0) {
            initializeNames();
            count++;
        }
        int i = (int) (Math.random() * _names.size());
        String name = "";
        try {
            name = _names.remove(i);
        } catch (Exception e) {
            System.out.println("NS Error: No more names to hand out.");
        }

        return name;
    }

    /*
     * if we're all out of names this will send us back a newly initiallized list 
     * of the names.  Initializes with 10 random names.
     */
    private static void initializeNames() {
        _names.add("Raymond");
        _names.add("Mia");
        _names.add("Maximus");
        _names.add("Thomas");
        _names.add("Candice");

        _names.add("Maggie");
        _names.add("Grant");
        _names.add("Joni");
        _names.add("Shirley");
        _names.add("Alaska");

        _names.add("Alice");
        _names.add("Bob");
        _names.add("Clark");
        _names.add("Bill");
        _names.add("Mike");

        _names.add("Sara");
        _names.add("Jim");
        _names.add("Laura");
        _names.add("Steve");
        _names.add("Chris");
    }
}
