package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

import java.util.Set;

/**
 * Parent class to realize divinities whose effect is to move twice in the same turn
 */
public class MoveTwice extends DivinityDecoratorWithEffects {
    private Tile firstMovedTile;
    private int moveCount;
    private boolean hasBuilt;

    public MoveTwice() { super(); }

    public MoveTwice(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    /**
     * Saves the tile the worker was on his first movement and updates the move counter
     */
    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        if(moveCount==0) {
            firstMovedTile = selectedWorker.getPositionOnBoard();
        }
        moveCount++;
        super.move(selectedWorker,selectedTile);
    }

    /**
     * The build can only happen upon a worker's change of position
     */
    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        hasBuilt = true;
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
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        return super.legalMove(selectedWorker, selectedTile);
    }

    protected int getMoveCount() {
        return moveCount;
    }

    protected Tile getFirstMovedTile() {
        return firstMovedTile;
    }

    protected boolean isHasBuilt() {
        return hasBuilt;
    }

    @Override
    public void updatePossibleActions(Set<Action> possibleActions) {
         super.updatePossibleActions(possibleActions);
    }

    /**
     * Initializes the attribute for the correct use of the gods' effects
     */
    @Override
    public void setupDivinity(Set<Action> possibleActions) {
        firstMovedTile = null;
        moveCount = 0;
        hasBuilt=false;
        super.setupDivinity(possibleActions);
    }
}
