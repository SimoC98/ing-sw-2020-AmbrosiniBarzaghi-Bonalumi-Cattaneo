package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

/**
 * Verifies that a client is reachable. Sent after a ping is received from the client
 */
public class Ping implements ServerEvent{
    @Override
    public void handleEvent(ClientView clientView) {
        return;
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        ping.receivePing();
    }
}
