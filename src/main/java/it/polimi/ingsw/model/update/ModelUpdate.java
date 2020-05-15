package it.polimi.ingsw.model.update;

import it.polimi.ingsw.model.*;

import java.util.List;

public class ModelUpdate {
    private final Action action;
    private Worker worker;
    private List<Tile> modifiedTiles;

    public ModelUpdate(Action action, Worker worker, List<Tile> modifiedTiles) {
        this.action = action;
        this.worker = worker;
        this.modifiedTiles = modifiedTiles;
    }

    public Action getAction() {
        return action;
    }

    public Worker getWorker() {
        return worker;
    }

    public List<Tile> getModifiedTiles() {
        return modifiedTiles;
    }
}
