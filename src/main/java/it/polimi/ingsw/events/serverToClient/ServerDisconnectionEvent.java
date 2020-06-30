package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;


public class ServerDisconnectionEvent implements ServerEvent{
    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageServerDisconnection();
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        return;
    }
}
