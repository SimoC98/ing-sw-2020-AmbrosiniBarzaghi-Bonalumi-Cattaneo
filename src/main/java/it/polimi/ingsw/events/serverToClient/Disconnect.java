package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

public class Disconnect implements ServerEvent{

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.disconnect();
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        return;
    }
}
