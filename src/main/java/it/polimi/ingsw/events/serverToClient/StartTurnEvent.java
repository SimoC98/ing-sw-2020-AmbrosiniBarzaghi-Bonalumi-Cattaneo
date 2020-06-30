package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;

/**
 * Informs a client that it is their turn. This should unlock the client's ui and stop the others'
 */
public class StartTurnEvent implements ServerEvent{
    public StartTurnEvent() {    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageStartTurn();
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
