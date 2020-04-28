package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

/**
 * Pan's player can also win if his worker drops by two level
 */
public class WinByDropTwoLevel extends AdditionalWinCondition {

    public WinByDropTwoLevel() { super(); }

    public WinByDropTwoLevel(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    /**
     * @return {@code true} if Pan's winning condition is verified
     */
    @Override
    protected boolean isWinner(Worker selectedWorker, Tile selectedTile) {
        int levelDifference = selectedWorker.getPositionOnBoard().getLevel() - selectedTile.getLevel();
        return levelDifference >= 2;
    }

    /**
     * This methods also needs to check that if the worker is dropping by two levels and in that case, it calls {@link Player#setWinner()}
     */
    @Override
    public void move(Board board, Worker selectedWorker, Tile selectedTile) {
        if(isWinner(selectedWorker, selectedTile)) {
            selectedWorker.getPlayer().setWinner();
        }
        super.move(board,selectedWorker, selectedTile);
    }

    @Override
    public void build(Board board,Worker selectedWorker, Tile selectedTile) {
        super.build(board,selectedWorker, selectedTile);
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
