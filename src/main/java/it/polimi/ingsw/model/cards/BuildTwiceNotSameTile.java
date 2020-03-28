package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

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
}
