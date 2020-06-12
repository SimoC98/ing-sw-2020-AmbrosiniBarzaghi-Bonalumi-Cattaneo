package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

/**
 * Hypnus' effect is always active: at the beginning of an opponent's turn, they cannot select a worker if this
 * is on a higher level than the others of the same player.
 * <p>
 * To accomplish this condition the opponents' divinities are wrapped in an outer decoration, {@link BlockSelectionWhenHigher},
 * at the beginning of their turn. See {@link Match#startNextTurn()}
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
