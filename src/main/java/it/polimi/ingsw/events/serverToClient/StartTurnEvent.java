package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

/**
 * Informs a client that it is their turn. This should unlock the client's ui and stop the others'
 */
public class StartTurnEvent implements ServerEvent{
    private String username;

    public StartTurnEvent(String username) {
        this.username = username;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageStartTurn();
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
