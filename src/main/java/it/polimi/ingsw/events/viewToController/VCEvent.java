package it.polimi.ingsw.events.viewToController;

import it.polimi.ingsw.controller.Controller;

import java.io.Serializable;

public interface VCEvent extends Serializable {
    public void handleEvent(Controller controller);
}
