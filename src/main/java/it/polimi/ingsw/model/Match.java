package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Board board;
    private Action userAction;  //soluzione momentanea

    public Match() {  }

    public Match(Board board) {
        this.board = board;
    }

    public Match(Board board, List<String> users, List<Color> colors) {
        this.board = board;
        players = new ArrayList<>();
        for(int i=0; i<users.size();i++) {
            Player newPlayer = new Player(users.get(i),colors.get(i));
            players.add(newPlayer);
        }
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

    public Action getUserAction() {
        return userAction;
    }

    public void setUserAction(Action userAction) {
        this.userAction = userAction;
    }
}
