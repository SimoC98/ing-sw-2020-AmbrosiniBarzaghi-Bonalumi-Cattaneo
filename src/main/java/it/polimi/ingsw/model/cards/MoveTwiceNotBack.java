package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.model.Phase;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;

public class MoveTwiceNotBack extends MoveTwice{

    public MoveTwiceNotBack(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        if(getMoveCount()>0) {
            if(getFirstMovedTile().getX()==selectedTile.getX() && getFirstMovedTile().getY()==selectedTile.getY()) {
                return false;
            }
        }
        return super.legalMove(selectedWorker,selectedTile);
    }

    @Override
    public List<Phase> updatePossibleActions(List<Phase> possibleActions) {
        if(getMoveCount()>0) {
            possibleActions.add(Phase.MOVE);
        }
        return super.updatePossibleActions(possibleActions);
    }
}
