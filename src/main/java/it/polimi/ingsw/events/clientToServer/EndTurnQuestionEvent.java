package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.serverView.PingManager;

/**
 * Event informing the server that a user wants to end their turn. It is particularly important
 * for players with a divinity that modifies the list of available actions.
 */
public class EndTurnQuestionEvent implements ClientEvent {
    private Action action;
    private int x;
    private int y;

    /**
     * The coordinates are set to -1 in order not to break the paradigm of the other events
     */
    public EndTurnQuestionEvent() {
        action = Action.END;
        x = -1;
        y = -1;
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
