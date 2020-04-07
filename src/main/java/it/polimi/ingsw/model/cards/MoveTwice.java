package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;
import java.util.Set;

/**
 * Decorator Pattern
 * the Divinity decorated with this class is allowed to move 2 times
 */
public class MoveTwice extends DivinityDecoratorWithEffects {
    private Tile firstMovedTile;
    private int moveCount;

    public MoveTwice(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    /**
     *saves the tile from which the move action starts
     * @param selectedWorker
     * @param selectedTile
     */
    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        if(moveCount==0) {
            moveCount++;
            firstMovedTile = selectedWorker.getPositionOnBoard();
        }
        super.move(selectedWorker,selectedTile);
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
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        return super.legalMove(selectedWorker, selectedTile);
    }

    protected int getMoveCount() {
        return moveCount;
    }

    protected Tile getFirstMovedTile() {
        return firstMovedTile;
    }

    @Override
    public Set<Action> updatePossibleActions(Set<Action> possibleActions) {
        return super.updatePossibleActions(possibleActions);
    }

    /**
     * initializes the attributes of the class
     * @param possibleActions
     */
    @Override
    public void setupDivinity(Set<Action> possibleActions) {
        firstMovedTile = null;
        moveCount = 0;
        super.setupDivinity(possibleActions);
    }
}
