package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;


import java.util.ArrayList;
import java.util.List;

/**
 * Athena prevents other players from moving up during their next turns, if she has moved up.
 * This effects lasts until her next turn.
 * <p>
 * To achieve such condition every opponent's divinity is decorated with another player that modifies {@code legalMove}
 */
public class SetEffectOnOpponent extends DivinityDecoratorWithEffects {
    private boolean hasMovedUp;
    private String playerUsername;

    public SetEffectOnOpponent() {
        super();
    }

    public SetEffectOnOpponent(Divinity divinity) {
        super(divinity);
    }

    /**
     * If the worker goes to a higher level than their current one, other players' divinities are decorated
     * with a class that blocks their ascension
     */
    @Override
    public void move(Board board,Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.getLevel()== 1 + selectedWorker.getPositionOnBoard().getLevel()) {
            hasMovedUp = true;
        }
        super.move(board,selectedWorker, selectedTile);
    }


    /**
     * If Athena had moved up during her previous turn, all the affected divinities
     * needs to get rid of the "blocking" layer. Such condition is checked through a flag
     */
    @Override
    public void setupDivinity(List<Action> possibleActions) {
        hasMovedUp = false;
        super.setupDivinity(possibleActions);
    }

    @Override
    public boolean hasSetEffectOnOpponentWorkers() {
        if(hasMovedUp) return true;
        else return super.hasSetEffectOnOpponentWorkers();
    }

    @Override
    public void setEffectOnOpponentWorkers(Player opponentPlayer) {
        opponentPlayer.setDivinity(new BlockLevelUp(opponentPlayer.getDivinity()));
        super.setEffectOnOpponentWorkers(opponentPlayer);
    }

    public boolean hasMovedUp() {
        return hasMovedUp;
    }
}
