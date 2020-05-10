package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;

public class LoginRequestEvent implements ServerEvent{
    private int id;

    public LoginRequestEvent(int id) {
        this.id = id;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        //TODO
    }
}
