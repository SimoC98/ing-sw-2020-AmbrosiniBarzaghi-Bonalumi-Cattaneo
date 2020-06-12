package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.List;

/**
 * Pan's player can also win if his worker drops by two level
 */
public class WinByDropTwoLevel extends DivinityDecoratorWithEffects {

    public WinByDropTwoLevel() { super(); }

    public WinByDropTwoLevel(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    /**
     * Verifies if the worker will perform a winning move before moving
     * @param selectedTile
     * @param selectedWorker
     * @return {@code true} if Pan's winning condition is verified
     */
    @Override
    public boolean isWinner(Worker selectedWorker, Tile selectedTile) {
        int levelDifference = selectedWorker.getPositionOnBoard().getLevel() - selectedTile.getLevel();
            if (levelDifference>=2)
                return true;
            else
                return super.isWinner(selectedWorker, selectedTile);
    }

}
