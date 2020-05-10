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

    public ServerSocketHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;

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
            server.deregisterConnection(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startPing() {
        /*timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(pingCounter==3) disconnect();
                else {
                    pingCounter++;
                    sendEvent(new Ping());
                }
            }
        },0,5000);*/
        PingSender ping = new PingSender(this);
        ping.startPing();
    }

    private void login(LoginEvent event) {
        synchronized (server) {
            server.loginUser(event.getPlayerNumber(),event.getUsername(),this);
        }
    }

    protected void disconnect() {
        synchronized (server) {
            if(server.isGameStarted()) {
                //notify to virtual view disconnection event --> the game will end
            }
            else {
                close();
            }
        }
    }

}
