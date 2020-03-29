package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

public class StandardDivinity implements Divinity{
    String name;
    String description;

    public StandardDivinity(String name, String description)
    {
        this.name = name;
        this.description = description;
    }


    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {

    }

    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {

    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        return false;
    }

    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        return false;
    }

    @Override
    public Divinity getDivinity() {
        return null;
    }
}
