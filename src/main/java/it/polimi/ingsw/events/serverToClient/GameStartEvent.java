package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

/**
 * Notifies the client that the game has begun
 */
public class GameStartEvent implements ServerEvent{

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.startGame();
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        return;
    }
}
