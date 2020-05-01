package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Action;

public class MoveQuestionEvent implements VCEvent{
    private Action action;
    private int x;
    private int y;

    public MoveQuestionEvent(int x, int y) {
        action = Action.MOVE;
        this.x = x;
        this.y = y;
    }


    @Override
    public void handleEvent(Controller controller) {
        controller.handleActionValidation(action,x,y);
    }
}
