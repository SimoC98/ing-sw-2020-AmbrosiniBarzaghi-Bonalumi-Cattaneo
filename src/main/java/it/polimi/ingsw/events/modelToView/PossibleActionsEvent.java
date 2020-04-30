package it.polimi.ingsw.events.modelToView;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.serverView.ServerView;

import java.util.List;

public class PossibleActionsEvent implements MVEvent {

    List<Action> possibleActions;

    public PossibleActionsEvent(List<Action> possibleActions){
        this.possibleActions = possibleActions;
    }

    @Override
    public void handleEvent(ServerView serverView) {
        serverView.notifyPossibleActions(possibleActions);
    }
}
