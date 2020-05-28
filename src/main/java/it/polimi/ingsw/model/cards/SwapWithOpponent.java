package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import it.polimi.ingsw.model.update.ModelUpdate;
import it.polimi.ingsw.model.update.MoveUpdate;


import java.util.ArrayList;
import java.util.List;

/**
 * When this God, Apollo, moves onto an opponent tile, his worker and the opponent's swap their positions
 * <p>
 * {@code legalMove} and {@code move} are the functions affected in this class: {@link SwapWithOpponent#legalMove(Board,Worker, Tile)} has to
 * perform several tests to verify the possibility of movement; {@link PushOpponent#move(Board,Worker, Tile)} has to access the {@link it.polimi.ingsw.model.Match}
 * in order to retrieve and move the opponent's {@link Worker}
 */
public class SwapWithOpponent extends MoveOnOpponent {

    public SwapWithOpponent() {
        super();
    }

    public SwapWithOpponent(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    /**
     * If the selected tile is occupied by an opponent worker, such worker and his tile are
     * saved and the swap takes place. Otherwise it is a simple move
     * @param board
     * @param selectedWorker
     * @param selectedTile
     * @return
     */
    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        if (selectedTile.getWorker() != null) {
            List<ModelUpdate> ret = new ArrayList<>();
            //List<Tile> modifiedTiles = new ArrayList<>();
            List<Pair<Integer,Integer>> modifiedTiles = new ArrayList<>();

            Worker opponentWorker = selectedTile.getWorker();
            Tile myActualTile = selectedWorker.getPositionOnBoard();

            //first moveUpdate --> opponentWorker from his tile to selectedWorker tile
            modifiedTiles.add(new Pair<>(selectedTile.getX(),selectedTile.getY()));
            modifiedTiles.add(new Pair<>(selectedWorker.getPositionOnBoard().getX(),selectedWorker.getPositionOnBoard().getY()));
            ret.add(new ModelUpdate(Action.MOVE,opponentWorker,new ArrayList<>(modifiedTiles)));

            modifiedTiles.clear();
            selectedTile.free();

            //second moveUpdate --> selectedWorker from his tile to selectedTile
            modifiedTiles.add(new Pair<>(selectedWorker.getPositionOnBoard().getX(),selectedWorker.getPositionOnBoard().getY()));
            modifiedTiles.add(new Pair<>(selectedTile.getX(),selectedTile.getY()));
            try {
                selectedWorker.move(selectedTile);
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
            myActualTile.setWorker(opponentWorker);
            opponentWorker.setPositionOnBoard(myActualTile);

            ret.add(new ModelUpdate(Action.MOVE,selectedWorker,new ArrayList<>(modifiedTiles)));

            return ret;

        }else{
            return super.move(board,selectedWorker, selectedTile);
        }
    }

    /**
     * An {@code ArrayList} is created to prevent the player from committing a rare losing condition:
     * The player swaps on a {@link Tile} where he will not be able to build; i.e. the worker is surrounded by domes
     * or other workers.
     * @param board
     * @param selectedWorker
     * @param selectedTile
     * @return {@code true} if the worker moves on a free tile or an occupied tile and will be able to build
     */
    @Override
    public boolean legalMove(Board board,Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.isOccupied()) {
            List<Tile> l = board.getAdjacentTiles(selectedTile);
            List<Tile> l2 = new ArrayList<>();
            for(int i=0; i<l.size(); i++) {
                Tile t = l.get(i);
                if(!t.isOccupied() && !t.isDome()) l2.add(t);
            }
            if(l2.size()==0) return false;
        }
        return super.legalMove(board,selectedWorker, selectedTile);
    }


}
