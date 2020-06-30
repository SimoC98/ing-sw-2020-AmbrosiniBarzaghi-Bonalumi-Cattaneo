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
 * Server contains the main to create a server that manages the clients, allowing them to communicate and playing a game.
 * <p>
 * Following the tutor's tips:
 * <ul><li>-The server accepts connections without blocking itself  and asking to the first user the players number to start the match</li>
 * <li>-The server start the match as soon as 3 users connects</li>
 * <li>-When 2 users are connected, a timer is scheduled: after XX seconds the match starts with 2 players</li>
 * <li>-If 2 players are in the waiting room and one of them disconnects, the timer is deleted </li></ul>
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
     * Prepares the server to listen to clients and to accept their connection increasing their id.
     * It also creates a {@link ServerSocketHandler} for each client and sends them a {@link WelcomeEvent}
     */
    public void startServer() {
        System.out.println("I. AM. ALIVE. ON " + port); //ADDED FOR MULTI_SERVER
        ServerSocket serverSocket = null;
        try {
            try{
                serverSocket = new ServerSocket(port);
            } catch (Exception e) {
                System.out.println("\n\nPORT ALREADY IN USE!\n\n");
                exit(0);
            }
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


    /**
     * Adds a connection to the list of connections saved on the server
     * @param connection New user connection
     */
    public synchronized void registerConnection(ServerSocketHandler connection) {
        connections.add(connection);
    }

    /**
     * Removes the client's connection from the list of registered connections.
     * If the client disconnecting was the second one, the timer is reset so that the game can not
     * begin with only one player
     * @param connection Connection of the disconnecting client
     */
    public synchronized void unregisterConnection(ServerSocketHandler connection) {
        if(!isGameStarted && loggedPlayers.size()==2) {
            timer.cancel();
        }

        connections.remove(connection);
        if(loggedPlayers.keySet().contains(connection)) {
            loggedPlayers.remove(connection);
        }
        printUsers();

        if(isGameStarted && loggedPlayers.size()==0) {
            connections.clear();
            loggedPlayers.clear();
            isGameStarted=false;
        }
    }

    /**
     * Returns true if the game started after waiting for a certain time to make sure that the game is not starting when called.
     * @return {@code true} when the game start with two or three players
     */
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
     * been already take, the client is accepted into the lobby and a {@link InLobbyEvent} is sent.
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
            return;
        }
        else if(loggedPlayers.values().contains(username)) {
            List<String> loggedUsernames = new ArrayList<>(loggedPlayers.values());
            connection.sendEvent(new UsernameAlreadyUsed(loggedUsernames));
            System.out.print("USERNAME NOT AVAILABLE");
            return;
        }
        /*else if(username.length()<3 || username.length()>15 || username.contains(" ")) {
            connection.sendEvent(new InvalidUsernameEvent());
            return;
        }*/

        connection.sendEvent(new InLobbyEvent());
        registerConnection(connection);
        loggedPlayers.put(connection,username);
        printUsers();


        if(loggedPlayers.keySet().size()==3) {
            timer.cancel();
            startMatch();
            connection.startPing();
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
            connection.startPing();
        }
        else {
            connection.startPing();
        }
    }

    /**
     * Start the game saving the current players, initializing the due observer and observable classes and sending each
     * client a {@link GameStartEvent}
     */
    private void startMatch() {
        System.out.println("GAME START\n");
        //this.playerGameNumber = connections.size();
        isGameStarted=true;

        List<ServerView> users = new ArrayList<>();
        List<String> players = new ArrayList<>();

        for(ServerSocketHandler s : connections) {
            ServerView newUser = new ServerView(loggedPlayers.get(s), s);
            players.add(loggedPlayers.get(s));
            users.add(newUser);
        }

        Match match = new Match(players);
        Controller controller = new Controller(match,users);

        for(ServerView s : users) {
            s.addObserver(controller);
            match.addObserver(s);
        }

        connections.stream().forEach(x -> x.sendEvent(new GameStartEvent()));
        controller.startGame(new ArrayList<String>(),null);
    }

    /**
     * Prints the users to verify the correct functioning of the server and the sockets.
     */
    private void printUsers() {
        System.out.println("\nLOGGED PLAYERS: ");
        connections.stream().forEach(x -> System.out.println(loggedPlayers.get(x)));
        //loggedPlayers.values().forEach(x -> System.out.println(x));
        System.out.println("\n");
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    /**
     * Disconnects users after a user disconnects or there is a winner
     * @param connection Socket of the lost connection
     */
    protected void disconnectAll(ServerSocketHandler connection){
        System.out.println("user " + loggedPlayers.get(connection) + " disconnected... this match will end soon");

        for(int i=0; i<connections.size(); i++) {
            ServerSocketHandler s = connections.get(i);
            if(!s.equals(connection)) {
                s.sendEvent(new PlayerDisconnectionEvent(loggedPlayers.get(connection)));
                s.stopPing();
            }

        }


        System.out.println("\n\nthis match is ended... waiting for players\n\n\n");
        connections.clear();
        loggedPlayers.clear();
        isGameStarted=false;
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
            portNumber = Integer.parseInt(args[0]);
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
