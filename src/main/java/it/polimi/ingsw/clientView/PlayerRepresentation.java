package it.polimi.ingsw.clientView;

import it.polimi.ingsw.model.Color;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepresentation {

    private final String username;
    private Color color;
    private boolean hasWon, hasLost;
    private List<Pair<Integer, Integer>> workers;

    public PlayerRepresentation(String username, Color color) {
        this.username = username;
        this.color = color;
        hasWon = false;
        hasLost = false;
        workers = new ArrayList<>();

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

    public void setWinner() {
        this.hasWon = true;
    }

    public void setLoser() {
        this.hasLost = true;
    }

    public List<Pair<Integer, Integer>> getWorkers() {
        return workers;
    }

    public void addWorker(int x, int y){
        workers.add(new Pair<>(x,y));
    }

//    public Pair<Integer, Integer> getWorker(int x, int y){
//        Pair<Integer, Integer> goal = new Pair<>(x,y);
//
//        for(int i=0; i<workers.size(); i++){
//            if(workers.get(i) == goal)
//                return workers.get(i);
//        }
//
//        return null;
//    }

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
