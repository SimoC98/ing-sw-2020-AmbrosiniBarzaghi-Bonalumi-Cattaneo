package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;

import java.io.Serializable;


/**
 * This class is the parent of all the other client events. It is sent to the client to  be read and to override the
 * specified method.
 */
public interface ServerEvent extends Serializable {
    void handleEvent(ClientView clientView);
    void handleEvent(PingReceiver ping);
}
