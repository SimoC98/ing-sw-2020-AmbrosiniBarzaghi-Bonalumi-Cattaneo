package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.serverView.ServerView;

import java.util.List;

/**
 * List containing the actions a user can select: move, build, builddome, end
 */
public class PossibleActionsEvent implements ServerEvent {

    private final List<Action> possibleActions;

    public PossibleActionsEvent(List<Action> possibleActions){
        this.possibleActions = possibleActions;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.managePossibleActions(possibleActions);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
