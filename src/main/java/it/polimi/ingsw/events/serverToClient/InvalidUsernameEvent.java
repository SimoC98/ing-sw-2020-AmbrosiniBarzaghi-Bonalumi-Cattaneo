package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

import java.util.List;

/**
 * Sent if the user picked an already used username
 */
public class InvalidUsernameEvent implements ServerEvent {
    private final List<String> usernames;

    public InvalidUsernameEvent(List<String> usernames) {
        this.usernames = usernames;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageWrongUsername(usernames);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
