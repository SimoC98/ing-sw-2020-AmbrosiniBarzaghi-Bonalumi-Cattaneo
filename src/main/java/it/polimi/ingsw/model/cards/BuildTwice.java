package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;
import java.util.Set;

/**
 * Decorator Pattern
 * the Divinity decorated with this class is allowed to build 2 times
 */
public class BuildTwice extends DivinityDecoratorWithEffects {
    private Tile firstBuildTile;
    private int buildCount;

    public BuildTwice(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        return super.legalBuild(selectedWorker, selectedTile);
    }

    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        if(buildCount==0) {
            buildCount++;
            firstBuildTile = selectedTile;
        }
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
