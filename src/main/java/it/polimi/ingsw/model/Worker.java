package it.polimi.ingsw.model;

public class Worker {
    private Tile positionOnBoard;
    //private static Player player;

    public Worker() {
        this.positionOnBoard = null;
        //this.player = null;
    }

    public Worker(Tile initialPosition) {
        this.positionOnBoard = initialPosition;
        //this.player = player;
    }

    public void move(Tile t){
        t.occupy();
        t.setWorker(this);
        positionOnBoard = t;
    }

   public boolean legalMove(Tile t){return true;}

    public void build (Tile t){}

    public boolean legalBuild(Tile t){return true;}

    public Tile getPositionOnBoard() {
        return this.positionOnBoard;
    }

    public void setPositionOnBoard(Tile t) {
        this.positionOnBoard = t;
    }

//    public void getPlayer(){
//    public void getPlayer(){
//        return player;
//    }
}
