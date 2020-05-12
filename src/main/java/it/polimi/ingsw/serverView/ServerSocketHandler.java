package it.polimi.ingsw.serverView;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.events.clientToServer.LoginEvent;
import it.polimi.ingsw.events.serverToClient.ServerEvent;
import it.polimi.ingsw.events.clientToServer.ClientEvent;

import java.io.*;
import java.net.Socket;

public class ServerSocketHandler extends Observable<ClientEvent> implements Runnable {
    private Socket socket;
    private Server server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private PingManager sender;

    public ServerSocketHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        sender = new PingManager(this);

        try {
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        //
        try {
            while(true) {
                ClientEvent event = (ClientEvent) in.readObject();
                if(event instanceof LoginEvent) {
                    LoginEvent presentation = (LoginEvent) event;
                    login(presentation);
                }
                else notify(event);
            }
        }catch (Exception e) {
            //
        }
    }


    public void sendEvent(ServerEvent event) {
        try {
            out.writeObject(event);
            out.flush();
        } catch (Exception e) {
            //System.out.println("client disconnected exceptioon");
            //disconnect();
        }
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
            server.deregisterConnection(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(LoginEvent event) {
        synchronized (server) {
            server.loginUser(event.getPlayerNumber(),event.getUsername(),this);
        }
    }

    protected void disconnect() {
        System.out.println("disconnection");
        synchronized (server) {
            if(server.isGameStarted()) {
                System.out.println("game already started...");
                //sender.stop();
                server.disconnectAll(this);
            }
            else {
                System.out.println("game not already started...");
                //sender.stop();
                server.deregisterConnection(this);
            }
        }
    }

}