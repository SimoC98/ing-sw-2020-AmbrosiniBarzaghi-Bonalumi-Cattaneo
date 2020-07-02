package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

/**
 * Additional layer other divinities are encapsulated into if Athena moved up in her turn.
 * <p>
 * If the player with Athena moves up, this layer is applied to the other players, preventing
 * their workers from moving up. It is achieved modifying {@code legalMove}
 */
public class BlockLevelUp extends DivinityDecoratorWithEffects {

    public BlockLevelUp() { super(); }

    public BlockLevelUp(Divinity divinity) {
        super(divinity);
    }

    /**
     * New legalMove
     * @param board current {@link Board}
     * @param selectedWorker {@link Worker} whose move is verified
     * @param selectedTile {@link Tile} to check
     * @return {@code false} if a {@link Worker} is trying to ascend
     */
    @Override
    public boolean legalMove(Board board, Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.getLevel()>selectedWorker.getPositionOnBoard().getLevel()) {
            return false;
        }
        return super.legalMove(board,selectedWorker, selectedTile);
    }
}
