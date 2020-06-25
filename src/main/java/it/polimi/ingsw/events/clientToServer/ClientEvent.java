package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.serverView.PingManager;

import java.io.Serializable;

/**
 * Base class that client events inherit. These events are sent to the server to communicate a user's action, overriding
 * the specified action on the controller.
 */
public interface ClientEvent extends Serializable {
    void handleEvent(Controller controller);
    void handleEvent(PingManager pingSender);
}
