package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;
import it.polimi.ingsw.model.Action;

import java.util.List;
import java.util.Map;

public class InvalidMoveEvent implements ServerEvent{
    private Map<Action,List<Pair<Integer,Integer>>> possibleActions;
    private int wrongX, wrongY;

    public InvalidMoveEvent(Map<Action, List<Pair<Integer, Integer>>> possibleActions, int wrongX, int wrongY) {
        this.possibleActions = possibleActions;
        this.wrongX = wrongX;
        this.wrongY = wrongY;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageInvalidMove(possibleActions,wrongX,wrongY);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
