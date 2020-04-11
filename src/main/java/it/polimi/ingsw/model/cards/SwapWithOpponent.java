package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;

import java.util.List;
import java.util.Set;

public class SwapWithOpponent extends MoveOnOpponent {

    public SwapWithOpponent() {
        super();
    }

    public SwapWithOpponent(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    /**
     * if the Tile selected is occupied by another player's Worker, the two Workers can be swapped
     * @param selectedWorker
     * @param selectedTile
     */
    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        if (selectedTile.getWorker() != null) {
            Worker opponentWorker = selectedTile.getWorker();
            Tile myActualTile = selectedWorker.getPositionOnBoard();
            selectedTile.free();
            try {
                selectedWorker.move(selectedTile);
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
            myActualTile.setWorker(opponentWorker);
            opponentWorker.setPositionOnBoard(myActualTile);
//            selectedWorker.getPositionOnBoard().setWorker(selectedTile.getWorker());
//            selectedTile.getWorker().setPositionOnBoard(selectedWorker.getPositionOnBoard());
//            selectedWorker.setPositionOnBoard(selectedTile);
//            selectedTile.setWorker(selectedWorker);
        }else{
            super.move(selectedWorker, selectedTile);
        }
    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        return super.legalMove(selectedWorker, selectedTile);
    }


}
