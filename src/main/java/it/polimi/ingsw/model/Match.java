package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private ArrayList<Player> players;
    private Player currentPlayer;

    public ArrayList<Player> getPlayers() {
        return new ArrayList<Player>(players);
    }

    public String getCurrentPlayerUsername() {
        return currentPlayer.getUsername();
    }
}
