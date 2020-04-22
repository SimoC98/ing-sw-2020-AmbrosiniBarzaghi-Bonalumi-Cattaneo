package it.polimi.ingsw.events.viewToController;

import it.polimi.ingsw.controller.Controller;

public interface VCEvent {
    public void handleEvent(Controller controller);
}
