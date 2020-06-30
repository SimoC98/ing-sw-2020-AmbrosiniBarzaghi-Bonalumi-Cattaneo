package it.polimi.ingsw.Client;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Representation of the {@link it.polimi.ingsw.model.Board} on the client side. It is a simplification of the board:
 * it contains an array of integers instead of tiles and the workers are substituted by their colors.
 */
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

    /**
     * Adds a player to the map of players, saving their color (which is passed by the server)
     * @param player player's name
     * @param color player's color
     */
    public void addPlayer(String player, Color color) {
        players.put(player, new PlayerRepresentation(player, color));
    }

    /**
     * Adds a worker to the specified user
     * @param username owner of the worker added
     * @param x x coordinate of the worker
     * @param y y coordinate of the worker
     */
    public void addWorker(String username, int x, int y) {
        players.get(username).addWorker(x, y);
    }

    public int[][] getBoard() {
        return board;
    }

    /**
     * Returns the map of the players saved with their names paired to their representation on the client
     * @return map of string and {@link PlayerRepresentation}
     */
    public Map<String, PlayerRepresentation> getPlayersMap() {
        return players;
    }

    /**
     * List of {@link PlayerRepresentation}
     * @return Players' representations
     */
    public List<PlayerRepresentation> getPlayersList() {
        return new ArrayList<>(players.values());
    }

    public List<String> getPlayersNames() {
        return new ArrayList<>(players.keySet());
    }

    /**
     * Returns the map of the divinities' names and their descriptions
     * @return map of two strings
     */
    public Map<String, String> getDivinities() {
        return divinitiesDescriptions;
    }

    public void setDivinities(Map<String, String> divinitiesDescriptions) {
        this.divinitiesDescriptions = divinitiesDescriptions;
    }

    /**
     * Checks if there is a worker on a tile and in such case it returns its color, retrieved from the {@link PlayerRepresentation}
     * @param x x coordinate to check
     * @param y y coordinate ot check
     * @return {@link Color} or {@code null}
     */
    public Color isThereAWorker(int x, int y) {
        for(PlayerRepresentation player : getPlayersList()){
            for(Pair<Integer, Integer> worker : player.getWorkers()){
                if(worker.equals(new Pair<>(x, y)))
                    return player.getColor();
            }
        }
        return null;
    }

    /**
     * Moves the worker of the specified player from a position to the other
     * @param username Player moving a worker
     * @param fromX Initial position of the worker
     * @param fromY Initial position of the worker
     * @param toX Move destination
     * @param toY Move destination
     */
    public void moveWorker(String username, int fromX, int fromY, int toX, int toY){
        players.get(username).moveWorker(fromX, fromY, toX, toY);
    }

    /**
     * Updates the client's board to build on a tile. The building player is not specified ever since such process is
     * validated on the model, so that it is unnecessary to check the validity here.
     * If the construction reaches level 4, a dome is placed instead.
     * @param x x coordinate to build on
     * @param y y coordinate to build on
     * @param action {@link Action#BUILD} or {@link Action#BUILDDOME}(for Atlas)
     */
    public void buildTile(int x, int y, Action action){
        if(action == Action.BUILDDOME || board[x][y]==3)
            board[x][y] += 4;
        else //if(action == Action.BUILD)
            board[x][y] += 1;
    }

    /**
     * Sets the specified user as loser and removed it form the map of players
     * @param username losing player
     */
    public void setLoser(String username){
        players.get(username).setLoser();

        players.remove(username);
    }

    /**
     * It sets the specified player as winner and sets the other to losers.
     * @param username winning player
     */
    public void setWinner(String username){
        for(PlayerRepresentation player : getPlayersList()) {
            if(! player.getUsername().equals(username))
                player.setLoser();
            else
                player.setWinner();
        }
    }
}
