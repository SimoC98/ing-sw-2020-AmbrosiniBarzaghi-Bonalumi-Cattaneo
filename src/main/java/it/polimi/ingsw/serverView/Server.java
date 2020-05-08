package it.polimi.ingsw.serverView;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.events.serverToClient.InvalidUsernameEvent;
import it.polimi.ingsw.events.serverToClient.LobbyFullEvent;
import it.polimi.ingsw.events.serverToClient.PresentationEvent;
import it.polimi.ingsw.model.Match;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
               ServerSocketHandler connection = new ServerSocketHandler(socket,this);
               executor.submit(connection);
               registerConnection(connection);

               if(playerId==0) {
                   connection.sendEvent(new PresentationEvent(playerId));
                   int cont=0;
                   while(true) {
                       Thread.sleep(1000);
                       cont++;
                       if(playerGameNumber>0 || cont==60) break;
                   }
                   if(cont==60) {
                       connection.close();
                       continue;
                   }
                   playerId++;
                   connection.startPing();
               }
               else if(isLobbyFull()) {
                   connection.sendEvent(new LobbyFullEvent());
                   connection.close();
               }
               else {
                   connection.sendEvent(new PresentationEvent(playerId));
                   playerId++;
                   connection.startPing();
               }
           }
        } catch (Exception e) {
            //
        }




        /*ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int i = 0;
        while(true){
            System.out.println("while(true), just me and you");
            try{
                Socket clientSock = serverSocket.accept();
                System.out.println("Here comes the client #" + i + "...");
                i++;
                executor.submit(new ServerSocketHandler(clientSock,this));
                System.out.println("#" + i + "managed!");
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        executor.shutdown();*/
    }

    public synchronized void registerConnection(ServerSocketHandler connection) {
        connections.add(connection);
    }

    public synchronized void deregisterConnection(ServerSocketHandler connection) {
        connections.remove(connection);
        if(loggedPlayers.keySet().contains(connection)) {
            loggedPlayers.remove(connection);
        }
    }

    public synchronized boolean isLobbyFull() throws InterruptedException {
        Thread.sleep(1000);
        return (playerGameNumber>0 && connections.size()==playerGameNumber);
    }

    public synchronized void loginUser(int playerNumber, String username, ServerSocketHandler connection) {
        if(playerNumber>0) playerGameNumber = playerNumber;
        else if(loggedPlayers.containsKey(username)) {
            List<String> loggedUsernames = new ArrayList<>(loggedPlayers.values());
            connection.sendEvent(new InvalidUsernameEvent(loggedUsernames));
        }
        loggedPlayers.put(connection,username);
        if(loggedPlayers.size()==playerNumber) {
            isGameStarted=true;

            List<ServerView> users = new ArrayList<>();
            for(ServerSocketHandler s : connections) {
                ServerView newUser = new ServerView(loggedPlayers.get(s), s);
                users.add(newUser);
            }

            Match match = new Match((List<String>)loggedPlayers.values());
            Controller controller = new Controller(match,users);

            for(ServerView s : users) {
                s.addObserver(controller);
                match.addObserver(s);
            }

            //controller.startGame();

        }
    }

    public boolean isGameStarted() {
        return isGameStarted;
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
