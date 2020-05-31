package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

public class InvalidWorkerPlacement implements ServerEvent{

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageInvalidWorkerPlacement();
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
