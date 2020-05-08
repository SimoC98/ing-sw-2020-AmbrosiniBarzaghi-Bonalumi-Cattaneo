package it.polimi.ingsw.clientView;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardRepresentation {

    protected final int boardDimension = 5;
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
        Color color;
        switch(players.size()){
            case 0:
                color = Color.BLUE;
                break;

            case 1:
                color = Color.CREAM;
                break;

            default:
                color = Color.WHITE;
        }

        players.put(player, new PlayerRepresentation(player, color));
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

    public Color isThereAWorker(int x, int y) {
        Pair<Integer, Integer> goal = new Pair<>(x, y);
        for(PlayerRepresentation player : getPlayersList()){
            for(Pair<Integer, Integer> worker : player.getWorkers()){
                if(worker.equals(goal))
                    return player.getColor();
            }
        }
        return null;
    }

    public void moveWorker(String username, int fromX, int fromY, int toX, int toY){
        players.get(username).moveWorker(fromX, fromY, toX, toY);
        //maybe can use return value
    }

    public void buildTile(int x, int y, Action action){
        if(action == Action.BUILD)
            board[x][y] += 1;
        else if(action == Action.BUILDDOME)
            board[x][y] = 4;
    }

    public void setLoser(String username){

    }

    public void setWinner(String username){

    }






    //MANCA UN BOTTO forse
}
