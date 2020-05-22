package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.serverView.PingManager;

/**
 * Event to manage the connection between the client and server.
 * It verifies that a client is always active and reachable
 */
public class Ping implements ClientEvent{

    @Override
    public void handleEvent(Controller controller) {
        return;
    }

    @Override
    public void handleEvent(PingManager pingSender) {
        pingSender.receivePing();
    }
}
