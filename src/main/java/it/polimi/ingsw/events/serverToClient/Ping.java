package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

/**
 * Verifies that a client is reachable. Sent after a pong is received from the client and at the beginning of the connection.
 */
public class Ping implements ServerEvent{
    @Override
    public void handleEvent(ClientView clientView) {
        return;
    }

    /**
     * Overrides the PingReceiver to send a pong.
     * @param ping {@link PingReceiver}
     */
    @Override
    public void handleEvent(PingReceiver ping) {
        ping.receivePing();
    }
}
