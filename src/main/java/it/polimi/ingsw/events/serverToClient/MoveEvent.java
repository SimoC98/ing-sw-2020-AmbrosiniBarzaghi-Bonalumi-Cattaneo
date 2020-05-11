package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;
import it.polimi.ingsw.model.Tile;

public class MoveEvent implements ServerEvent {

    private final Tile from, to;
    private final String player;

    public MoveEvent(String player, Tile from, Tile to){
        this.from = from;
        this.to = to;
        this.player = player;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageMove(player, from.getX(), from.getY(), to.getX(), to.getY());
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
