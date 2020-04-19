package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.model.*;

import java.util.List;
import java.util.Set;

/**
 * This Goddess, Artemis, can move twice but not back to its initial space.
 * <p>
 * For this divinity it is required that {@code legalMove} checks
 * the player's second move selection and that {@code updatePossibleActions}
 * adds the possibility to end the move phase and thus beginning the build phase,
 * after the first move.
 */
public class MoveTwiceNotBack extends MoveTwice{

    public MoveTwiceNotBack() { super(); }

    public MoveTwiceNotBack(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    /**
     * @return {@code true} if the move is correct: it is the first one and in accordance to the game rules, or it is the second and on a different tile
     */
    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        if(getMoveCount()>0) {
            if(getFirstMovedTile().getX()==selectedTile.getX() && getFirstMovedTile().getY()==selectedTile.getY()) {
                return false;
            }
        }
        return super.legalMove(selectedWorker,selectedTile);
    }

    /**
     * After the first move, the player can build
     * @return {@code Set} updated with {@link Action#MOVE} when called after the first move, if a build has not happened
     */
    @Override
    public Set<Action> updatePossibleActions(Set<Action> possibleActions) {
        if(getMoveCount() == 1 && !isHasBuilt()) {
            List<Tile> l = Game.getMatch().getAvailableMoveTiles(Game.getMatch().getSelectedWorker());
            if(l.size()>0) possibleActions.add(Action.MOVE);
        }
        return super.updatePossibleActions(possibleActions);
    }

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        super.move(selectedWorker, selectedTile);
    }

    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        super.build(selectedWorker, selectedTile);
    }

    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        return super.legalBuild(selectedWorker, selectedTile);
    }

    @Override
    public Divinity getDivinity() {
        return super.getDivinity();
    }

    @Override
    public void setupDivinity(Set<Action> possibleActions) {
        super.setupDivinity(possibleActions);
    }
}
