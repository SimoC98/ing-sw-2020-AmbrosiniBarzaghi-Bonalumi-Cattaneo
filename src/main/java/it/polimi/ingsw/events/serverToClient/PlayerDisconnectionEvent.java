package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;

/**
 * Sent to every client after an user disconnected. According to specifications, the game ends if someone quits
 */
public class PlayerDisconnectionEvent implements ServerEvent{
    private String username;

    public PlayerDisconnectionEvent(String username) {
        this.username = username;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.managePlayerDisconnection(username);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
