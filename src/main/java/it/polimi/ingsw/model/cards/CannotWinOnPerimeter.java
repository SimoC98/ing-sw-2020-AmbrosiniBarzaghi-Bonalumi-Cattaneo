package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

/**
 * Hera prevent the other players from winning if they move onto a perimetral tile
 */
public class CannotWinOnPerimeter extends SetEffectOnOpponent{

    public CannotWinOnPerimeter() {
        super();
    }

    public CannotWinOnPerimeter(Divinity divinity) {
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
        opponentPlayer.setDivinity(new BlockWinOnPerimeter(opponentPlayer.getDivinity()));
        super.setEffectOnOpponentWorkers(opponentPlayer);
    }
}
