package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

/**
 * Decorator Pattern
 * the selected Worker can move into an opponent Worker's Tile, if their Worker can be forced one space straight backwards to an unoccupied Tile
 */
public class PushOpponent extends MoveOnOpponent {

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        boolean moveOnOpponent = super.legalMove(selectedWorker,selectedTile);
        int dx = selectedTile.getX() - selectedWorker.getPositionOnBoard().getX();
        int dy = selectedTile.getY() - selectedWorker.getPositionOnBoard().getY();

        if(moveOnOpponent) {
            if(selectedTile.isOccupied()) {
                if(selectedTile.getX()+dx>5 || selectedTile.getX()+dx<0 || selectedTile.getY()+dy>5 || selectedTile.getY()+dy<0 || Game.getMatch().getBoard().getTile(selectedTile.getX()+dx,selectedTile.getY()+dy).isOccupied()) {
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                return moveOnOpponent;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        int dx = selectedTile.getX() - selectedWorker.getPositionOnBoard().getX();
        int dy = selectedTile.getY() - selectedWorker.getPositionOnBoard().getY();

        if(selectedTile.isOccupied()) {
            Tile pushOpponentTile = Game.getMatch().getBoard().getTile(selectedTile.getX()+dx,selectedTile.getY()+dy);
            pushOpponentTile.setWorker(selectedTile.getWorker());
            selectedTile.getWorker().setPositionOnBoard(pushOpponentTile);
            selectedTile.free();
        }
        super.move(selectedWorker,selectedTile);
    }
}
