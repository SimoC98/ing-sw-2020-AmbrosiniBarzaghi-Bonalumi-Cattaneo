package it.polimi.ingsw.model;

public class Tile {
    private final int x;
    private final int y;
    private int level;
    private Boolean isDomed;
    private Boolean isOccupied;
    private Worker worker;


    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        level = 0;
        isDomed = false;
        isOccupied = false;
        worker = null;
    }

    public void increaseLevel()
    {
        if(level < 3)
            level++;
        else if(level == 3)
            setDomed();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getLevel() { return level; }

    public Boolean isDomed() { return isDomed; }

    public Boolean isOccupied() { return isOccupied; }

    public Worker getWorker() { return worker; }

    public void setDomed() { isDomed = true; }

    public void occupy() { isOccupied = true; }

    public void free() { isOccupied = false; }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
