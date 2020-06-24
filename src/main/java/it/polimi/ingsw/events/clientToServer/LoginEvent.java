package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.serverView.PingManager;

/**
 * Sent when the client connects to the server. It bears the username with which the client will be known.
 */
public class LoginEvent implements ClientEvent{
    private final String username;

    public LoginEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void handleEvent(Controller controller) {    }

    @Override
    public void handleEvent(PingManager pingSender) {
        return;
    }
}
