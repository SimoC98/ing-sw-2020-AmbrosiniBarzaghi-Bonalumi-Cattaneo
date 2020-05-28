package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;


import java.util.ArrayList;
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
     * @param board
     * @param selectedWorker
     * @param selectedTile
     */
    @Override
    public List<ModelUpdate> build(Board board, Worker selectedWorker, Tile selectedTile) {
        if(Match.getUserAction().equals(Action.BUILDDOME)) {
            hasBuilt=true;
            selectedTile.setDome();


            List<ModelUpdate> ret = new ArrayList<>();

            List<Pair<Integer,Integer>> modifiedTiles = new ArrayList<>();
            modifiedTiles.add(new Pair<Integer, Integer>(selectedTile.getX(),selectedTile.getY()));

            ModelUpdate update = new ModelUpdate(Action.BUILD,selectedWorker,modifiedTiles);
            ret.add(update);

            return ret;
        }
        else {
            hasBuilt=true;
            return super.build(board,selectedWorker,selectedTile);
        }
    }

    /**
     * If the player has not built yet, {@link Action#BUILDDOME} is added to their list of possible actions
     * @param possibleActions initial list of possible actions
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

    @Override
    public void setupDivinity(List<Action> possibleActions) {

        hasBuilt=false;
    }
}