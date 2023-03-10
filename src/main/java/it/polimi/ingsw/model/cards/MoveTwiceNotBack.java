package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;


import java.util.List;

/**
 * This Goddess, Artemis, can move twice but not back to its initial space.
 * <p>
 * For this divinity it is required that {@code legalMove} checks
 * the player's second move selection and that {@code updatePossibleActions}
 * adds the possibility to end the move phase and thus beginning the build phase,
 * after the first move.
 */
public class MoveTwiceNotBack extends MoveMultipleTimes {

    public MoveTwiceNotBack() { super(); }

    public MoveTwiceNotBack(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    /**
     * Checks if a player is moving twice on different tiles
     * @param board current {@link Board}
     * @param selectedWorker {@link Worker} whose move is verified
     * @param selectedTile {@link Tile} to check
     * @return {@code true} if the move is correct: it is the first one and in accordance to the game rules, or it is the second and on a different tile
     */
    @Override
    public boolean legalMove(Board board,Worker selectedWorker, Tile selectedTile) {
        if(getMoveCount()>0) {
            if(getFirstMovedTile().getX()==selectedTile.getX() && getFirstMovedTile().getY()==selectedTile.getY()) {
                return false;
            }
        }
        return super.legalMove(board,selectedWorker,selectedTile);
    }

    /**
     * After the first move, the player can build
     * @param possibleActions List of actions to modify
     */
    @Override
    public void updatePossibleActions(List<Action> possibleActions) {


        if(getMoveCount()>1 || hasBuilt()) return;
        super.updatePossibleActions(possibleActions);
    }

    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        return super.move(board,selectedWorker, selectedTile);
    }

    @Override
    public List<ModelUpdate> build(Board board, Worker selectedWorker, Tile selectedTile) {
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

    @Override
    public void setupDivinity(List<Action> possibleActions) {
        super.setupDivinity(possibleActions);
    }
}
