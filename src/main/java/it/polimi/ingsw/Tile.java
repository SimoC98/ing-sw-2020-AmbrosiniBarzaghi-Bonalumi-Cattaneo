package it.polimi.ingsw;

public class Tile {
    private final int x;
    private final int y;
    private int level;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        level = 0;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}
