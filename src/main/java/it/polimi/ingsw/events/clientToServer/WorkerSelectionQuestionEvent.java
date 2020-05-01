package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;

public class WorkerSelectionQuestionEvent implements VCEvent{
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
}
