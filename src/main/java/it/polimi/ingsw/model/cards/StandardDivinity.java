package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

public class StandardDivinity implements Divinity {
    private final String name;
    private final String description;

    public StandardDivinity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        selectedWorker.move(selectedTile);
    }

    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        selectedWorker.build(selectedTile);
    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        return selectedWorker.legalMove(selectedTile);
    }

    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        return selectedWorker.legalBuild(selectedTile);
    }

    /*@Override
    public boolean isWinner(Worker ) {
        return
    } */

    @Override
    public Divinity getDivinity() {
        return this;
    }
}
