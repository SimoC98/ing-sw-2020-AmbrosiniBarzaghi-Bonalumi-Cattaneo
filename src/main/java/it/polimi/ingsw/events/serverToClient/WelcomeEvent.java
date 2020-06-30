package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;

/**
 * Event after which a client sends their username. The server sends them an id to tell them their number
 */
public class WelcomeEvent implements ServerEvent {
    private int id;

    public WelcomeEvent(int id) {
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
