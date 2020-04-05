package it.polimi.ingsw.model;


import it.polimi.ingsw.model.exceptions.InvalidBuildException;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;

public class Worker {
    private Tile positionOnBoard;
    private final Player player;

    public Worker() {
        this.positionOnBoard = null;
        this.player = null;
    }

    public Worker(Tile initialPosition, Player player) {
        this.positionOnBoard = initialPosition;
        positionOnBoard.setWorker(this);
        this.player = player;
    }

    public void move(Tile t) throws InvalidMoveException {
        if(!legalMove(t)) {
            throw new InvalidMoveException();
        }

        if(isWinner(t)) {
            player.setWinner();
        }
        positionOnBoard.free();

        t.setWorker(this);

        positionOnBoard = t;
    }

   public boolean legalMove(Tile t){
        if(t.isDome() || t.isOccupied() || t.getLevel()-positionOnBoard.getLevel()>1 || t == this.positionOnBoard || !positionOnBoard.isAdjacent(t.getX(), t.getY())) return false;
        return true;
   }

    public void build (Tile t) throws InvalidBuildException {
        if(!legalBuild(t)) {
            throw new InvalidBuildException();
        }
        t.increaseLevel();
    }

    public boolean legalBuild(Tile t){
        if(t.isDome() || t.isOccupied() || !positionOnBoard.isAdjacent(t.getX(),t.getY())) return false;
        else return true;
    }

    public Tile getPositionOnBoard() {
        return this.positionOnBoard;
    }

    public void setPositionOnBoard(Tile t) {
        this.positionOnBoard = t;
    }

    public Player getPlayer() {
        return player;
    }



    public boolean isWinner(Tile t) {
        if(positionOnBoard.getLevel()==2 && t.getLevel()==3) {
            return true;
        }
        else return false;
    }


}

