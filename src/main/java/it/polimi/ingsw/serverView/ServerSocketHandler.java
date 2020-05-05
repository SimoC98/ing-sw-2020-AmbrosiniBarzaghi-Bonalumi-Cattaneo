package it.polimi.ingsw.serverView;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.events.serverToClient.ServerEvent;
import it.polimi.ingsw.events.clientToServer.ClientEvent;

import java.io.*;
import java.net.Socket;

public class ServerSocketHandler extends Observable<ClientEvent> implements Runnable {

    private Socket socket;
    private Server server;
    private boolean isGameStart;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ServerSocketHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        isGameStart = false;

        try {
            in = new ObjectInputStream(socket.getInputStream());
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
                notify(event);
            }
        }catch (Exception e) {
            //
        }
    }

    public void sendEvent(ServerEvent event) {
        try {
            out.writeObject(event);
            out.flush();
        } catch (IOException e) {
            //
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
            //server.deregisterConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
