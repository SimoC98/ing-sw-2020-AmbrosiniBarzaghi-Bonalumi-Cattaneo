package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;

/**
 * Informs a client that the specified player moved with the selected worker on the specified tile
 */
public class MoveEvent implements ServerEvent {

    //private final Tile from, to;

    private final int x1,y1,x2,y2;
    private final String player;

    public MoveEvent(int x1, int y1, int x2, int y2, String player) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.player = player;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageMove(player, x1,y1,x2,y2);
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        return;
    }
}
