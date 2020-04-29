package it.polimi.ingsw.events.modelToView;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.serverView.ServerView;

public class MoveEvent implements MVEvent{

    //TODO: do I need "Worker"? probably not
    private Tile fromTile, toTile;

    public MoveEvent(Tile fromTile, Tile toTile){
        this.fromTile = fromTile;
        this.toTile = toTile;
    }

    @Override
    public void handleEvent(ServerView serverView) {
        serverView.notifyMove(fromTile.getX(), fromTile.getY(), toTile.getX(), toTile.getY());
    }
}
