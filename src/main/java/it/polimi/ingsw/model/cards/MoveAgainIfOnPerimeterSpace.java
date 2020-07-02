package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.List;

/**
 * This God, Triton, can move as many times as he wishes if his worker is on a perimetrical tile
 */
public class MoveAgainIfOnPerimeterSpace extends MoveMultipleTimes {
    //is true if after a move is on a perimeter cell
    private boolean isOnPerimeter;

    public MoveAgainIfOnPerimeterSpace(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    public MoveAgainIfOnPerimeterSpace() {
    }

    //if is not on perimeter or if has already built return

    /**
     * The list of possible actions is updated so that if the worker is not on the perimeter or they have built, they
     * can no longer keep moving
     * @param possibleActions
     */
    @Override
    public void updatePossibleActions(List<Action> possibleActions) {
        if(!isOnPerimeter || super.hasBuilt) return;
        else super.updatePossibleActions(possibleActions);
    }

    //set isOnPerimeter as true if has moved on a perimeter cell, otherwise set it to false

    /**
     * Updates the list of actions to send to the user, checking the flag.
     * If it is set to {@code true}, it returns a list containing both {@link Action#MOVE} and {@link Action#BUILD};
     * otherwise only {@link Action#BUILD}.
     * @param board
     * @param selectedWorker
     * @param selectedTile
     * @return
     */
    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.getX()==0 || selectedTile.getX()==4 || selectedTile.getY()==0 || selectedTile.getY()==4) isOnPerimeter=true;
        else isOnPerimeter=false;

        return super.move(board, selectedWorker, selectedTile);
    }

    /**
     * Initialize {@code isOnPerimeter} to false so that it updates correctly when calling {@code move}
     * @param possibleActions
     */
    @Override
    public void setupDivinity(List<Action> possibleActions) {
        isOnPerimeter=false;
        super.setupDivinity(possibleActions);
    }
}
