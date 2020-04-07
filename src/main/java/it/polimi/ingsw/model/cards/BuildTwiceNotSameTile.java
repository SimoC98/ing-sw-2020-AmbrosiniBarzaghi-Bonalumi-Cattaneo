package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;
import java.util.Set;

/**
 * Decorator Pattern
 * the Divinity decorated with this class is allowed to build 2 times but on different tiles
 */
public class BuildTwiceNotSameTile extends BuildTwice {

    public BuildTwiceNotSameTile(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        if(getBuildCount()>0) {
            if(getFirstBuildTile().getX()==selectedTile.getX() && getFirstBuildTile().getY()==selectedTile.getY()) {
                return false;
            }
        }
        return super.legalBuild(selectedWorker,selectedTile);
    }

    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        super.build(selectedWorker, selectedTile);
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

    @Override
    protected int getBuildCount() {
        return super.getBuildCount();
    }

    @Override
    protected Tile getFirstBuildTile() {
        return super.getFirstBuildTile();
    }

    /**
     *
     * is added to @param possibleActions BUILD and END: the current player can choose to build 2 times;
     * @return
     */
    @Override
    public Set<Action> updatePossibleActions(Set<Action> possibleActions) {
        if(getBuildCount()==1) {
            possibleActions.add(Action.BUILD);
            possibleActions.add(Action.END);
        }
        return super.updatePossibleActions(possibleActions);
    }

    @Override
    public void setupDivinity(Set<Action> possibleActions) {
        super.setupDivinity(possibleActions);
    }


}
