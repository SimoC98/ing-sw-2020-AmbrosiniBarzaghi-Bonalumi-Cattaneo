package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;
import it.polimi.ingsw.model.Action;

import java.util.List;
import java.util.Map;

/**
 * Tells the client that they performed a bad build and sends again the list of possible action through a map:
 * the action and the list of coordinates as a {@link Pair}
 */
public class InvalidBuildEvent implements ServerEvent{
    private Map<Action,List<Pair<Integer,Integer>>> possibleActions;
    private int wrongX, wrongY, actualX, actualY;

    public InvalidBuildEvent(Map<Action, List<Pair<Integer, Integer>>> possibleActions, int wrongX, int wrongY, int actualX, int actualY) {
        this.possibleActions = possibleActions;
        this.wrongX = wrongX;
        this.wrongY = wrongY;
        this.actualX = actualX;
        this.actualY = actualY;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageInvalidBuild(possibleActions,wrongX,wrongY,actualX,actualY);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
