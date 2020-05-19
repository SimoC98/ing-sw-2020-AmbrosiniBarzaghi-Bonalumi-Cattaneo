package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.serverView.ServerView;

public class WinnerEvent implements ServerEvent {

    private final String winner;

    public WinnerEvent(String winner) {
        this.winner = winner;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageWinner(winner);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
