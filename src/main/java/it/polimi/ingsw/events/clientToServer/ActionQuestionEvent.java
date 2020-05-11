package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.serverView.PingSender;

public class ActionQuestionEvent implements ClientEvent {

    private final Action action;
    private final int x, y;

    public ActionQuestionEvent(Action action, int x, int y) {
        this.action = action;
        this.x = x;
        this.y = y;
    }

    @Override
    public void handleEvent(Controller controller) {
        controller.handleActionValidation(action, x, y);
    }

    @Override
    public void handleEvent(PingSender pingSender) {
        return;
    }
}
