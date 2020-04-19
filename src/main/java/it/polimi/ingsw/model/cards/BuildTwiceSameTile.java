package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.Set;

/**
 * Decorator Pattern
 * the Divinity decorated with this class is allowed to build 2 times on the same tile
 * and only if the second built is not a dome
 */
public class BuildTwiceSameTile extends BuildTwice {

    public BuildTwiceSameTile() { super(); }

    public BuildTwiceSameTile(Divinity decoratedDivinity) {
        super(decoratedDivinity);
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

    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        if(getBuildCount()>0) {
            if(getFirstBuildTile().getX()==selectedTile.getX() && getFirstBuildTile().getY()==selectedTile.getY()) {
                return true;
            }
            else {
                return false;
            }
        }
        return super.legalBuild(selectedWorker,selectedTile);
    }

    @Override
    public Set<Action> updatePossibleActions(Set<Action> possibleActions) {
        if(getBuildCount()==1) {
            if(getFirstBuildTile().getLevel()<=2) {
                possibleActions.add(Action.BUILD);
                possibleActions.add(Action.END);
            }
        }
        return super.updatePossibleActions(possibleActions);
    }

    @Override
    public void setupDivinity(Set<Action> possibleActions) {
        super.setupDivinity(possibleActions);
    }


}
