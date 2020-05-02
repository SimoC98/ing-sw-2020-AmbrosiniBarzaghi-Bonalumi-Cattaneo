package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;

public class StartTurnEvent implements ServerEvent{
    private String username;

    public StartTurnEvent(String username) {
        this.username = username;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        //TODO
    }
}
