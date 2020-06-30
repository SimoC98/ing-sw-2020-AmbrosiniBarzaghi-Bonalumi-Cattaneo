package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;

/**
 * Asks the client to select a worker
 */
public class WorkerSelectionEvent implements ServerEvent{

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageNewWorkerSelection();
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
