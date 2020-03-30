package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

public class BlockLevelUp extends DivinityDecoratorWithEffects {

    public BlockLevelUp(Divinity divinity) {
        super(divinity);
    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.getLevel()>selectedWorker.getPositionOnBoard().getLevel()) {
            return false;
        }
        return super.legalMove(selectedWorker, selectedTile);
    }
}
