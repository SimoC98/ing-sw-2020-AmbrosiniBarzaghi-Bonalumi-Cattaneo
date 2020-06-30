package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;

/**
 * sent if user picked an invalid username(less than 3 characters, more than 15 characters or contains blank spaces)
 */
public class InvalidUsernameEvent implements ServerEvent{

    @Override
    public void handleEvent(ClientView clientView) {
        //never used
        return;
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        return;

    }
}
