package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.serverView.PingSender;

public class LoginEvent implements ClientEvent{
    private int playerNumber=-1;
    private String username;

    public int getPlayerNumber() {
        return playerNumber;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void handleEvent(Controller controller) {
        return;
    }

    @Override
    public void handleEvent(PingSender pingSender) {
        return;
    }
}
