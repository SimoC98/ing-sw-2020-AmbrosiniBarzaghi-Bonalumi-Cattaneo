package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Tile;

public class BuildEvent implements ServerEvent {

    private final String player;
    private final Action action;
    private final Tile builtTile;

    public BuildEvent(String player, Action action, Tile builtTile) {
        this.player = player;
        this.action = action;
        this.builtTile = builtTile;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageBuild(player, builtTile.getX(), builtTile.getY(), action);
    }
}
