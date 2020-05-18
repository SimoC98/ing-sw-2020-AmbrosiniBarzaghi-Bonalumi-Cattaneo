package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;


import java.util.List;

/**
 * Atlas is able to build a dome instead of a regular block during his
 * build phase, complying with the basic rules of {@link Worker#build}
 */
public class BuildDomeEverywhere extends DivinityDecoratorWithEffects {
    private boolean hasBuilt;
    private boolean hasBuiltDome;

    public BuildDomeEverywhere() { super(); }

    public BuildDomeEverywhere(Divinity divinity) {
        super(divinity);
    }

    /**
     * Modified {@code build} that builds a dome if the player chooses to do so.
     * The choice is done in {@link Match}
     */
    @Override
    public void build(Board board,Worker selectedWorker, Tile selectedTile) {
        if(Match.getUserAction().equals(Action.BUILDDOME)) {
            hasBuiltDome=true;
            selectedTile.setDome();
        }
        else {
            super.build(board,selectedWorker,selectedTile);
        }
    }

    /**
     */
    @Override
    public void updatePossibleActions(List<Action> possibleActions) {
        if(!hasBuilt && !hasBuiltDome) {
            possibleActions.add(Action.BUILDDOME);
        }
        super.updatePossibleActions(possibleActions);
    }

    @Override
    public void setDivinity(Divinity decoratedDivinity) {
        hasBuilt = false;
        super.setDivinity(decoratedDivinity);
    }

    @Override
    public void setupDivinity(List<Action> possibleActions) {
        hasBuiltDome=false;
        hasBuilt=false;
    }
}