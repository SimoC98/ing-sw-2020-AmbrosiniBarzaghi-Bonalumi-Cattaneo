package it.polimi.ingsw.model;

public class Worker {
    private Tile positionOnBoard;
    private static Player player;

    public Worker() {
        this.positionOnBoard = null;
        this.player = null;
    }


    public Worker(Tile initialPosition) {
        this.positionOnBoard = initialPosition;
        this.player = player;
    }

    public Tile getPositionOnBoard() {
        return this.positionOnBoard;
    }

    public Player getPlayer() {
        return player;
    }

    public void move(Tile destinationMoveTile) {
        //TODO
    }

    public void build(Tile destinationBuildTile) {
        //TODO
    }

    public boolean legalMove(Tile destinationMoveTile) {
        return true;
        //TODO
    }

    public boolean legalBuild(Tile destinationBuildTile) {
        return true;
        //TODO
    }

    public boolean isWinner(Tile t) {
        return true;
        //TODO
    }


}
