package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;

/**
 * Informs the client that they correctly joined a session
 */
public class InLobbyEvent implements ServerEvent{


    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageInLobby();
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
