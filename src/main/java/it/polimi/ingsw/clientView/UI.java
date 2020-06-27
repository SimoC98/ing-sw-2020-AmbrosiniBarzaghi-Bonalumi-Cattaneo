package it.polimi.ingsw.clientView;

import it.polimi.ingsw.Pair;
import it.polimi.ingsw.model.Action;

import java.util.List;
import java.util.Map;

/**
 * UI is the interface for the user interface. It bears all the methods to view what is happening on the {@link it.polimi.ingsw.model.Match}
 * and to communicate to the Server through the {@link ClientSocketHandler}. It is implemented by the {@link it.polimi.ingsw.clientView.gui.GUI}
 * and the {@link CLI}
 */
public interface UI {

    /**
     * Initializes the UI
     */
    public void start();

    /**
     * Logs to a match from the UI
     */
    public void login();

    /**
     * Handles the failure to log to the match
     * @param usernames List of strings of the already logged players
     */
    public void failedLogin(List<String> usernames);

    /**
     * Method to choose the divinity that can be picked by the players
     * @param divinitiesNames List of all divinities
     * @param divinitiesDescriptions List of their effects
     * @param playersNumber number of players in game
     * @param players Names of the players
     */
    public void selectPlayableDivinities(List<String> divinitiesNames, List<String> divinitiesDescriptions, int playersNumber, List<String> players);

    /**
     * Called when the user has to pick their divinity
     * @param divinitiesNames names of the selectable divinities
     */
    public void selectDivinity(List<String> divinitiesNames);

    /**
     * Places the worker of the player on the UI and the match
     */
    public void placeWorkers();

    /**
     * Displays a message coming from the server
     * @param msg generic message
     */
    public void textMessage(String msg);

    /**
     * Called to initialize the UI at the beginning of the player's turn
     */
    public void startTurn();

    /**
     * Asks the player which worker they want to select
     */
    public void selectWorker();

    /**
     * Displays a list of actions and the valid positions so that a player can choose
     * @param possibleActions {@code Map} of {@link Action} and list of coordinates
     */
    public void performAction(Map<Action, List<Pair<Integer, Integer>>> possibleActions);

    /**
     * Manages a losing player
     * @param username Name of loser
     */
    public void loser(String username);

    /**
     * Manages a winning player
     * @param username Name of winner
     */
    public void winner(String username);

    /**
     * Called to display the list of players' divinities
     */
    public void playersDivinities();

    /**
     * Manages the disconnection of a player
     * @param username Disconnected player
     */
    public void playerDisconnection(String username);

    /**
     * Called when the player correctly joined a lobby
     */
    public void inLobby();

    /**
     * Called when the player joined a full lobby
     */
    public void lobbyFull();

    /**
     * Called when the player ends their turn
     */
    public void endTurn();

    /**
     * Called to initialize the board and the information to display to begin a match
     */
    public void startGame();

    //errors

    /**
     * Tells the player that they commit a wrong move (coming from the server)
     * @param possibleActions list of possible actions
     * @param wrongX wrong x coordinate
     * @param wrongY wrong y coordinate
     */
    public void invalidMove(Map<Action, List<Pair<Integer, Integer>>> possibleActions, int wrongX, int wrongY);

    /**
     * Tells the player that they commit a wrong build (coming from the server)
     * @param possibleActions list of possible actions
     * @param wrongX wrong x coordinate
     * @param wrongY wrong y coordinate
     */
    public void invalidBuild(Map<Action, List<Pair<Integer, Integer>>> possibleActions, int wrongX, int wrongY);

    /**
     * Called when the server meets a wrong workers' placement
     */
    public void invalidWorkerPlacement();

    /**
     * Called when the server finds a wrong worker's selection
     */
    public void invalidWorkerSelection(int wrongX, int wrongY);

    //updates from model

    /**
     * Called to update the {@link BoardRepresentation}: the specified player moved a worker form the {@code from} coordinates
     * to the {@code to} position
     * @param player name of the player moving
     * @param xFrom beginning worker's position
     * @param yFrom beginning worker's position
     * @param xTo destination worker's position
     * @param yTo destination worker's position
     */
    public void moveUpdate(String player,int xFrom,int yFrom,int xTo,int yTo);

    /**
     * Called to update the {@link BoardRepresentation}: the specified player built on the specified coordinates
     * @param player name of the building player
     * @param x coordinate of the tile built
     * @param y coordinate of the tile built
     */
    public void buildUpdate(String player, int x, int y);

    /**
     * Called to update the board: it places the workers of a player on the specified positions
     * @param player name of the player to retrieve their color
     * @param x1 first worker's coordinate
     * @param y1 first worker's coordinate
     * @param x2 second worker's coordinate
     * @param y2 second worker's coordinate
     */
    public void workerPlacementUpdate(String player,int x1,int y1,int x2,int y2);

}
