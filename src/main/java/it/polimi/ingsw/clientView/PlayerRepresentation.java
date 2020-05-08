package it.polimi.ingsw.clientView;




import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepresentation {

    private final String username;
    private boolean hasWon, hasLost;
    private List<Pair<Integer, Integer>> workers;

    public PlayerRepresentation(String username) {
        this.username = username;
        hasWon = false;
        hasLost = false;
        workers = new ArrayList<>();
    }

    public boolean hasWon() {
        return hasWon;
    }

    public boolean hasLost() {
        return hasLost;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public void setHasLost(boolean hasLost) {
        this.hasLost = hasLost;
    }

    public List<Pair<Integer, Integer>> getWorkers() {
        return workers;
    }

    public void addWorker(int x, int y){
        workers.add(new Pair<>(x,y));
    }
}
