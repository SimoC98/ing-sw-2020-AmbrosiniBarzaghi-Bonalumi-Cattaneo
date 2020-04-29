package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

import java.util.List;

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
    public void move(Board board,Worker selectedWorker, Tile selectedTile) {
        if(moveCount==0) {
            firstMovedTile = selectedWorker.getPositionOnBoard();
        }
        moveCount++;
        super.move(board,selectedWorker,selectedTile);
    }

    /**
     * The build can only happen upon a worker's change of position
     */
    @Override
    public void build(Board board,Worker selectedWorker, Tile selectedTile) {
        hasBuilt = true;
        super.build(board,selectedWorker, selectedTile);
    }

    @Override
    public boolean legalBuild(Board board,Worker selectedWorker, Tile selectedTile) {
        return super.legalBuild(board,selectedWorker, selectedTile);
    }


    @Override
    public Divinity getDivinity() {
        return super.getDivinity();
    }

    @Override
    public boolean legalMove(Board board,Worker selectedWorker, Tile selectedTile) {
        return super.legalMove(board,selectedWorker, selectedTile);
    }

    protected int getMoveCount() {
        return moveCount;
    }

    protected Tile getFirstMovedTile() {
        return firstMovedTile;
    }

    protected boolean hasBuilt() {
        return hasBuilt;
    }

    @Override
    public void updatePossibleActions(List<Action> possibleActions) {
         super.updatePossibleActions(possibleActions);
    }

    /**
     * Initializes the attribute for the correct use of the gods' effects
     */
    @Override
    public void setupDivinity(List<Action> possibleActions) {
        firstMovedTile = null;
        moveCount = 0;
        hasBuilt=false;
        super.setupDivinity(possibleActions);
    }
}
