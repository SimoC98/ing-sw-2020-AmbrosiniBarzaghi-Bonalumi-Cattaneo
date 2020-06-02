package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.List;

public class MoveAgainIfOnPerimeterSpace extends MoveTwice{
    //is true if after a move is on a perimeter cell
    private boolean isOnPerimeter;

    public MoveAgainIfOnPerimeterSpace(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    public MoveAgainIfOnPerimeterSpace() {
    }

    //if is not on perimeter or if has already built return
    @Override
    public void updatePossibleActions(List<Action> possibleActions) {
        if(!isOnPerimeter || super.hasBuilt) return;
        else super.updatePossibleActions(possibleActions);
    }

    //set isOnPerimeter as true if has moved on a perimeter cell, otherwise set it to false
    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.getX()==0 || selectedTile.getX()==4 || selectedTile.getY()==0 || selectedTile.getY()==4) isOnPerimeter=true;
        else isOnPerimeter=false;

        return super.move(board, selectedWorker, selectedTile);
    }

    @Override
    public void setupDivinity(List<Action> possibleActions) {
        isOnPerimeter=false;
        super.setupDivinity(possibleActions);
    }
}
