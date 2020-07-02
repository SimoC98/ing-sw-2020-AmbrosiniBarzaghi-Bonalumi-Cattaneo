package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;


/**
 * Additional layer other divinities are encapsulated into if Hera is in game.
 * <p>
 * Every winning move is blocked if the worker performing the action is on the perimeter
 */
public class BlockWinOnPerimeter extends DivinityDecoratorWithEffects  {

    public BlockWinOnPerimeter() {
        super();
    }

    public BlockWinOnPerimeter(Divinity divinity) {
        super(divinity);
    }

    /**
     * Checks if a tile is on the perimeter
     * @param selectedTile tile to be checked
     * @return true if the tile is perimetral
     */
    public boolean isOnPerimeter(Tile selectedTile){
        int x = selectedTile.getX();
        int y = selectedTile.getY();
        return x == 0 || x == 4 || y == 0 || y == 4;
    }

    /**
     * New win condition
     * @param selectedWorker current worker performing an action
     * @param selectedTile {@link Tile} the worker is going to perform the action on
     * @return false even when the winning condition takes place on the perimeter
     */
    @Override
    public boolean isWinner(Worker selectedWorker,Tile selectedTile){
        if(isOnPerimeter(selectedTile)) return false;
        else return super.isWinner(selectedWorker, selectedTile);
    }

}
