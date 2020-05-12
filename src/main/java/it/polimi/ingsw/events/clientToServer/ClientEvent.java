package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.serverView.PingManager;

import java.io.Serializable;

public interface ClientEvent extends Serializable {
    void handleEvent(Controller controller);
    void handleEvent(PingManager pingSender);
}
