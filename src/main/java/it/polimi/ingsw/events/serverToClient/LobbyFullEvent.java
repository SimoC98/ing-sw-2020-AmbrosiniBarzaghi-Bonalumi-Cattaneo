package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

/**
 * Event telling a client they connected after the lobby filled
 */
public class LobbyFullEvent implements ServerEvent{

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.lobbyFull();
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        return;
    }
}
