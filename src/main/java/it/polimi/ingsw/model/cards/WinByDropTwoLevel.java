package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;
import it.polimi.ingsw.model.update.MoveUpdate;

import java.util.List;

/**
 * Pan's player can also win if his worker drops by two level
 */
public class WinByDropTwoLevel extends AdditionalWinCondition {

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
    protected boolean isWinner(Worker selectedWorker, Tile selectedTile) {
        int levelDifference = selectedWorker.getPositionOnBoard().getLevel() - selectedTile.getLevel();
        return levelDifference >= 2;
    }

    /**
     * This methods also needs to check that if the worker is dropping by two levels and in that case, it calls {@link Player#setWinner()}
     * @param board
     * @param selectedWorker
     * @param selectedTile
     * @return
     */
    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        if(isWinner(selectedWorker, selectedTile)) {
            selectedWorker.getPlayer().setWinner();
        }
        return super.move(board,selectedWorker, selectedTile);
    }

    @Override
    public List<ModelUpdate> build(Board board, Worker selectedWorker, Tile selectedTile) {
        return super.build(board,selectedWorker, selectedTile);
    }

    @Override
    public boolean legalMove(Board board,Worker selectedWorker, Tile selectedTile) {
        return super.legalMove(board,selectedWorker, selectedTile);
    }

    @Override
    public boolean legalBuild(Board board,Worker selectedWorker, Tile selectedTile) {
        return super.legalBuild(board,selectedWorker, selectedTile);
    }

    @Override
    public Divinity getDivinity() {
        return super.getDivinity();
    }

}
