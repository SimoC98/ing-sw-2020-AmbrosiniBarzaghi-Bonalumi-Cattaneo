package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.Set;

public class BuildDomeEverywhere extends DivinityDecoratorWithEffects {
    private boolean hasBuilt;

    public BuildDomeEverywhere(Divinity divinity) {
        super(divinity);
    }

    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        if(Game.getMatch().getUserAction().equals(Action.BUILDDOME)) {
            selectedTile.setDome();
        }
        else {
            super.build(selectedWorker,selectedTile);
        }
    }

    @Override
    public Set<Action> updatePossibleActions(Set<Action> possibleActions) {
        if(!hasBuilt) {
            possibleActions.add(Action.BUILDDOME);
        }
        return super.updatePossibleActions(possibleActions);
    }

    @Override
    public void setDivinity(Divinity decoratedDivinity) {
        hasBuilt = false;
        super.setDivinity(decoratedDivinity);
    }
}