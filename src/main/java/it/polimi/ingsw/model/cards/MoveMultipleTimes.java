package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.List;

/**
 * Parent class to realize divinities whose effect is to move twice in the same turn
 */


public class MoveMultipleTimes extends DivinityDecoratorWithEffects {
    private Tile firstMovedTile;
    protected int moveCount;
    protected boolean hasBuilt;

    public MoveMultipleTimes() { super(); }

    public MoveMultipleTimes(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    /**
     * Saves the tile the worker was on his first movement and updates the move counter
     * @param board current {@link Board}
     * @param selectedWorker Selected worker from the {@link Match}
     * @param selectedTile {@link Tile} to move onto
     * @return
     */
    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        if(moveCount==0) {
            firstMovedTile = selectedWorker.getPositionOnBoard();
        }
        moveCount++;
        return super.move(board,selectedWorker,selectedTile);
    }

    /**
     * The build can only happen upon a worker's change of position
     * @param board current {@link Board}
     * @param selectedWorker Selected worker from the {@link Match}
     * @param selectedTile {@link Tile} to build on
     * @return
     */
    @Override
    public List<ModelUpdate> build(Board board, Worker selectedWorker, Tile selectedTile) {
        hasBuilt = true;
        return super.build(board,selectedWorker, selectedTile);
    }

    @Override
    public boolean legalBuild(Board board,Worker selectedWorker, Tile selectedTile) {
        return super.legalBuild(board,selectedWorker, selectedTile);
    }


    @Override
    public Divinity getDivinity() {
        return super.getDivinity();
    }

    /**
     * Overrides the normal legalMove
     * @param board current {@link Board}
     * @param selectedWorker {@link Worker} whose move is verified
     * @param selectedTile {@link Tile} to check
     * @return
     */
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

    /**
     * Adds another MOVE action
     * @param possibleActions List of actions to modify
     */
    @Override
    public void updatePossibleActions(List<Action> possibleActions) {
        possibleActions.add(Action.MOVE);
         super.updatePossibleActions(possibleActions);
    }

    /**
     * Initializes the attribute for the correct use of the gods' effects
     * @param possibleActions List of actions to be modified
     */
    @Override
    public void setupDivinity(List<Action> possibleActions) {
        firstMovedTile = null;
        moveCount = 0;
        hasBuilt=false;
        super.setupDivinity(possibleActions);
    }
}
