package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;
import it.polimi.ingsw.model.update.MoveUpdate;


import java.util.List;

/**
 * This Goddess, Demeter, can build twice but not on the same tile.
 * <p>
 * For this divinity it is required that {@code legalBuild} checks
 * the player's second build position and that {@code updatePossibleActions}
 * adds the possibility to end the build phase and thus the turn, after the
 * first build. This is why it adds {@link Action#END} in the list of actions to pick from.
 */
public class BuildTwiceNotSameTile extends BuildTwice {

    public BuildTwiceNotSameTile() { super(); }

    public BuildTwiceNotSameTile(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    /**
     * Checks if the player is building on a tile different from the first
     * @param board
     * @param selectedWorker
     * @param selectedTile
     * @return {@code true} if the build is correct: it is the first one and in accordance to the game rules, or it is the second and on a different tile
     */
    @Override
    public boolean legalBuild(Board board,Worker selectedWorker, Tile selectedTile) {
        if(getBuildCount()>0) {
            if(getFirstBuildTile().getX()==selectedTile.getX() && getFirstBuildTile().getY()==selectedTile.getY()) {
                return false;
            }
        }
        return super.legalBuild(board,selectedWorker,selectedTile);
    }

    @Override
    public List<ModelUpdate> build(Board board, Worker selectedWorker, Tile selectedTile) {
        return super.build(board,selectedWorker, selectedTile);
    }

    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        return super.move(board,selectedWorker, selectedTile);
    }

    @Override
    public boolean legalMove(Board board,Worker selectedWorker, Tile selectedTile) {
        return super.legalMove(board,selectedWorker, selectedTile);
    }

    @Override
    public Divinity getDivinity() {
        return super.getDivinity();
    }

    @Override
    protected int getBuildCount() {
        return super.getBuildCount();
    }

    @Override
    protected Tile getFirstBuildTile() {
        return super.getFirstBuildTile();
    }

    /**
     * After the first build the player can end his turn
     * @param possibleActions
     */
    @Override
    public void updatePossibleActions(List<Action> possibleActions) {
        /*if(getBuildCount()==1) {
            List<Tile> l = ActionManager.getMatch().getAvailableBuildTiles(Game.getMatch().getSelectedWorker());
            if(l.size()>0) {
                possibleActions.add(Action.BUILD);
                possibleActions.add(Action.END);
            }
        }*/

        if(getBuildCount()==1) {
            possibleActions.add(Action.BUILD);
            possibleActions.add(Action.END);
        }
        super.updatePossibleActions(possibleActions);
    }

    @Override
    public void setupDivinity(List<Action> possibleActions) {
        super.setupDivinity(possibleActions);
    }


}
