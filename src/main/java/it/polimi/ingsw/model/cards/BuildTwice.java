package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

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
     *The methods follows the call flow of parent classes to
     * effectively "decorate" such method
     */
    @Override
    public boolean legalBuild(Board board,Worker selectedWorker, Tile selectedTile) {
        return super.legalBuild(board,selectedWorker, selectedTile);
    }

    /**
     * The function saves the {@link Tile} where the {@link Worker}
     * first builds and it increments the building counter in order to
     * build only twice.
     */
    @Override
    public void build(Board board,Worker selectedWorker, Tile selectedTile) {
        if(buildCount==0) {
            firstBuildTile = selectedTile;
        }
        buildCount++;
        super.build(board,selectedWorker,selectedTile);
    }

    @Override
    public void move(Board board,Worker selectedWorker, Tile selectedTile) {
        super.move(board,selectedWorker, selectedTile);
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

    @Override
    public void setupDivinity(List<Action> possibleActions) {
        firstBuildTile = null;
        buildCount = 0;
        super.setupDivinity(possibleActions);
    }

    @Override
    public void updatePossibleActions(List<Action> possibleActions) {
        super.updatePossibleActions(possibleActions);
    }
}
