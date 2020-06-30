package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.Server.PingManager;

//TODO: MAYBE
public class DisconnectionEvent implements ClientEvent {

    private final String playerName;

    public DisconnectionEvent(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void handleEvent(Controller controller) {
        controller.handleDisconnection(playerName);
    }

    @Override
    public void handleEvent(PingManager pingSender) {
        return;
    }
}
