package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;

import java.io.Serializable;

public interface ClientEvent extends Serializable {
    void handleEvent(Controller controller);
}
