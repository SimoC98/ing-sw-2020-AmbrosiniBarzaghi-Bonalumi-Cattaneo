package it.polimi.ingsw.serverView;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.events.clientToServer.LoginEvent;
import it.polimi.ingsw.events.serverToClient.ServerEvent;
import it.polimi.ingsw.events.clientToServer.ClientEvent;

import java.io.*;
import java.net.Socket;

/**
 * Helps to manage each client connection, pairing it with the server. It holds the input stream to
 * receive {@link ClientEvent} and holds an output stream to send {@link ServerEvent}
 */
public class ServerSocketHandler extends Observable<ClientEvent> implements Runnable {

    //sync between instances
    private static final Object lock = new Object();

    private Socket socket;
    private Server server;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    //maybe reference is not necessary
    private PingManager sender;


    /**
     * Opens a channel between the client and the server and start the pinging process ({@link PingManager}
     * @param socket client connection
     * @param server server connection
     */
    public ServerSocketHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        sender = new PingManager(this);

        try {
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            //e.printStackTrace();
            e.getMessage();
        }
    }

    /**
     * The thread catches the events coming from the client to communicate to the server.
     * If the event is a {@link LoginEvent}, a method is called
     */
    public void run() {
        //
        try {
            while(true) {
                ClientEvent event = (ClientEvent) in.readObject();
                //if(event instanceof Pong) System.out.print("pong received");
                if(event instanceof LoginEvent) {
                    LoginEvent presentation = (LoginEvent) event;
                    login(presentation);
                }
                else notify(event);
            }
        }catch (Exception e) {
            //e.printStackTrace();
            e.getMessage();
            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException ioException) {
                //ioException.printStackTrace();
            }
        }
    }

    /**
     * Sends an event to the client
     * @param event {@link ServerEvent}
     */
    public void sendEvent(ServerEvent event) {
        try {
            out.writeObject(event);
            out.flush();
        } catch (Exception e) {
            e.getMessage();
            //e.printStackTrace();
        }
    }

    /**
     * Closes the streams, the socket and closes the connection  on the server
     */
    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            //e.printStackTrace();
            e.getMessage();
        } finally {
            server.unregisterConnection(this);
            stopPing();
        }
    }

    /**
     * Logs the user on the server
     * @param event Valid {@link LoginEvent}
     */
    private void login(LoginEvent event) {
        synchronized (lock) {
            server.loginUser(event.getUsername(),this);
        }
    }

    /**
     * Disconnects a client if the game has not started yet;
     * disconnects every client if the game has already begun.
     */
    protected void disconnect() {
        System.out.println("disconnection");
        synchronized (lock) {
            if(server.isGameStarted()) {
                server.disconnectAll(this);
            }
            else {
                server.unregisterConnection(this);
            }
        }
    }

    /**
     * Starts {@link PingManager}
     */
    public void startPing() {
        sender.startPing();
    }

    /**
     * Stops {@link PingManager}
     */
    public void stopPing() {
        sender.stop();
    }

}