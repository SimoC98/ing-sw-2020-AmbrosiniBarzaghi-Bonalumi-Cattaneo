package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.ArrayList;
import java.util.List;


/**
 * Additional layer other divinities are encapsulated into if Hera is in game.
 * <p>
 * Every winning move is blocked if the worker performing the action is on the perimeter
 */
public class BlockWinOnPerimeter extends DivinityDecoratorWithEffects {

    public BlockWinOnPerimeter() {
        super();
    }

    public BlockWinOnPerimeter(Divinity divinity) {
        super(divinity);
    }

    /**
     * Checks if a tile is on the perimeter
     * @param selectedTile
     * @return
     */
    public boolean isOnPerimeter(Tile selectedTile){
        int x = selectedTile.getX();
        int y = selectedTile.getY();
        return x == 0 || x == 4 || y == 0 || y == 4;
    }

    /**
     * The move is overwritten so that this decoration, which is outer than the one deriving from the divinity chosen
     * by the player, prevents the check of the win condition coming from the move on standard divinity; in other words,
     * this method bypasses the normal flow of other divinities writing its own move.
     * <p>
     * The overwrite happens only if the player chooses a tile on the edge of the board, otherwise the standard method
     * is called.
     * @param board
     * @param selectedWorker
     * @param selectedTile
     * @return
     */
    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        if(isOnPerimeter(selectedTile)) {
                List<ModelUpdate> ret = new ArrayList<>();
                List<Pair<Integer,Integer>> modifiedTiles = new ArrayList<>();

                try {
                    modifiedTiles.add(new Pair<>(selectedWorker.getPositionOnBoard().getX(),selectedWorker.getPositionOnBoard().getY()));
                    modifiedTiles.add(new Pair<>(selectedTile.getX(),selectedTile.getY()));
                    ret.add(new ModelUpdate(Action.MOVE,selectedWorker,modifiedTiles));

                    selectedWorker.move(selectedTile);

                } catch (InvalidMoveException e) {
                    e.printStackTrace();
                }

                return ret;
            }
        else return super.move(board,selectedWorker,selectedTile);
    }
}
