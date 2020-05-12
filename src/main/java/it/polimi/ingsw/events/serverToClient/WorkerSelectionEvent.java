package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

public class WorkerSelectionEvent implements ServerEvent{

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageNewWorkerSelection();
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
