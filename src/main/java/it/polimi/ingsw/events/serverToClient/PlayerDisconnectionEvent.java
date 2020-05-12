package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

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
