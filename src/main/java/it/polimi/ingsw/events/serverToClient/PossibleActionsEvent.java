package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.serverView.ServerView;

import java.util.List;

public class PossibleActionsEvent implements ServerEvent {

    private final List<Action> possibleActions;

    public PossibleActionsEvent(List<Action> possibleActions){
        this.possibleActions = possibleActions;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.managePossibleActions(possibleActions);
    }
}
