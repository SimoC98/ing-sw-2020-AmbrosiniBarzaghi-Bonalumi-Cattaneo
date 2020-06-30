package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.Server.PingManager;

/**
 * Tells the server which divinity the player chose.
 */
public class DivinitySelectionEvent implements ClientEvent{
    private final String chosenDivinity;

    public DivinitySelectionEvent(String chosenDivinity) {
        this.chosenDivinity = chosenDivinity;
    }

    @Override
    public void handleEvent(Controller controller) {
        controller.handleDivinityInitialization(chosenDivinity);
    }

    @Override
    public void handleEvent(PingManager pingSender) {
        return;
    }
}
