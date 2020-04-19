package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.DivinityDecoratorWithEffects;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

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
     * @return {@code false} if a {@link Worker} is trying to ascend
     */
    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.getLevel()>selectedWorker.getPositionOnBoard().getLevel()) {
            return false;
        }
        return super.legalMove(selectedWorker, selectedTile);
    }
}
