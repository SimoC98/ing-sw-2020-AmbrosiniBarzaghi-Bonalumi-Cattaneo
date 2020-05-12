package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

import java.io.Serializable;

public interface ServerEvent extends Serializable {
    void handleEvent(ClientView clientView);
    void handleEvent(PingReceiver ping);
}
