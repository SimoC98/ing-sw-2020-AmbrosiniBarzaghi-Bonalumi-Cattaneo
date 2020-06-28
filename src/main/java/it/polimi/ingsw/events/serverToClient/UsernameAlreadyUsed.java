package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

import java.util.List;

/**
 * Sent if the user picked an already used username
 */
public class UsernameAlreadyUsed implements ServerEvent {
    private final List<String> usernames;

    public UsernameAlreadyUsed(List<String> usernames) {
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
