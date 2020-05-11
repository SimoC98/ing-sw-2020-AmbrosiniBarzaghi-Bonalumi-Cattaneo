package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

public class WorkerSelectionEvent implements ServerEvent{

    @Override
    public void handleEvent(ClientView clientView) {
        //TODO
        //actually the client is expecting the possibleActions
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
