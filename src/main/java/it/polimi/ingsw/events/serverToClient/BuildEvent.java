package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Tile;

public class BuildEvent implements ServerEvent {

    private final String player;
    private final Action action;
    //private final Tile builtTile;
    private final int x,y;

    public BuildEvent(String player, Action action, int x, int y) {
        this.player = player;
        this.action = action;
        this.x = x;
        this.y = y;
    }


    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageBuild(player, x, y, action);
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        return;
    }
}
