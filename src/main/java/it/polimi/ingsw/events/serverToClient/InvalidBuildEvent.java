package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;
import it.polimi.ingsw.model.Action;

import java.util.List;

public class InvalidBuildEvent implements ServerEvent{
    private List<Action> possibleActions;
    private int wrongX, wrongY;

    public InvalidBuildEvent(List<Action> possibleActions, int wrongX, int wrongY) {
        this.possibleActions = possibleActions;
        this.wrongX = wrongX;
        this.wrongY = wrongY;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageInvalidBuild(possibleActions,wrongX,wrongY);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
