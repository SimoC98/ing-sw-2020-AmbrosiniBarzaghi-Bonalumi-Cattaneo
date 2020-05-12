package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.serverView.PingManager;

public class BuildDomeQuestionEvent implements ClientEvent {
    private Action action;
    int x;
    int y;

    public BuildDomeQuestionEvent(int x, int y) {
        action = Action.BUILDDOME;
        this.x = x;
        this.y = y;
    }

    @Override
    public void handleEvent(Controller controller) {
        controller.handleActionValidation(action,x,y);
    }

    @Override
    public void handleEvent(PingManager pingSender) {
        return;
    }
}
