package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

import java.util.List;

/**
 * Atlas is able to build a dome instead of a regular block during his
 * build phase, complying with the basic rules of {@link Worker#build}
 */
public class BuildDomeEverywhere extends DivinityDecoratorWithEffects {
    private boolean hasBuilt;

    public BuildDomeEverywhere() { super(); }

    public BuildDomeEverywhere(Divinity divinity) {
        super(divinity);
    }

    /**
     * Modified {@code build} that builds a dome if the player chooses to do so.
     * The choice is done in {@link Match}
     */
    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        if(Game.getMatch().getUserAction().equals(Action.BUILDDOME)) {
            selectedTile.setDome();
        }
        else {
            super.build(selectedWorker,selectedTile);
        }
    }

    /**
     */
    @Override
    public void updatePossibleActions(List<Action> possibleActions) {
        if(!hasBuilt) {
            possibleActions.add(Action.BUILDDOME);
        }
        super.updatePossibleActions(possibleActions);
    }

    @Override
    public void setDivinity(Divinity decoratedDivinity) {
        hasBuilt = false;
        super.setDivinity(decoratedDivinity);
    }
}