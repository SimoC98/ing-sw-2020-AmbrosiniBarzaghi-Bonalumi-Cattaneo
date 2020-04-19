package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;

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

        }else{
            super.move(selectedWorker, selectedTile);
        }
    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.isOccupied()) {
            List<Tile> l = Game.getMatch().getBoard().getAdjacentTiles(selectedTile);
            List<Tile> l2 = new ArrayList<>();
            for(int i=0; i<l.size(); i++) {
                Tile t = l.get(i);
                if(!t.isOccupied() && !t.isDome()) l2.add(t);
            }
            if(l2.size()==0) return false;
        }
        return super.legalMove(selectedWorker, selectedTile);
    }


}
