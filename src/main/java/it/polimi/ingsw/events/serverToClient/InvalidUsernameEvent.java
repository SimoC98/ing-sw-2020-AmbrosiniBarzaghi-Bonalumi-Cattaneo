package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;

import java.util.List;

public class InvalidUsernameEvent implements ServerEvent {
    private List<String> username;

    public InvalidUsernameEvent(List<String> username) {
        this.username = username;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        //TODO
    }
}
