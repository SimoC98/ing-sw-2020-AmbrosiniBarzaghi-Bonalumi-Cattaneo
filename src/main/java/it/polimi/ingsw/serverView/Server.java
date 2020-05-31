package it.polimi.ingsw.serverView;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.events.serverToClient.*;
import it.polimi.ingsw.model.Match;

import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.exit;


/*
Following tutor Michele Bertoni's tips:
-server accepts connections without blocking itself asking to the first user the players number to start the match
-server start the match as soon as 3 users connects
-when 2 users are connected, a timer is scheduled ---> after XX seconds the match starts with 2 players
-if 2 players are in the waiting room and one of them disconnect himself, timer is deleted
 */

/**
 * Server contains the main to create a server that manages the clients
 */
public class Server{
    private int port;
    private ExecutorService executor;
    private List<ServerSocketHandler> connections;
    private int playerId;
    private int playerGameNumber;
    private boolean isGameStarted;
    private Map<ServerSocketHandler,String> loggedPlayers;

    private Timer timer;



    public Server(int port){
        this.port = port;
        executor = Executors.newCachedThreadPool();
        connections = new ArrayList<>();
        loggedPlayers = new HashMap<>();
        isGameStarted=false;
        playerId = 0;
        playerGameNumber = -1;

        timer = new Timer();
    }

    /**
     * Prepares the server to listen to clients and to accept their connection increasing their id
     */
    public void startServer() {
        ServerSocket serverSocket = null;
        try {
           serverSocket = new ServerSocket(port);
           while(true) {
               Socket socket = serverSocket.accept();
               System.out.println("accepted" + socket.getInetAddress());
               ServerSocketHandler connection = new ServerSocketHandler(socket,this);
               executor.submit(connection);
               System.out.println("player id" + playerId);
               connection.sendEvent(new WelcomeEvent(playerId));
               playerId++;

           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public synchronized void registerConnection(ServerSocketHandler connection) {
        connections.add(connection);
    }

    public synchronized void unregisterConnection(ServerSocketHandler connection) {
        if(!isGameStarted && loggedPlayers.size()==2) {
            timer.cancel();
        }

        connections.remove(connection);
        if(loggedPlayers.keySet().contains(connection)) {
            loggedPlayers.remove(connection);
        }
        printUsers();
    }

    public synchronized boolean isLobbyFull()  {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (isGameStarted);
    }

    /**
     * Manages a user's connection: if the lobby is not full and the submitted username has not
     * been already take, the client is accepted into the lobby
     * <p>
     * We decided, after consulting the tutor, that the lobby holds up to 3 players; once 2 players have arrived
     * a timer is scheduled and if it expires before the third player arrives, the match begins. If the lobby fills with
     * 3 players the match starts
     * @param username client's name
     * @param connection client's connection
     */
    public synchronized void loginUser(String username, ServerSocketHandler connection) {
        if(isLobbyFull()) {
            System.out.println("lobby full");
            connection.sendEvent(new LobbyFullEvent());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connection.close();
            return;
        }
        else if(loggedPlayers.values().contains(username)) {
            List<String> loggedUsernames = new ArrayList<>(loggedPlayers.values());
            connection.sendEvent(new InvalidUsernameEvent(loggedUsernames));
            System.out.print("USERNAME NOT AVAILABLE");
            return;
        }

        registerConnection(connection);

        connection.sendEvent(new InLobbyEvent());

        loggedPlayers.put(connection,username);
        connection.startPing();
        printUsers();


        if(loggedPlayers.keySet().size()==3) {
            timer.cancel();
            startMatch();
        }
        else if(loggedPlayers.keySet().size()==2) {
            timer = new Timer();
            new Thread(()-> {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        startMatch();
                    }
                },30000);
            }).start();


        }
        /*else {
            connection.sendEvent(new WaitingRoomEvent());
        }*/
    }

    /**
     * Start the game saving the current players
     */
    private void startMatch() {
        System.out.println("GAME START\n");
        isGameStarted=true;

        List<ServerView> users = new ArrayList<>();
        List<String> players = new ArrayList<>();

        for(ServerSocketHandler s : connections) {
            ServerView newUser = new ServerView(loggedPlayers.get(s), s);
            players.add(loggedPlayers.get(s));
            users.add(newUser);
        }


        //Match match = new Match(new ArrayList<>(loggedPlayers.values()));
        Match match = new Match(players);
        Controller controller = new Controller(match,users);

        for(ServerView s : users) {
            s.addObserver(controller);
            match.addObserver(s);
        }

        controller.startGame(new ArrayList<String>());
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

    /**
     * Disconnects users after a user disconnects or there is a winner
     * @param connection
     */
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
