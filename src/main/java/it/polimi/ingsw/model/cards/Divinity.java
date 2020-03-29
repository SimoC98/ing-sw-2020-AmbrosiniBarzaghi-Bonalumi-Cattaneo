package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Phase;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;

/**
 * Decorator Pattern *
 */
public interface Divinity {
    public void move(Worker selectedWorker, Tile selectedTile) ;
    public void build(Worker selectedWorker, Tile selectedTile);
    public boolean legalMove(Worker selectedWorker, Tile selectedTile);
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile);
    public Divinity getDivinity();
    public List<Phase> updatePossibleActions(List<Phase> possibleActions);
    public void setupDivinity(List<Phase> possibleActions);
}
