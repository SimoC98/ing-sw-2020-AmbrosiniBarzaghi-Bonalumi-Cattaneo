package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

public class SwapWithOpponent extends MoveOnOpponent {

    public SwapWithOpponent() {
        super();
    }

    public SwapWithOpponent(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        if (selectedTile.getWorker() != null) {
            selectedWorker.getPositionOnBoard().setWorker(selectedTile.getWorker());
            selectedTile.getWorker().setPositionOnBoard(selectedWorker.getPositionOnBoard());
            selectedWorker.move(selectedTile);
        }else{
            super.move(selectedWorker, selectedTile);
        }
    }
}
