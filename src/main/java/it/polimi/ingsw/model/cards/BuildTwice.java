package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.List;

/**
 * General class to decorate divinities that are able to build twice consecutively.
 * The functioning of {@code build} and {@code legalBuild} methods are changed;
 * this type of divinities requires that a build counter is created and the tile
 * the player builds on, is remembered. Other methods are unchanged.
 */
public class BuildTwice extends DivinityDecoratorWithEffects {
    private Tile firstBuildTile;
    private int buildCount;

    public BuildTwice() { super(); }

    public BuildTwice(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    /**
     * The methods follows the call flow of parent classes to effectively "decorate" this method
     * @param board current {@link Board}
     * @param selectedWorker {@link Worker} whose build is verified
     * @param selectedTile {@link Tile} to check
     * @return
     */
    @Override
    public boolean legalBuild(Board board,Worker selectedWorker, Tile selectedTile) {
        return super.legalBuild(board,selectedWorker, selectedTile);
    }

    /**
     * The function saves the {@link Tile} where the {@link Worker}
     * first builds and it increments the building counter in order to
     * build only twice.
     * @param board current {@link Board}
     * @param selectedWorker Selected worker from the {@link Match}
     * @param selectedTile {@link Tile} to build on
     * @return list of tiles to be sent to view
     */
    @Override
    public List<ModelUpdate> build(Board board, Worker selectedWorker, Tile selectedTile) {
        if(buildCount==0) {
            firstBuildTile = selectedTile;
        }
        buildCount++;
        return super.build(board,selectedWorker,selectedTile);
    }

    @Override
    public List<ModelUpdate> move(Board board,Worker selectedWorker, Tile selectedTile) {
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

    protected int getBuildCount() {
        return buildCount;
    }

    protected Tile getFirstBuildTile() {
        return firstBuildTile;
    }

    /**
     * Tracks the first building position and the building count
     * @param possibleActions List of actions to be modified
     */
    @Override
    public void setupDivinity(List<Action> possibleActions) {
        firstBuildTile = null;
        buildCount = 0;
        super.setupDivinity(possibleActions);
    }

    /**
     * Adds the possibility to build twice or to end the turn
     * @param possibleActions List of actions to modify
     */
    @Override
    public void updatePossibleActions(List<Action> possibleActions) {
        possibleActions.add(Action.BUILD);
        possibleActions.add(Action.END);

        super.updatePossibleActions(possibleActions);
    }
}
