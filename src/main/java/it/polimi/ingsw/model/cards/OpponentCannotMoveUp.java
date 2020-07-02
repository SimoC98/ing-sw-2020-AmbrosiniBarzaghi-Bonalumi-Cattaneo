package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;


import java.util.List;

/**
 * Athena prevents other players from moving up during their next turns, if she has moved up.
 * This effects lasts until her next turn.
 * <p>
 * To achieve such condition every opponent's divinity is decorated with another player that modifies {@code legalMove}
 */
public class OpponentCannotMoveUp extends SetEffectOnOpponent{
    private boolean hasMovedUp;

    public OpponentCannotMoveUp() {
        super();
    }

    public OpponentCannotMoveUp(Divinity divinity) {
        super(divinity);
    }

    /**
     * If the worker goes to a higher level than their current one, other players' divinities are decorated
     * with a class that blocks their ascension
     * @param board current {@link Board}
     * @param selectedWorker Selected worker from the {@link Match}
     * @param selectedTile {@link Tile} to move onto
     * @return List of tiles to update on the UI representations
     */
    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.getLevel()== 1 + selectedWorker.getPositionOnBoard().getLevel()) {
            hasMovedUp = true;
        }
        return super.move(board,selectedWorker, selectedTile);
    }


    /**
     * If Athena had moved up during her previous turn, all the affected divinities
     * needs to get rid of the "blocking" layer. Such condition is checked through a flag
     * @param possibleActions list of possible actions to be modified
     */
    @Override
    public void setupDivinity(List<Action> possibleActions) {
        hasMovedUp = false;
        super.setupDivinity(possibleActions);
    }

    @Override
    public boolean hasSetEffectOnOpponentWorkers() {
        if(hasMovedUp) super.setEffect = true;
        return super.hasSetEffectOnOpponentWorkers();

    }

    @Override
    public void setEffectOnOpponentWorkers(Player opponentPlayer) {
        opponentPlayer.setDivinity(new BlockLevelUp(opponentPlayer.getDivinity()));
        super.setEffectOnOpponentWorkers(opponentPlayer);
    }

}
