package it.polimi.ingsw.serverView;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.events.serverToClient.*;
import it.polimi.ingsw.model.Match;

import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class Server{
    private int port;
    private ExecutorService executor;
    private List<ServerSocketHandler> connections;
    private int playerId;
    private int playerGameNumber;
    private boolean isGameStarted;
    private Map<ServerSocketHandler,String> loggedPlayers;



    public Server(int port){
        this.port = port;
        executor = Executors.newCachedThreadPool();
        connections = new ArrayList<>();
        loggedPlayers = new HashMap<>();
        isGameStarted=false;
        playerId = 0;
        playerGameNumber = -1;
    }

    public void startServer() {
        ServerSocket serverSocket = null;
        try {
           serverSocket = new ServerSocket(port);
           while(true) {
               Socket socket = serverSocket.accept();
               System.out.println("accepted" + socket.getInetAddress());
               ServerSocketHandler connection = new ServerSocketHandler(socket,this);
               executor.submit(connection);
               registerConnection(connection);

               if(playerId==0) {
                   connection.sendEvent(new LoginRequestEvent(playerId));
                   int cont=0;
                   while(true) {
                       Thread.sleep(1000);
                       cont++;
                       if(playerGameNumber>0 || cont==60) break;
                   }
                   if(cont==60) {
                       connection.sendEvent(new Disconnect());
                       connection.close();
                       continue;
                   }
                   playerId++;
                   //registerConnection(connection);
                   //connection.startPing();
               }
               else if(isLobbyFull()) {
                   System.out.println("lobby full");
                   connection.sendEvent(new LobbyFullEvent());
                   connection.close();
               }
               else {
                   System.out.println("player id" + playerId);
                   connection.sendEvent(new LoginRequestEvent(playerId));
                   playerId++;
                   //registerConnection(connection);
                   //connection.startPing();
               }
           }
        } catch (Exception e) {
            //
        }
    }

    public synchronized void registerConnection(ServerSocketHandler connection) {
        connections.add(connection);
    }

    public synchronized void deregisterConnection(ServerSocketHandler connection) {
        connections.remove(connection);
        if(loggedPlayers.keySet().contains(connection)) {
            loggedPlayers.remove(connection);
        }
        printUsers();
    }

    public synchronized boolean isLobbyFull() throws InterruptedException {
        //Thread.sleep(1000);
        return (playerGameNumber!=-1 && connections.size()>playerGameNumber);
    }

    public synchronized void loginUser(int playerNumber, String username, ServerSocketHandler connection) {
        if(playerNumber>0) {
            playerGameNumber = playerNumber;
            System.out.println("player numbers chosen: " + playerNumber);
        }
        else if(loggedPlayers.values().contains(username)) {
            List<String> loggedUsernames = new ArrayList<>(loggedPlayers.values());
            connection.sendEvent(new InvalidUsernameEvent(loggedUsernames));
            System.out.print("USERNAME NOT AVAILABLE");
            return;
        }

        loggedPlayers.put(connection,username);
        printUsers();


        if(loggedPlayers.keySet().size()==playerGameNumber) {
            System.out.println("GAME START\n");
            isGameStarted=true;

            List<ServerView> users = new ArrayList<>();
            for(ServerSocketHandler s : connections) {
                ServerView newUser = new ServerView(loggedPlayers.get(s), s);
                users.add(newUser);
            }

            Match match = new Match(new ArrayList<>(loggedPlayers.values()));
            Controller controller = new Controller(match,users);

            for(ServerView s : users) {
                s.addObserver(controller);
                match.addObserver(s);
            }

            controller.startGame(new ArrayList<String>());

        }
    }

    private void printUsers() {
        System.out.println("\nLOGGED PLAYERS: ");
        /*for(ServerSocketHandler s : loggedPlayers.keySet()) {
            System.out.println(loggedPlayers.get(s));
        }*/
        loggedPlayers.values().forEach(x -> System.out.println(x));
        System.out.println("\n");
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    protected void disconnectAll(ServerSocketHandler connection){

        for(ServerSocketHandler s : connections) {
            s.sendEvent(new PlayerDisconnectionEvent(loggedPlayers.get(connection)));
        }

        System.out.println("GAME IS ENDED");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exit(0);
    }


    public static void main(String []args){
        int portNumber;
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if(inetAddress != null)
            System.out.println("Hello, I'm on: " + inetAddress.getHostAddress());
        else
            System.out.println("Hello, I'm on: 127.0.0.1");


        if(args.length >= 1) {
            portNumber = Integer.parseInt(args[1]);
        }
        else
        {
            //should be taken from mxl file
            portNumber = 4000;
        }

        Server server = new Server(portNumber);
        server.startServer();
    }
}
