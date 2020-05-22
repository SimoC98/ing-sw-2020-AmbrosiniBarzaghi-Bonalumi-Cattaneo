package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

import java.io.Serializable;

/**
 * Event after which a client sends their username. The server sends them an id to tell them their number
 */
public class LoginRequestEvent implements ServerEvent, Serializable {
    private int id;

    public LoginRequestEvent(int id) {
        this.id = id;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageLogin(id);
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        return;
    }
}
