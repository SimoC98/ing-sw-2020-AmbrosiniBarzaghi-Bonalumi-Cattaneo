package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Phase;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;

public class BuildTwiceSameTile extends BuildTwice {

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
    public List<Phase> updatePossibleActions(List<Phase> possibleActions) {
        if(getBuildCount()>0) {
            if(getFirstBuildTile().getLevel()<=2) {
                possibleActions.add(Phase.BUILD);
            }
        }
        return super.updatePossibleActions(possibleActions);
    }
}
