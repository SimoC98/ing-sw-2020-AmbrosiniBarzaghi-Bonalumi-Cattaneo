package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;
import java.util.Set;

public class MoveTwiceNotBack extends MoveTwice{

    public MoveTwiceNotBack(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    /**
     * @param selectedWorker
     * @param selectedTile
     * @return false if, during the second movement, the worker tries to move back to the tile where he started
     */
    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        if(getMoveCount()>0) {
            if(getFirstMovedTile().getX()==selectedTile.getX() && getFirstMovedTile().getY()==selectedTile.getY()) {
                return false;
            }
        }
        return super.legalMove(selectedWorker,selectedTile);
    }

    @Override
    public Set<Action> updatePossibleActions(Set<Action> possibleActions) {
        if(getMoveCount() == 1 && !isHasBuilt()) {
            possibleActions.add(Action.MOVE);
        }
        return super.updatePossibleActions(possibleActions);
    }

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        super.move(selectedWorker, selectedTile);
    }

    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        super.build(selectedWorker, selectedTile);
    }

    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        return super.legalBuild(selectedWorker, selectedTile);
    }

    @Override
    public Divinity getDivinity() {
        return super.getDivinity();
    }

    @Override
    public void setupDivinity(Set<Action> possibleActions) {
        super.setupDivinity(possibleActions);
    }
}
