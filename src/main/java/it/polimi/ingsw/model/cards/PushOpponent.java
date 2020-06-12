package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.update.ModelUpdate;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents Minotaur, whose effect is to push an opponent on the direction he is moving,
 * if the tile the opponent is to be occupying is accessible.
 * <p>
 * {@code legalMove} and {@code move} are the functions affected in this class: has to
 * perform several tests to verify the possibility of movement; {@link PushOpponent(Worker, Tile)} has to access the {@link it.polimi.ingsw.model.Match}
 *in order to retrieve and move the opponent's {@link Worker}
 */
public class PushOpponent extends MoveOnOpponent {

    public PushOpponent() { super(); }

    public PushOpponent(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    /**
     * The control verify that if the selected tile contains an opponent worker, then when he will be moved, he will not
     * be moved out of the {@link it.polimi.ingsw.model.Board}, on a dome or on another worker
     * @param board
     * @param selectedWorker
     * @param selectedTile
     * @return {@code true} if the move is not illegal
     */
    @Override
    public boolean legalMove(Board board,Worker selectedWorker, Tile selectedTile) {
        boolean moveOnOpponent = super.legalMove(board,selectedWorker,selectedTile);
        int dx = selectedTile.getX() - selectedWorker.getPositionOnBoard().getX();
        int dy = selectedTile.getY() - selectedWorker.getPositionOnBoard().getY();

        if(moveOnOpponent) {
            if(selectedTile.isOccupied()) {
                if(selectedTile.getX()+dx>4 || selectedTile.getX()+dx<0 || selectedTile.getY()+dy>4 || selectedTile.getY()+dy<0 || board.getTile(selectedTile.getX()+dx,selectedTile.getY()+dy).isOccupied() || board.getTile(selectedTile.getX()+dx,selectedTile.getY()+dy).isDome()) {
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    /**
     * Moves both the current player's worker and the opponent one if the selected tile is occupied by the latter
     * @param board
     * @param selectedWorker
     * @param selectedTile
     * @return List of tiles to update on the UI representations
     */
    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        int dx = selectedTile.getX() - selectedWorker.getPositionOnBoard().getX();
        int dy = selectedTile.getY() - selectedWorker.getPositionOnBoard().getY();

        List<ModelUpdate> ret = new ArrayList<>();
        //List<Tile> modifiedTiles = new ArrayList<>();
        List<Pair<Integer,Integer>> modifiedTiles = new ArrayList<>();
        ModelUpdate update = null;

        if(selectedTile.isOccupied()) {
            Tile pushOpponentTile = board.getTile(selectedTile.getX()+dx,selectedTile.getY()+dy);

            modifiedTiles.add(new Pair<>(selectedTile.getX(),selectedTile.getY()));
            modifiedTiles.add(new Pair<>(pushOpponentTile.getX(),pushOpponentTile.getY()));
            update = new ModelUpdate(Action.MOVE,selectedTile.getWorker(),new ArrayList<>(modifiedTiles));

            pushOpponentTile.setWorker(selectedTile.getWorker());
            selectedTile.getWorker().setPositionOnBoard(pushOpponentTile);
            selectedTile.free();
        }


        ret = super.move(board,selectedWorker,selectedTile);

        if(update!=null) {
            ret.add(0,update);
        }

        return ret;
    }
}
