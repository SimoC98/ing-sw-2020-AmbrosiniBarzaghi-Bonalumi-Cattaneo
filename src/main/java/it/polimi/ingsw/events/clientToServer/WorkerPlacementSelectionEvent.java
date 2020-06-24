package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.serverView.PingManager;

/**
 * Event that tells the model where to place a player's workers. The placement on the tiles is made passing the coordinates
 * that will be computed by the controller.
 */
public class WorkerPlacementSelectionEvent implements ClientEvent{
    private final int x1, y1, x2, y2;

    public WorkerPlacementSelectionEvent(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void handleEvent(Controller controller) {
        controller.handleWorkerPlacementInitialization(x1,y1,x2,y2);
    }

    @Override
    public void handleEvent(PingManager pingSender) {
        return;
    }
}
