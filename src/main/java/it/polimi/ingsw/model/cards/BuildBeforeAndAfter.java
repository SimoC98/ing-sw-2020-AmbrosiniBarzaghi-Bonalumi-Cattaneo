package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;
import java.util.Set;

public class BuildBeforeAndAfter extends DivinityDecoratorWithEffects {
    private boolean hasMoved;
    private boolean hasBuiltBefore;

    public BuildBeforeAndAfter() { super(); }

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

    /**
     * is added to the @param possibleActions BUILD: the current player can choose to build as first action
     */
    @Override
    public void setupDivinity(Set<Action> possibleActions) {
        hasMoved=false;
        hasBuiltBefore = false;
        possibleActions.add(Action.BUILD);
        super.setupDivinity(possibleActions);
    }
}
