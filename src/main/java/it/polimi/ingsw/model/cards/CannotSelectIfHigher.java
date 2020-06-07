package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

/**
 *
 */
public class CannotSelectIfHigher extends SetEffectOnOpponent{

    public CannotSelectIfHigher() {
        super();
    }

    public CannotSelectIfHigher(Divinity divinity) {
        super(divinity);
    }

    @Override
    public boolean hasSetEffectOnOpponentWorkers() {
        super.setEffect = true;
        return super.hasSetEffectOnOpponentWorkers();
    }

    /**
     * Method called in {@link Match#startNextTurn()} that decorates the other players.
     * @param opponentPlayer
     */
    @Override
    public void setEffectOnOpponentWorkers(Player opponentPlayer) {
        opponentPlayer.setDivinity(new BlockSelectionWhenHigher(opponentPlayer.getDivinity()));
        super.setEffectOnOpponentWorkers(opponentPlayer);
    }

}
