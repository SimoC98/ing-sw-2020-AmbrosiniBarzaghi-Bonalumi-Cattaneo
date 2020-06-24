package it.polimi.ingsw.clientView;

import it.polimi.ingsw.Observer;

import it.polimi.ingsw.events.clientToServer.Pong;
import it.polimi.ingsw.events.serverToClient.ServerEvent;

/**
 * Class to verify that a user is still active. Once a ping is received from the {@link it.polimi.ingsw.serverView.Server},
 * a {@link Pong} is sent to prove that the connection is working.
 * It extends the class observer to effectively update and override the method when a Pong (a ServerEvent) is received.
 */
public class PingReceiver implements Observer<ServerEvent> {
    private ClientSocketHandler connection;

    public PingReceiver(ClientSocketHandler connection) {
        this.connection = connection;
        connection.addObserver(this);
    }


    /**
     * Called whenever a {@link it.polimi.ingsw.events.serverToClient.Ping} is received.
     */
    public void receivePing() {
        connection.sendEvent(new Pong());
    }


    /**
     * Updates the class to send a pong when it receives a ping.
     * @param event ServerEvent received. If the event is not a pong, it will not override the class
     */
    @Override
    public void update(ServerEvent event) {
        event.handleEvent(this);
    }
}