package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

import java.util.List;

/**
 * Hestia's workers can build twice but not on the perimeter
 */
public class BuildTwiceNotOnPerimeter extends BuildTwice{

    public BuildTwiceNotOnPerimeter(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    public BuildTwiceNotOnPerimeter() {
    }

    /**
     * Checks if the worker is building for the first time or the second time and on which tile.
     * If the tile is on the edge of the board, they can not build.
     * @param board
     * @param selectedWorker
     * @param selectedTile
     * @return
     */
    @Override
    public boolean legalBuild(Board board, Worker selectedWorker, Tile selectedTile) {
        if(getBuildCount()==1) {
            if(selectedTile.getX()==0 || selectedTile.getX()==4 || selectedTile.getY()==0 || selectedTile.getY()==4) return false;
            else return super.legalBuild(board,selectedWorker,selectedTile);
        }
        else return super.legalBuild(board, selectedWorker, selectedTile);
    }

    @Override
    public void setupDivinity(List<Action> possibleActions) {
        super.setupDivinity(possibleActions);
    }

    /**
     * Updates the list of possible actions so that a user can build at most twice.
     * @param possibleActions
     */
    @Override
    public void updatePossibleActions(List<Action> possibleActions) {
        if(getBuildCount()!=1) return;
        super.updatePossibleActions(possibleActions);
    }
}
