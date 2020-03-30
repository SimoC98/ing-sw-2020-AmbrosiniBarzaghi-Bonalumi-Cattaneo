package it.polimi.ingsw.model;

public class Tile {
    private final int x;
    private final int y;
    private int level;
    private Boolean isDome;
    private Boolean isOccupied;
    private Worker worker;


    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        level = 0;
        isDome = false;
        isOccupied = false;
        worker = null;
    }

    /**
     * places a dome if it reaches level 3
     */
    public void increaseLevel()
    {
        if(isDome() || isOccupied) /*throw exception*/ return;
        else {
            if(level < 3)
                level++;
            else if(level == 3)
                setDome();
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getLevel() { return level; }

    public Boolean isDome() { return isDome; }

    public Boolean isOccupied() { return isOccupied; }

    /**
     *
     * @return the worker on this tile or null if is free
     */
    public Worker getWorker() { return worker; }

    public void setDome() { isDome = true; }


    public void free() {
        isOccupied = false;
        worker = null;
    }

    /**
     *
     * associate this tile to @param worker and set this tile as occupied
     */
    public void setWorker(Worker worker) {
        if(isOccupied || isDome) //throw exception
        this.worker = worker;
        isOccupied = true;
    }
}
