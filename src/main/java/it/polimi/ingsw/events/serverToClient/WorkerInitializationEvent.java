package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

public class WorkerInitializationEvent implements ServerEvent{


    @Override
    public void handleEvent(ClientView clientView) {
        //TODO
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        return;
    }
}
