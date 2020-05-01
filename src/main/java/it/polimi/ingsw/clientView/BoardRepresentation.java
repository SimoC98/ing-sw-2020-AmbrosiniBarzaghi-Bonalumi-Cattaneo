package it.polimi.ingsw.clientView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardRepresentation {

    private final int boardDimension = 5;
    private int [][] board;
    Map<String, PlayerRepresentation> players;

    public BoardRepresentation() {
        board = new int[boardDimension][boardDimension];
        for(int i=0; i<boardDimension; i++)
            for(int j=0; j<boardDimension; j++)
                board[i][j] = 0;
        players = new HashMap<>();
    }

    public void addPlayer(String player) {
        players.put(player, new PlayerRepresentation(player));
    }

    public void incTile(int x, int y){
//        if(board[x][y] <= 3)
            board[x][y]++;
    }

    public void addWorker(String username, int x, int y) {
        players.get(username).addWorker(x, y);
    }

    public int[][] getBoard() {
        return board;
    }

    public Map<String, PlayerRepresentation> getPlayersMap() {
        return players;
    }

    public List<PlayerRepresentation> getPlayersList() {
        return new ArrayList<>(players.values());
    }

    public List<String> getPlayersNames() {
        return new ArrayList<>(players.keySet());
    }

    public void moveWorker(){

    }




    //MANCA UN BOTTO
}
