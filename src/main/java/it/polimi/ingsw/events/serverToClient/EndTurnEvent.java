package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;

/**
 * Notifies the client that their turn has ended
 */
public class EndTurnEvent implements ServerEvent{
    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageEndTurnEvent();
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
