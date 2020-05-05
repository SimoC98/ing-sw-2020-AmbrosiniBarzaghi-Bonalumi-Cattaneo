package it.polimi.ingsw.serverView;

import it.polimi.ingsw.events.serverToClient.InvalidUsernameEvent;
import it.polimi.ingsw.events.serverToClient.LobbyFullEvent;
import it.polimi.ingsw.events.serverToClient.PresentationEvent;

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
    private Map<String, ServerSocketHandler> loggedPlayers;

    public Server(int port){
        this.port = port;
        executor = Executors.newCachedThreadPool();
        connections = new ArrayList<>();
        loggedPlayers = new HashMap<>();
        playerId = 0;
        playerGameNumber = -1;
    }

    public Map<String, ServerSocketHandler> getLoggedPlayers() {
        return loggedPlayers;
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
                   while(true) {
                       Thread.sleep(100);
                       if(playerGameNumber>0) break;
                   }
               }
               else if(isLobbyFull()) {
                   connection.sendEvent(new LobbyFullEvent());
                   connection.close();
               }
               else {
                   connection.sendEvent(new PresentationEvent(playerId));
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
    }

    public synchronized boolean isLobbyFull() {
        return (playerGameNumber>0 && connections.size()==playerGameNumber);
    }

    public synchronized void loginUser(int playerNumber, String username, ServerSocketHandler connection) {
        if(playerNumber>0) playerGameNumber = playerNumber;
        else if(loggedPlayers.containsKey(username)) {
            List<String> loggedUsernames = new ArrayList<>(loggedPlayers.keySet());
            connection.sendEvent(new InvalidUsernameEvent(loggedUsernames));
        }
        loggedPlayers.put(username,connection);
        if(loggedPlayers.size()==playerNumber) {
            //startGame
        }
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
