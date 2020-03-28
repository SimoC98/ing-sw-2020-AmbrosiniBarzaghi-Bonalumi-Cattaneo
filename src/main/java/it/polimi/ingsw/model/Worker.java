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

    public Tile getPositionOnBoard() {
        return this.positionOnBoard;
    }
}
