package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.serverView.PingManager;

public class DivinitySelectionAndWorkersPlacementEvent implements ClientEvent{

    private final String divinity;
    private final int x1, y1, x2, y2;

    public DivinitySelectionAndWorkersPlacementEvent(String divinity, int x1, int y1, int x2, int y2) {
        this.divinity = divinity;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void handleEvent(Controller controller) {
        controller.gameInitialization(x1, y1, x2, y2, divinity);
    }

    @Override
    public void handleEvent(PingManager pingSender) {
        return;
    }
}
