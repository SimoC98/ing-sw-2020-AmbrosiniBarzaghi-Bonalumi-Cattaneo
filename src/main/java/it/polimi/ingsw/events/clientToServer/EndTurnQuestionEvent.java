package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Action;

public class EndTurnQuestionEvent implements VCEvent{
    private Action action;
    private int x;
    private int y;

    public EndTurnQuestionEvent() {
        action = Action.END;
        x = -1;
        y = -1;
    }

    @Override
    public void handleEvent(Controller controller) {
        controller.handleActionValidation(action,x,y);
    }
}
