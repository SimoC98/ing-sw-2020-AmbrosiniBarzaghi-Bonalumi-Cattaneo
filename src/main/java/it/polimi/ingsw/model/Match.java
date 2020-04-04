package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Board board;

    public Match(Board board) {
        this.board = board;
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<Player>(players);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }
}
