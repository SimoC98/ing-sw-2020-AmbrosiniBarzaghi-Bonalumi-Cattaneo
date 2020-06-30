package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;

/**
 * Event to tell the client to place their workers
 */
public class WorkerInitializationEvent implements ServerEvent{


    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageWorkersPlacementRequest();
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        return;
    }
}
