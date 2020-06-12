package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

public class InvalidWorkerSelection implements ServerEvent{
    private int wrongX, wrongY;

    public InvalidWorkerSelection(int wrongX, int wrongY) {
        this.wrongX = wrongX;
        this.wrongY = wrongY;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageInvalidWorkerSelection(wrongX,wrongY);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
