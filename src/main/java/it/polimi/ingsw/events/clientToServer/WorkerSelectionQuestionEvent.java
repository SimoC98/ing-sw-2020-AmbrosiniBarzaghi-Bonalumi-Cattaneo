package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.serverView.PingManager;

public class WorkerSelectionQuestionEvent implements ClientEvent {
    private int x;
    private int y;

    public WorkerSelectionQuestionEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void handleEvent(Controller controller) {
        controller.handleSelectionWorker(x,y);
    }

    @Override
    public void handleEvent(PingManager pingSender) {
        return;
    }
}
