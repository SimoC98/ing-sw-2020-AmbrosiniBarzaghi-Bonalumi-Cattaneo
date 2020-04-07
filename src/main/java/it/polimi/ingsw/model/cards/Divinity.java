package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;
import java.util.Set;

/**
        * Decorator Pattern *
        * methods implemented by different Divinities
        */
public interface Divinity {
    public void move(Worker selectedWorker, Tile selectedTile) ;
    public void build(Worker selectedWorker, Tile selectedTile);
    public boolean legalMove(Worker selectedWorker, Tile selectedTile);
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile);
    public Divinity getDivinity();
    public Set<Action> updatePossibleActions(Set<Action> possibleActions);
    public void setupDivinity(Set<Action> possibleActions);
}
