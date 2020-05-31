package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;
import it.polimi.ingsw.model.Action;

import java.util.List;

public class InvalidMoveEvent implements ServerEvent{
    private List<Action> possibleActions;

    public InvalidMoveEvent(List<Action> possibleActions) {
        this.possibleActions = possibleActions;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageInvalidMove(possibleActions);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
