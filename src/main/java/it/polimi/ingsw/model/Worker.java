package it.polimi.ingsw.model;


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
        if(t.isDome() || t.isOccupied() || t.getLevel()-positionOnBoard.getLevel()>1) return false;
        else return true;
   }

    public void build (Tile t){
         t.increaseLevel();
    }

    public boolean legalBuild(Tile t){
        if(t.isDome() || t.isOccupied()) return false;
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
    }{

    }

    public boolean isWinner(Tile t) {
        if(positionOnBoard.getLevel()==2 && t.getLevel()==3) {
            return true;
        }
        else return false;
    }



}
