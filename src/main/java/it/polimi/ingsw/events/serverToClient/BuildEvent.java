package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.serverView.ServerView;

public class BuildEvent implements ServerEvent {

    private Action action;
    private Tile builtTile;

    public BuildEvent(Action action, Tile builtTile) {
        this.action = action;
        this.builtTile = builtTile;
    }

    @Override
    public void handleEvent(ServerView serverView) {
        serverView.notifyBuild(builtTile.getX(), builtTile.getY(), action);
    }
}
