package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.DivinityDecoratorWithEffects;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

public class MoveOnOpponent extends DivinityDecoratorWithEffects {

    public MoveOnOpponent() {
        super();
    }

    public MoveOnOpponent(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        super.move(selectedWorker, selectedTile);
    }

    /**
     * @param selectedWorker
     * @param selectedTile
     * @return true even if the selected tile is occupied by another Player's worker
     */
    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
       if(selectedTile.isOccupied()) {
           if(!selectedTile.getWorker().getPlayer().equals(selectedWorker.getPlayer())) {
               return true;
           }
           else {
               return false;
           }
       }

       return super.legalMove(selectedWorker,selectedTile);
    }
}
