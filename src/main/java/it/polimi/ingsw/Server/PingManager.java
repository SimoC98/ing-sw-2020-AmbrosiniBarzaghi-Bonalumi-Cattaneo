package it.polimi.ingsw.Server;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.clientToServer.ClientEvent;
import it.polimi.ingsw.events.serverToClient.Ping;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class that manages a ping to verify that a client is still active.
 */
public class PingManager implements Observer<ClientEvent> {
    private ServerSocketHandler connection;
    private boolean ping;
    private Timer pinger;
    private TimerTask task;
    private boolean stopPing=false;

    public PingManager(ServerSocketHandler connection) {
        this.connection = connection;
        connection.addObserver(this);
        ping = true;
    }



    public void receivePing() {
        ping = true;

    }

    /**
     * Called when a connection is created. It starts a {@code Timer} and updates it when it receives a pong from the client;
     * then it sends a Ping to the client. If it does not receive a pong, it drops the connection. see {@link ServerSocketHandler#disconnect()}
     */
    public void startPing() {
        pinger = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if(ping==false) {
                    //System.out.println("TIMEEER");
                    pinger.cancel();
                    stopPing=true;
                    connection.disconnect();
                }
                else {
                    ping = false;
                    connection.sendEvent(new Ping());
                    //System.out.print("ping");
                }
            }
        };

        //2 sec to receive pong
        pinger.schedule(task,0,2000);

    }

    /**
     * Cancels the timer for the ping, so it stops to check if a client is still connected
     */
    public void stop() {
        if(!stopPing) {
            pinger.cancel();
            stopPing=true;
        }
    }

    /**
     * Manages the reception of a {@link it.polimi.ingsw.events.clientToServer.Pong}
     * @param event
     */
    @Override
    public void update(ClientEvent event) {
        event.handleEvent(this);
    }
}