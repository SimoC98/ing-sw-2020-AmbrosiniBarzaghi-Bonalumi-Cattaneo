package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.serverView.ServerView;

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
