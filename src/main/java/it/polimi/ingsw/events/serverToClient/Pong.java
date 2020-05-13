package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

public class Pong implements ServerEvent{
    @Override
    public void handleEvent(ClientView clientView) {
        return;
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        ping.receivePing();
    }
}
