package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;
import it.polimi.ingsw.model.Action;

import java.util.List;
import java.util.Map;

/**
 * List containing the actions a user can select: move, build, builddome, end
 */
public class PossibleActionsEvent implements ServerEvent {

    private final Map<Action,List<Pair<Integer,Integer>>> possibleActions;

    public PossibleActionsEvent(Map<Action, List<Pair<Integer, Integer>>> possibleActions) {
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
