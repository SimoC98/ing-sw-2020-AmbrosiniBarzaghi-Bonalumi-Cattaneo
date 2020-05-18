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
     * It increase a tile's level and places a dome once it reaches level 3, calling {@link Tile#setDome()}
     */
    public void increaseLevel() {
        if(level < 3)
            level++;
        else if(level == 3)
            setDome();

    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getLevel() { return level; }

    /**
     * @return Returns {@code true} if there is a dome an a tile
     */
    public Boolean isDome() { return isDome; }

    /**
     * @return Returns {@code true} if a {@link Worker} is on the tile
     */
    public Boolean isOccupied() { return isOccupied; }

    /**
     * @return The {@link Worker} on this tile or {@code null} if it is unoccupied
     */
    public Worker getWorker() { return worker; }

    public void setDome() { isDome = true; }

    /**
     * Sets a tile free, removing the {@link Worker}'s reference
     * and stating that it is unoccupied
     */
    public void free() {
        isOccupied = false;
        worker = null;
    }

    /**
     * Method to occupy a tile with a worker
     *@param worker {@link Worker} associated to this tile
     */
    public void setWorker(Worker worker) {
        this.worker = worker;
        isOccupied = true;
    }

    /**
     * @return {@code true} if the tile corresponding to the input coordinates is adjacent to the current one
     */
    public boolean isAdjacent(int tx, int ty){
        return (Math.abs(tx-x)<=1) && (Math.abs(ty-y)<=1);
    }

}
