package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;
import it.polimi.ingsw.model.update.MoveUpdate;

import java.util.List;

/**
 * Parent class for gods that can move on opponents
 */
public class MoveOnOpponent extends DivinityDecoratorWithEffects {

    public MoveOnOpponent() {
        super();
    }

    public MoveOnOpponent(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        return super.move(board,selectedWorker, selectedTile);
    }

    /**
     * Alters the method so that a worker can move on an opponent's worker
     * @param board
     * @param selectedWorker
     * @param selectedTile
     * @return {@code true} if the base move is valid and the worker is trying to  move on an enemy worker
     */
    @Override
    public boolean legalMove(Board board,Worker selectedWorker, Tile selectedTile) {
       if(selectedTile.isOccupied()) {
           if(!selectedTile.getWorker().getPlayer().equals(selectedWorker.getPlayer())) {
               if(selectedWorker.getPositionOnBoard().isAdjacent(selectedTile.getX(),selectedTile.getY()) && selectedWorker.getPositionOnBoard().getLevel()-selectedTile.getLevel()>=-1) return true;
           }
           else {
               return false;
           }
       }

       return super.legalMove(board,selectedWorker,selectedTile);
    }
}
