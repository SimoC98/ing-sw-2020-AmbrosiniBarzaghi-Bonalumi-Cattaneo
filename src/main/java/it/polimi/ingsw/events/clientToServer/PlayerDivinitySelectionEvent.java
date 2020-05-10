package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.serverView.PingSender;

public class PlayerDivinitySelectionEvent implements ClientEvent{

    private String divinity;

    @Override
    public void handleEvent(Controller controller) {

    }

    @Override
    public void handleEvent(PingSender pingSender) {

    }
}
