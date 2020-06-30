package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;

/**
 * Informs the player that they are the winner
 */
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
