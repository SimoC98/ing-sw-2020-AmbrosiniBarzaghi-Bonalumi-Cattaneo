package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;

/**
 * Sent if the client chose a worker that is currently blocked (cannot move).
 */
public class InvalidWorkerSelectionEvent implements ServerEvent{
    private int wrongX, wrongY;

    public InvalidWorkerSelectionEvent(int wrongX, int wrongY) {
        this.wrongX = wrongX;
        this.wrongY = wrongY;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageInvalidWorkerSelection(wrongX,wrongY);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
