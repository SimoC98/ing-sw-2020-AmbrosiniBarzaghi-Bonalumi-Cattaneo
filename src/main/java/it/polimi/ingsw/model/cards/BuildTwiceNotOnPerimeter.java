package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.List;

public class BuildTwiceNotOnPerimeter extends BuildTwice{

    public BuildTwiceNotOnPerimeter(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    public BuildTwiceNotOnPerimeter() {
    }

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

    @Override
    public void updatePossibleActions(List<Action> possibleActions) {
        if(getBuildCount()!=1) return;
        super.updatePossibleActions(possibleActions);
    }
}
