package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

public class WorkerPlacementEvent implements ServerEvent{
    private String username;
    private int x1,y1,x2,y2;

    public WorkerPlacementEvent(String username, int x1, int y1, int x2, int y2) {
        this.username = username;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageWorkersInitialPlacement(username, x1, y1, x2, y2);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
