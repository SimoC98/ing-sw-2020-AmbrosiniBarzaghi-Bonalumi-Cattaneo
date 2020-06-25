package it.polimi.ingsw.clientView;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a player at the client side. It is a simplification of the model's player: it contains
 * the username, the color, two boolean to check if they lost or won, their workers' position through a pair of coordinates
 * and their divinity as a string.
 * It has setters and getters for every item.
 */
public class PlayerRepresentation {

    private final String username;
    private Color color;
    private boolean hasWon, hasLost;
    private List<Pair<Integer, Integer>> workers;
    private String divinity;

    public PlayerRepresentation(String username, Color color) {
        this.username = username;
        this.color = color;
        hasWon = false;
        hasLost = false;
        workers = new ArrayList<>();
        divinity = null;
    }

    public String getUsername() {
        return username;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public boolean hasLost() {
        return hasLost;
    }

    /**
     * Called when a player performed a winning condition
     */
    public void setWinner() {
        this.hasWon = true;
    }

    /**
     * Called when a player has fallen into a losing condition
     */
    public void setLoser() {
        this.hasLost = true;
    }

    public List<Pair<Integer, Integer>> getWorkers() {
        return workers;
    }

    /**
     * Used to change a worker's position or to initially place it. See {@link PlayerRepresentation#moveWorker(int, int, int, int)}
     * @param x worker's x coordinate
     * @param y worker's y coordinate
     */
    public void addWorker(int x, int y){
        workers.add(new Pair<>(x,y));
    }


    public void setDivinity(String divinity){
        this.divinity = divinity;
    }

    public String getDivinity() {
        return divinity;
    }

    /**
     * Called when moving a worker from a tile to another.
     * @param fromX initial x coordinate
     * @param fromY initial y coordinate
     * @param toX destination x coordinate
     * @param toY destination y coordinate
     * @return {@code boolean} indicating if the move was successful
     */
    public boolean moveWorker(int fromX, int fromY, int toX, int toY) {
        for(int i=0; i < workers.size(); i++) {
            if(workers.get(i).equals(new Pair(fromX, fromY))) {
                workers.remove(i);
                workers.add(new Pair(toX, toY));
                return true;
            }
        }

        return false;
    }
}
