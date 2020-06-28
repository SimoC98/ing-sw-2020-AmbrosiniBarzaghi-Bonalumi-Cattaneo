package it.polimi.ingsw.clientView;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.events.clientToServer.ClientEvent;
import it.polimi.ingsw.events.serverToClient.ServerEvent;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * ClientSocketHandler manages a client's connection receiving holding an input stream and an output one
 * to receive {@link ServerEvent} and to send {@link ClientEvent}.
 */
public class ClientSocketHandler extends Observable<ServerEvent> implements Runnable {

    Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private PingReceiver pinger;

    private Object lock = new Object();

    /**
     * Assigns the client socket passed as parameters, it opens the streams and it starts the pong process ({@link PingReceiver}
     * @param socket
     */
    public ClientSocketHandler(Socket socket) {
        this.socket = socket;

        ObjectInputStream tempin = null;
        ObjectOutputStream tempout = null;

        try {
            tempout = new ObjectOutputStream(socket.getOutputStream());
            tempin = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            //e.printStackTrace();
        }

        this.in = tempin;
        this.out = tempout;

        pinger = new PingReceiver(this);
    }


    /**
     * Thread to remain open to accept server events
     */
    @Override
    public void run() {
        System.out.println("running...");

        try {
            while (true) {
                ServerEvent event = (ServerEvent) in.readObject();

                Thread t = new Thread(() -> {
                    receiveEvent(event);
                });
                t.setDaemon(false);
                t.setPriority(Thread.MAX_PRIORITY);
                t.start();
            }
        }catch(SocketException se){
            //System.out.println("SOCKET CLOSED, NO PROBLEM");
            //se.printStackTrace();
            //System.out.println("SOCKET CLOSED, NO PROBLEM");
        } catch(Exception e) {
            //notify(new Disconnect());
            //e.printStackTrace();
        }


    }

    /**
     * Called whenever a new server event is received; it notifies the client view of such event through {@link ClientView#update(ServerEvent)}.
     * @param event
     */
    private void receiveEvent(ServerEvent event) {
        notify(event);
    }

    /**
     * Closes the socket after closing the streams.
     */
    public void close() {
        try {
           in.close();
           out.close();
           socket.close();
        }
        catch(Exception e) {
            //e.printStackTrace();
            e.getMessage();
        }
    }

    /**
     * Sends the specified ClientEvent to the server (it arrives to the {@link it.polimi.ingsw.serverView.ServerSocketHandler} that dispatches it)
     * @param event {@link ClientEvent}
     */
    public synchronized void sendEvent(ClientEvent event) {
        try {
            out.writeObject(event);
            out.flush();
        } catch (IOException e) {
            //e.printStackTrace();
            //notify(new Disconnect());
        }
    }
}
