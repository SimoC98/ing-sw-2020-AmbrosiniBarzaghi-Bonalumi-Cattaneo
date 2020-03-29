package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

public class MoveOnOpponent extends DivinityDecoratorWithEffects{

    public MoveOnOpponent(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        super.move(selectedWorker, selectedTile);
    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        boolean stdLegalMoveReturn = super.legalMove(selectedWorker, selectedTile);

        if(!stdLegalMoveReturn && selectedTile.getWorker() != null)
            return true;
        else
            return stdLegalMoveReturn;
    }
}
