package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;

/**
 * Sent if the user chose a wrong place for the placement of a worker
 */
public class InvalidWorkerPlacement implements ServerEvent{

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageInvalidWorkerPlacement();
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
