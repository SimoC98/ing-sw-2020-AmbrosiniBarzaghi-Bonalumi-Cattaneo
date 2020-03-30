package it.polimi.ingsw.model;

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

    public void move(Tile t){
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
    }



}
