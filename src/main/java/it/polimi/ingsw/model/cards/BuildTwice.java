package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

import java.util.Set;

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
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        return super.legalBuild(selectedWorker, selectedTile);
    }

    /**
     * The function saves the {@link Tile} where the {@link Worker}
     * first builds and it increments the building counter in order to
     * build only twice.
     */
    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        if(buildCount==0) {
            firstBuildTile = selectedTile;
        }
        buildCount++;
        super.build(selectedWorker,selectedTile);
    }

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        super.move(selectedWorker, selectedTile);
    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        return super.legalMove(selectedWorker, selectedTile);
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
    public void setupDivinity(Set<Action> possibleActions) {
        firstBuildTile = null;
        buildCount = 0;
        super.setupDivinity(possibleActions);
    }

    @Override
    public Set<Action> updatePossibleActions(Set<Action> possibleActions) {
        return super.updatePossibleActions(possibleActions);
    }
}
