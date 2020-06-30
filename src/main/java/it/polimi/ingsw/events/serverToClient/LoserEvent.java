package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;

/**
 * Event sent to inform a player that they lost
 */
public class LoserEvent implements ServerEvent {

    private final String loser;

    public LoserEvent(String loser) {
        this.loser = loser;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageLoser(loser);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
