package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Phase;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;

public class BuildBeforeAndAfter extends DivinityDecoratorWithEffects {
    private boolean hasMoved;
    private boolean hasBuiltBefore;

    public BuildBeforeAndAfter(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        if(hasMoved==false) {
            hasMoved=true;
        }
        super.build(selectedWorker,selectedTile);
    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        if(hasBuiltBefore==true) {
            if(selectedTile.getLevel()==1 + selectedWorker.getPositionOnBoard().getLevel()) {
                return false;
            }
        }
        return super.legalMove(selectedWorker,selectedTile);
    }

    @Override
    public void setupDivinity(List<Phase> possibleActions) {
        hasMoved=false;
        hasBuiltBefore = false;
        possibleActions.add(Phase.BUILD);
        super.setupDivinity(possibleActions);
    }
}
