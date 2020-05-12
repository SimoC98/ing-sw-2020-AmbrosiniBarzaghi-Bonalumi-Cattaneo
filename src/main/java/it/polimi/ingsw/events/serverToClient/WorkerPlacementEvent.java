package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

public class WorkerPlacementEvent implements ServerEvent{
    private int x1,y1,x2,y2;

    public WorkerPlacementEvent(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        //TODO
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
