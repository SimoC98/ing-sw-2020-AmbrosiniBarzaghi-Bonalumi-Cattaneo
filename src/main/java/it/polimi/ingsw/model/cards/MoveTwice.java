package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Phase;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;

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

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        if(moveCount==0) {
            moveCount++;
            firstMovedTile = selectedTile;
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
    public List<Phase> updatePossibleActions(List<Phase> possibleActions) {
        return super.updatePossibleActions(possibleActions);
    }

    @Override
    public void setupDivinity(List<Phase> possibleActions) {
        firstMovedTile = null;
        moveCount = 0;
        super.setupDivinity(possibleActions);
    }
}
