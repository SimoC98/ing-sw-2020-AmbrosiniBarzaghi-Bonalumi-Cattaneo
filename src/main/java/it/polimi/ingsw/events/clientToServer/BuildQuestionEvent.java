package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Action;

public class BuildQuestionEvent implements ClientEvent {
    private Action action;
    int x;
    int y;

    public BuildQuestionEvent(int x, int y) {
        action = Action.BUILD;
        this.x = x;
        this.y = y;
    }

    public Action getAction() {
        return action;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void handleEvent(Controller controller) {
        controller.handleActionValidation(action,x,y);
    }
}
