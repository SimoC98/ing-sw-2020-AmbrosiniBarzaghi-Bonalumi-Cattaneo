package it.polimi.ingsw.clientView;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardRepresentation {

    protected final int boardDimension = 5;
    private int [][] board;
    //TODO: add another matrix for seeing domes position and keeping the level information
    private Map<String, PlayerRepresentation> players;
    private Map<String, String> divinitiesDescriptions;

    public BoardRepresentation() {
        board = new int[boardDimension][boardDimension];
        for(int i=0; i<boardDimension; i++)
            for(int j=0; j<boardDimension; j++)
                board[i][j] = 0;
        players = new HashMap<>();
    }

    public void addPlayer(String player, Color color) {
        players.put(player, new PlayerRepresentation(player, color));
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

    public Map<String, String> getDivinities() {
        return divinitiesDescriptions;
    }

    public void setDivinities(Map<String, String> divinitiesDescriptions) {
        this.divinitiesDescriptions = divinitiesDescriptions;
    }

    public Color isThereAWorker(int x, int y) {
        for(PlayerRepresentation player : getPlayersList()){
            for(Pair<Integer, Integer> worker : player.getWorkers()){
                if(worker.equals(new Pair<>(x, y)))
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
        if(action == Action.BUILDDOME || board[x][y]==3)
            board[x][y] += 4;
        else //if(action == Action.BUILD)
            board[x][y] += 1;
    }

    public void setLoser(String username){
        players.get(username).setLoser();

        players.remove(username);
    }

    public void setWinner(String username){
        for(PlayerRepresentation player : getPlayersList()) {
            if(! player.getUsername().equals(username))
                player.setLoser();
            else
                player.setWinner();
        }
    }
}
